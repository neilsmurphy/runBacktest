import java.text.SimpleDateFormat;
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
            int valid = 10;
            if (index < sma_period) {
                return;
            } else if (index > (feed.data.close.length - valid)) {
                return;
            }

            // Signal. Simple moving average.
            double sma_sum = 0.0;
            for (int n = index - sma_period; n < index; n++) {
                    sma_sum += feed.data.close[n];
            }
            sma = sma_sum / sma_period;
//            {System.out.printf("close: %f, sma: %f\n", feed.data.close[index], sma);}

            // Create Orders.
            // Create a buy order if close crosses up over sma.
            Position feedPosition = broker.getPositionFeed(feed);

            double currQuantity = 0.0;
            if (feedPosition != null) {
                currQuantity = feedPosition.getQuantity();
            }
            SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (feed.data.close[index - 1] < sma && feed.data.close[index] >= sma && currQuantity == 0.0) {
                orderSize = (int) ((broker.getValue() * 0.95) / feed.data.close[index]);
                double price = feed.data.close[index] * .995;
                Order buyOrder = new Order(index, feed.data, OrderType.LIMIT, price, orderSize, Side.BUY,
                        feed.data.date[index + valid]);
                submitOrder(broker, buyOrder);
                System.out.printf(
                        "BUY ORDER PLACED: %s %d, buy id: %d, limit price: %5.2f, quantity: %2d, " +
                                "Cash: %5.2f, Value %5.2f %n",
                        dateFor.format(feed.data.date[index]), index, buyOrder.orderId
                        , price, orderSize,
                        broker.getCash(), broker.getValue());

            } else if (feed.data.close[index - 1] > sma && feed.data.close[index] <= sma && currQuantity != 0) {
                Order sellOrder = new Order(index, feed.data, OrderType.MARKET, currQuantity,
                        Side.SELL);
                submitOrder(broker, sellOrder);
                System.out.printf("Index: %s %d, sell id: %d, Cash: %5.2f, Value %5.2f %n",
                        dateFor.format(feed.data.date[index]), index,
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