import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

enum OrderType { MARKET, LIMIT, STOP, STOP_LIMIT, STOP_TRAIL, STOP_TRAIL_LIMIT }
enum Side { BUY, SELL, CLOSE }
enum Status { CREATED, SUBMITTED, ACCEPTED, PARTIAL, COMPLETE, REJECTED, MARGIN, CANCELLED }

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
    Deque<Transaction> transactions = new LinkedList<>();


    Order (int index, TradeData tradeData, OrderType orderType, double quantity, Side side) {
        orderId = count.incrementAndGet();
        this.index = index;
        this.tradeData = tradeData;
        this.orderType = orderType;
        this.quantity = quantity;
        this.side = side;
    }

    Order (int index, TradeData tradeData, OrderType orderType, double quantity, Side side,
           double trailingPercent) {
        orderId = count.incrementAndGet();
        this.index = index;
        this.tradeData = tradeData;
        this.orderType = orderType;
        this.quantity = quantity;
        this.side = side;
        this.trailingPercent = trailingPercent;
        this.trailingAmount = 0;
    }

    public Status getStatus () {
        return status;
    }

    public void setStatus (Status status) {
        this.status = status;
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }

    public void addTransaction(Transaction transaction) {
       this.transactions.add(transaction);
    }

    public double getQuantity() { return quantity; }

    public Side getSide() {
        return side;
    }

    public TradeData getTradeData () {
        return tradeData;
    }
}