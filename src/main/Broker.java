import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


     /**
     * Returns a new {@link BarSeries} instance that is a subset of this BarSeries
     * instance. It holds a copy of all {@link Bar bars} between <tt>startIndex</tt>
     * (inclusive) and <tt>endIndex</tt> (exclusive) of this BarSeries. The indices
     * of this BarSeries and the new subset BarSeries can be different. I. e. index
     * 0 of the new BarSeries will be index <tt>startIndex</tt> of this BarSeries.
     * If <tt>startIndex</tt> < this.seriesBeginIndex the new BarSeries will start
     * with the first available Bar of this BarSeries. If <tt>endIndex</tt> >
     * this.seriesEndIndex the new BarSeries will end at the last available Bar of
     * this BarSeries
     *
     * @param startIndex the startIndex (inclusive)
     * @param endIndex   the endIndex (exclusive)
     * @return a new BarSeries with Bars from startIndex to endIndex-1
     * @throws IllegalArgumentException if endIndex <= startIndex or startIndex < 0
     */
//    public int add () {
//
//        }



public class Broker {
    private int index;
    private double cash;
    private double value;
    public List<Feed> feeds;
    public boolean printout;
    Deque<Order> orders = new LinkedList<Order>();
    Deque<Positionr> positions = new LinkedList<Position>();


    Broker (double c, List<Feed> f, boolean po) {
        cash = c;
        value = 0;
        feeds = fd
        printout = po;
    }

    Broker () {
        cash = 10000;
        value = 0;
        printout = false;
    }

    public void setBarCount(int i) {
        index = i;
    }

    public double getCash() {
        return cash;
    }

    /**
     *  Adjust cash.
     * @param increment,  minus to reduc.
     */
    public void adjCash(double increment) {
        this.cash += increment;
    }

    /**
     * @return the current value.
     */
    public double getValue() {
        return value;
    }

    // Adjust market value, minus to reduc.

         /**
          * Set the market value of the portfolio.
           * @param setval
          */
    public void setValue(double setval) {

        this.value = setval;
    }

    // Adjust market value, minus to reduc.
    public void adjValue(double increment) {
        this.value += increment;
    }


    public void receiveOrder(Order order) {
        order.setStatus(Status.ACCEPTED);
        orders.add(order);
    }

    public void executeOrders(int i) {
        for (Order order: getOpenOrders()) {
            if  (order.getSide() == order.side.BUY) {
                int sideFactor = 1;
            } else if (order.getSide() == order.side.SELL) {
                int sideFactor = -1;
            } else if (order.getSide() == order.side.CLOSE) {
                // Need to get position and make reverse order.
            }
            if{
                cash -= order.getQuantity() * order.getTradeData().open[i];
                order.setStatus(Status.COMPLETE);
                if (printout == true) {
                    System.out.printf("Date: %s  Buy: quantity %d, price %5.2f\n",
                            order.tradeData.date[i], order.getQuantity(),
                            order.getTradeData().open[i]);
                }
            } else {
                cash += order.getQuantity() * order.getTradeData().open[i];
                order.setStatus(Status.COMPLETE);
                if (printout == true) {
                    System.out.printf("Date: %s  Close: quantity %d, price %5.2f\n",
                            order.getTradeData().date[i], order.getQuantity(),
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
    public Deque<Order> getOpenOrders () {
        Deque<Order> openOrders = new LinkedList<Order>();
        for (Order order: orders) {
            if (order.status.ordinal() < 4) {
                openOrders.add(order);
            }
        }
        return openOrders;
    }
}