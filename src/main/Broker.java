import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * The broker class simulates a real life broker. It is responsible for
 * tracking cash, positions, receving and executing, rejecting orders.
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
    Deque<Order> orders = new LinkedList<Order>();
    Deque<Position> portfolio = new LinkedList<Position>();


    Broker (double c, List<Feed> fd, boolean po) {
        cash = c;
        value = 0;
        feeds = fd;
        printout = po;
    }

    Broker () {
        cash = 10000;
        value = 0;
        printout = false;
    }

    public void setIndex(int i) {
        index = i;
    }

    public double getCash() {
        return cash;
    }

    public void adjCash(double increment) {
        this.cash += increment;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double setval) {
        this.value = setval;
    }

    public void adjValue(double increment) {
        this.value += increment;
    }

    public void receiveOrder(Order order) {
        order.setStatus(Status.ACCEPTED);
        orders.add(order);
    }

    public void executeOrders(int i) {
        for (Order order: getOpenOrders()) {
            Position position;
            if (getPosition(order) != null) {
                position = getPosition(order);
            } else {
                position = createPosition(index, order);
                portfolio.add(position);
            }

            int sideFactor;
            if  (order.getSide() == order.side.BUY) {
                sideFactor = 1;
            } else if (order.getSide() == order.side.SELL) {
                sideFactor = -1;
            } else {
                sideFactor = 0;
            }
            System.out.println(sideFactor);
//            else if (order.getSide() == order.side.CLOSE) {
//                // Need to get position and make reverse order.
//            }

            // Different types of orders, start with market.

            // Market
            if (order.orderType == OrderType.MARKET)  {
//                System.out.printf("sf %d, qty %f, price: %f", sideFactor , order.getQuantity() ,
//                        order.getTradeData().open[i]);
                cash -= sideFactor * order.getQuantity() * order.getTradeData().open[i];
                order.setStatus(Status.COMPLETE);
                position.addTransaction(i, order.getTradeData(), order.getTradeData().open[i], order.getQuantity());

                if (printout == true) {
                    System.out.printf("Date: %s  %s: quantity %f, price %5.2f\n",
                            order.tradeData.date[i], order.getSide(), order.getQuantity(),
                            order.getTradeData().open[i]);
                }
            }
        }
    }

     /**
      * Ative open orders not completed or cancelled.
      * @return Returns a list of open orders.
      */
    public Deque<Order> getOpenOrders () {
        Deque<Order> openOrders = new LinkedList<Order>();
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
      * @return List of position objects.
      */
    public Deque<Position> getOpenPositions() {
        Deque<Position> openPositions = new LinkedList<Position>();
        for (Position position: portfolio) {
            if (position.getQuantity() != 0) {
                openPositions.add(position);
            }
        }
        return openPositions;
    }

     /**
      * get position for a given order. null if no position.
      * @param order
      * @return Position that matches to order.
      */
    public Position getPosition(Order order) {
//        if (getOpenPositions().length == 0) {
//            return null
//        }
        for (Position position : getOpenPositions()) {
            if (position.getTradeData() == order.getTradeData()) {
                return position;
            }
        }
        return null;
    }

    public Position createPosition (int i, Order order) {
        return new Position(index, order.getTradeData());
    }
}