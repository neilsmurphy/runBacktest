import java.util.List;

public class Strategy {
    public Broker broker;
    public List<Feed> feeds;
    private int index = 0;
    int quantity = 0;
    int order_size = 100;
    double sma = 0;
    int sma_period = 15;
    double trailing_stop = 0.0;
    double trailing_stop_pct = 0.02;

    public Strategy (Broker b, List<Feed> f) {
        broker = b;
        feeds = f;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void runStrategy () {
        for (Feed feed : feeds) {

            if (index == 5) {
                Order buyOrder = createOrder(index, feed.data, OrderType.MARKET, 10, Side.BUY);
                submitOrder(broker, buyOrder);
                System.out.printf("Index: %d, buy id: %d, Cash: %5.2f, Value %5.2f %n", index,
                        buyOrder.orderId, broker.getCash(), broker.getValue());
            } else if (index == 8) {
                Order sellOrder = createOrder(index, feed.data, OrderType.MARKET, 10,
                        Side.SELL);
                submitOrder(broker, sellOrder);
                System.out.printf("Index: %d, sell id: %d, Cash: %5.2f, Value %5.2f %n", index,
                        sellOrder.orderId, broker.getCash(), broker.getValue());
            }
            broker.executeOrders();
        }


//        setIndex = i;
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

    public Order createOrder(int index, TradeData feed, OrderType oType, double orderSize,
                             Side side) {
        return new Order(index, feed, oType, orderSize, side);
    }

    public void submitOrder(Broker bk, Order ord) {
        ord.setStatus(Status.SUBMITTED);
        bk.receiveOrder(ord);
    }
}