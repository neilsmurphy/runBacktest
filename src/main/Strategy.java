

class Strategy {
    public Broker broker:
    private int barCount;
    int quantity = 0;
    int order_size = 100;
    Order buyOrder = null;
    boolean order = false;
    double sma = 0;
    int sma_period = 15;
    double trailing_stop = 0.0;
    double trailing_stop_pct = 0.02;

    }

    Strategy (Broker b) {
        broker = b;
    }

    public int void setBarCount(int i) {
        barCount = i;
    }

    public int void runStrategy (int i) {
        System.out.println("From Strategy: %d", i);

//        setBarCount = i;
//        if (i < sma_period) {
//            return;
//        }
//        // Execute orders transactions.
//        if (buyOrder != null) {
//                broker.executeOrders(i);
//        }
//
//        // Notify orders transactions.
//
//        // Print cash and market values
//        broker.setValue(broker.getCash() + price.close[i] * quantity);
//        if (printout == true) {
//                System.out.printf("Date: %s, Cash: %10.2f  Value: %10.2f %n", price.date[i], broker.getCash(), broker.getValue());
//        }
//
//        // Strategy
//        double sma_sum = 0.0;
//        for (int n = i - sma_period; n < i; n++) {
//                sma_sum += price.close[n];
//        }
//        sma = sma_sum / sma_period;
//        if (printout == true) {
//                System.out.printf("close: %f, sma: %f\n", price.close[i], sma);
//        }
//
//        // Create Orders.
//        // Create a buy order if close crosses up over sma.
//        if (price.close[i - 1] < sma && price.close[i] >= sma && quantity == 0) {
//                order_size = (int) ((broker.getValue() * 0.95) / price.close[i]);
//                buyOrder = broker.createOrder(price, 'market', order_size, 'buy');
//        }
//
//        if (quantity == 0) {
//                trailing_stop = 0;
//        } else if (quantity > 0) {
//                double trailing_stop_calc = price.low[i] * (1. - trailing_stop_pct);
//                if (trailing_stop_calc > trailing_stop) {
//                        trailing_stop = trailing_stop_calc;
//                }
//
//                if (printout == true) {
//                        System.out.printf("Date: %s, Close: %7.2f  Trailing: %7.2f %n", price.date[i], price.close[i], trailing_stop);
//                }
//        }
//
//        // Create closing order.
//        if (quantity > 0 && price.low[i] < trailing_stop) {
//                order = true;
//                trailing_stop = 0;
//        }
//
//        // Any clean up
//        if (i == price.date.length - 1) {
//                System.out.printf("Date: %s, Close: %7.2f  Market Value: %8.2f%n", price.date[i], price.close[i], broker.getValue());
//        }
    }

    public Order createOrder(double price, double orderSize, String side) {
        order = new Order(i, price, orderSize, side);
        orders.add(order);
        return order;
    }
}