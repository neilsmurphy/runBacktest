import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * The broker class simulates a real life broker. It is responsible for
 * tracking cash, positions, receiving and executing, rejecting orders.
 * The broker class will calculate the value of the portfolio, cash +
 * securities.
 * <p>
 * During the backtesting phase of the project there will only be an initial
 * deposit. No contributions during testing.
 * <p>
 * A notification will be available when on any order events such as partial
 * or fully filled orders rejected orders, etc.
 */
public class Broker {
    private int index;
    private double cash;
    private double value;
    public List<Feed> feeds;
    public boolean printout;
    Deque<Order> orders = new LinkedList<>();
    Deque<Position> portfolio = new LinkedList<>();

    Broker (double cash, List<Feed> feeds, boolean printOn) {
        this.cash = cash;
        value = 0;
        this.feeds = feeds;
        this.printout = printOn;
    }

    /**
     * Resets the index for every trading cycle.
     * @param index             Trade iteration index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    public double getCash() {
        return this.cash;
    }

    public void adjCash(double increment) {
        this.cash += increment;
    }

    public double getValue() {
        double positionValues = 0.0;
        for (Position position : getOpenPositions()) {
            positionValues += position.getQuantity() * position.getLatestClose(index);
        }
        return (cash + positionValues);
    }

    public void adjValue(double increment) {
        this.value += increment;
    }



    public void receiveOrder(Order order) {
        order.setStatus(Status.ACCEPTED);
        orders.add(order);
    }

    /**
     * Manages order execution at the beginning of each trading cycle. This
     * will execute orders against the previously closed candle.
     */
    public void executeOrders() {
        for (Order order: getOpenOrders()) {

            Position position;
            if (getPosition(order) != null) {
                position = getPosition(order);
            } else {
                position = createPosition(order);
                portfolio.add(position);
            }

            if (order.valid != null && order.valid.compareTo(order.tradeData.date[index]) >= 0) {
                order.setStatus(Status.CANCELLED);
            }

            int sideFactor;
            if  (order.getSide() == Side.BUY) {
                sideFactor = 1;
            } else if (order.getSide() == Side.SELL) {
                sideFactor = -1;
            } else {
                sideFactor = 0;
            }
            // todo add in close

            // Different types of orders, start with market.
            double tradePrice = switch(order.orderType) {
                // Market
                case MARKET:
                    yield order.getTradeData().open[index];

                case LIMIT:
                    if (order.getTradeData().open[index] < order.price) {
                        yield order.getTradeData().open[index];
                    } else if (order.getTradeData().low[index] < order.price) {
                        yield order.price;
                    } else {
                        yield 0;
                    }

                case STOP, STOP_LIMIT, STOP_TRAIL, STOP_TRAIL_LIMIT:
                    yield 0;

            };
            if (tradePrice == 0) {
                return;
            }

            cash -= sideFactor * order.getQuantity() * tradePrice;
            order.setStatus(Status.COMPLETE);
            position.addTransaction(index, order, tradePrice,sideFactor * order.getQuantity());

            if (printout) {
                System.out.printf("TRANSACTION: Date: %s  %s: quantity %f, price %5.2f\n",
                        order.tradeData.date[index], order.getSide(), order.getQuantity(),
                        order.getTradeData().open[index]);
            }
        }
    }

     /**
      * Active open orders not completed or cancelled.
      * @return List            Returns a list of open orders.
      */
    public Deque<Order> getOpenOrders () {
        Deque<Order> openOrders = new LinkedList<>();
        for (Order order: orders) {
            if (order.status.ordinal() < 4) {
                openOrders.add(order);
            }
        }
        return openOrders;
    }

     /**
      * Active open positions.
      * Positions are closed when they go back to zero.
      * @return List            List of position objects.
      */
    public Deque<Position> getOpenPositions() {
        Deque<Position> openPositions = new LinkedList<>();
        for (Position position: portfolio) {
            if (position.getQuantity() != 0) {
                openPositions.add(position);
            }
        }
        return openPositions;
    }

     /**
      * get position for a given order. null if no position.
      * @param order            Get position associated with order.
      * @return position        That matches to order.
      */
    public Position getPosition(Order order) {
        for (Position position : getOpenPositions()) {
            if (position.getTradeData() == order.getTradeData()) {
                return position;
            }
        }
        return null;
    }

     /**
      * get position for a given order. null if no position.
      * @param feed            Get position associated with order.
      * @return position        That matches to order.
      */
    public Position getPositionFeed(Feed feed) {
        for (Position position : getOpenPositions()) {
            if (position.getTradeData() == feed.data) {
                return position;
            }
        }
        return null;
    }
    public Position createPosition (Order order) {
        return new Position(index, order.getTradeData());
    }
}