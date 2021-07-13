import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;

/**
 * Records each individual transaction, which add up to orders and positions.
 */
public class Transaction {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int transactionId;
    public int index;
    public Order order;
    public TradeData tradeData;
    public Date date_transacted;
    public double price;
    public double quantity;


    Transaction (int index, Order order, TradeData tradeData, double price, double quantity) {
        transactionId = count.incrementAndGet();
        this.index = index;
        this.order = order;
        this.tradeData = tradeData;
        date_transacted = tradeData.date[index];
        this.price = price;
        this.quantity = quantity;
    }
}