import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int orderId;
    public int barCount;
    public int status;
    public TradeData tradeData;
    public String orderType; // Market or trailing
    public double quantity;
    public String side;
    public double price;
    public double limit;
    public double trailingPercent;
    public double trailingAmount;
//    public double price; use market for now.

    Order (int i, TradeData td, String oType, double qty, String sd) {
        orderId = count.incrementAndGet();
        barCount = i;
        status = 0;
        tradeData = td;
        orderType = oType;
        quantity = qty;
        side = sd;
    }

    Order (int i, TradeData td, String oType, double qty, String sd, double tp) {
        orderId = count.incrementAndGet();
        barCount = i;
        status = 0;
        tradeData = td;
        orderType = oType;
        quantity = qty;
        side = sd;
        trailingPercent = tp;
        trailingAmount = 0;
    }



    public int getStatus () {
        return status;
    }

    public void setStatus (int st) {
        status = st;
    }

    public TradeData getTradeData () {
        return tradeData;
    }

    public String getSide() {
        return side;
    }

    public double getQuantity() { return quantity; }
}