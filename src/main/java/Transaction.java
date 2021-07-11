import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;

/**
 * Recoreds each individual transaction, which add up to orders and positions.
 */
public class Transaction {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int transactionId;
    public int index;
    public TradeData tradeData;
    public Date date_transacted;
    public double price;
    public double quantity;


    Transaction (int i, TradeData td, double prc, double qty) {
        transactionId = count.incrementAndGet();
        index = i;
        tradeData = td;
        date_transacted = tradeData.date[i];
        price = prc;
        quantity = qty;
    }
}