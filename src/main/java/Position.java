import java.util.concurrent.atomic.AtomicInteger;
import java.util.Deque;
import java.util.LinkedList;

/**
 * The Position class tracks all positions. It keeps a list of all the
 * position objects created. It can provide open or closed or all positions.
 * It will provide  position event when positions are created or closed.
 * A position is closed when it equals zero.
 */
public class Position {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int positionId;
    public int barCreated;
    double quantity = 0;
    public TradeData tradeData;
    Deque<Transaction> transactions = new LinkedList<>();

    Position (int index, TradeData tradeData) {
        positionId = count.incrementAndGet();
        barCreated = index;
        this.tradeData = tradeData;
    }

    public void addTransaction (int index, Order order, double price,
                                double quantity) {
        Transaction newTransaction = new Transaction(index, order, order.tradeData, price, quantity);
        transactions.add(newTransaction);
        order.addTransaction(newTransaction);
        this.quantity += quantity;
    }

    public TradeData getTradeData () {
        return tradeData;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getLatestClose (int index) {
        return getTradeData().close[index];
    }
}

