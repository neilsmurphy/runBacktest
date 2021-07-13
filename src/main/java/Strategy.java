import java.util.List;

public class Strategy {
    public Broker broker;
    public List<Feed> feeds;
    private int index = 0;
    int quantity = 0;
    int orderSize = 100;
    double sma = 0;
    int sma_period = 15;
    double trailing_stop = 0.0;
    double trailing_stop_pct = 0.02;

    public Strategy (Broker broker, List<Feed> feeds) {
        this.broker = broker;
        this.feeds = feeds;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void runStrategy () {
        for (Feed feed : feeds) {
            if (index < sma_period) {
                return;
            }

            // Signal. Simple moving average.
            double sma_sum = 0.0;
            for (int n = index - sma_period; n < index; n++) {
                    sma_sum += feed.data.close[n];
            }
            sma = sma_sum / sma_period;
            {System.out.printf("close: %f, sma: %f\n", feed.data.close[index], sma);}

            // Create Orders.
            // Create a buy order if close crosses up over sma.
            Position feedPosition = broker.getPositionFeed(feed);

            double currQuantity = 0.0;
            if (feedPosition != null) {
                currQuantity = feedPosition.getQuantity();
            }

            if (feed.data.close[index - 1] < sma && feed.data.close[index] >= sma && currQuantity == 0.0) {
                orderSize = (int) ((broker.getValue() * 0.95) / feed.data.close[index]);
                Order buyOrder = createOrder(index, feed.data, OrderType.MARKET, orderSize,
                        Side.BUY);
                submitOrder(broker, buyOrder);
                System.out.printf("Index: %d, buy id: %d, Cash: %5.2f, Value %5.2f %n", index,
                        buyOrder.orderId, broker.getCash(), broker.getValue());
            } else if (feed.data.close[index - 1] > sma && feed.data.close[index] <= sma && currQuantity != 0) {
                Order sellOrder = createOrder(index, feed.data, OrderType.MARKET, currQuantity,
                        Side.SELL);
                submitOrder(broker, sellOrder);
                System.out.printf("Index: %d, sell id: %d, Cash: %5.2f, Value %5.2f %n", index,
                        sellOrder.orderId, broker.getCash(), broker.getValue());
            }
        }




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

    public Order createOrder(int index, TradeData feed, OrderType orderType, double orderSize,
                             Side side) {
        return new Order(index, feed, orderType, orderSize, side);
    }

    public void submitOrder(Broker broker, Order order) {
        order.setStatus(Status.SUBMITTED);
        broker.receiveOrder(order);
    }

    public void cancelOrder(Order order) {
        order.cancel();
    }
}