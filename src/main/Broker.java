import java.util.Deque;
import java.util.LinkedList;

public class Broker {
    private int barCount;
    private double cash;
    private double value;
    public boolean printout;
    Deque<Order> orders = new LinkedList<Order>();

    Broker (double c, boolean po) {
        cash = c;
        value = 0;
        printout = po;
    }

    Broker () {
        cash = 10000;
        value = 0;
        printout = false;
    }

    public void setBarCount(int i) {
        barCount = i;
    }

    public double getCash() {
        return cash;
    }

    // Adjust cash, minus to reduc.
    public void adjCash(double increment) {
        this.cash += increment;
    }

    public double getValue() {
        return value;
    }

    // Adjust market value, minus to reduc.
    public void setValue(double setval) {
        this.value = setval;
    }

    // Adjust market value, minus to reduc.
    public void adjValue(double increment) { this.value += increment; }



    public void executeOrders(int i) {
        for (Order order: getOpenOrders()) {
            if (order.getSide() == "buy") {
                cash -= order.getQuantity() * order.getTradeData().close[i];
                order.setStatus(1);
                if (printout == true) {
                    System.out.printf("Date: %s  Buy: quantity %d, price %5.2f\n",
                            order.tradeData.date[i], order.getQuantity(),
                            order.getTradeData().open[i]);
                }
            } else {
                cash += order.getQuantity() * order.getTradeData().close[i];
                order.setStatus(1);
                if (printout == true) {
                    System.out.printf("Date: %s  Close: quantity %d, price %5.2f\n",
                            order.getTradeData().date[i], order.getQuantity(),
                            order.getTradeData().open[i]);
                }
            }
        }
    }

    public Deque<Order> getOpenOrders () {
        Deque<Order> openOrders = new LinkedList<Order>();
        for (Order order: orders) {
            if (order.status == 0) {
                openOrders.add(order);
            }
        }
        return openOrders;
    }
}