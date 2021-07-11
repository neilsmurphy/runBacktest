import java.util.concurrent.atomic.AtomicInteger;

enum Status { CREATED, SUBMITTED, ACCEPTED, PARTIAL, COMPLETE, REJECTED, MARGIN, CANCELLED }
enum Side { BUY, SELL, CLOSE }
enum OrderType { MARKET, LIMIT, STOP, STOP_LIMIT, STOP_TRAIL, STOP_TRAIL_LIMIT }

public class Order {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int orderId;
    public int index;
    Status status = Status.CREATED;
    public TradeData tradeData;
    public OrderType orderType;
    public double quantity;
    public Side side;
    public double price;
    public double limit;
    public double trailingPercent;
    public double trailingAmount;
//    public double price; use market for now.


    Order (int i, TradeData td, OrderType oType, double qty, Side sd) {
        orderId = count.incrementAndGet();
        index = i;
        tradeData = td;
        orderType = oType;
        quantity = qty;
        side = sd;
    }

    Order (int i, TradeData td, OrderType oType, double qty, Side sd, double tp) {
        orderId = count.incrementAndGet();
        index = i;
        tradeData = td;
        orderType = oType;
        quantity = qty;
        side = sd;
        trailingPercent = tp;
        trailingAmount = 0;
    }

    public Status getStatus () {
        return status;
    }

    public void setStatus (Status st) {
        status = st;
    }

    public TradeData getTradeData () {
        return tradeData;
    }

    public Side getSide() {
        return side;
    }

    public double getQuantity() { return quantity; }
}