import java.util.concurrent.atomic.AtomicInteger;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * The Position class tracks all positions. It keeps a list of all the
 * position objects created. It can provide open or closed or all positions.
 * It will provide  position event when positions are created or closed.
 * A position is closed when it equals zero. For instance, if you go from
 */
public class Position {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int positionId;
    double quantity = 0;
    public TradeData tradeData;
    Deque<Transaction> transactions = new LinkedList<>();

    Position (int i, TradeData td) {
        positionId = count.incrementAndGet();
        tradeData = td;
    }

    public void addTransaction (int i, TradeData td, double prc, double qty) {
        transactions.add(new Transaction(i, td, prc, qty));
        quantity += qty;
    }

    public TradeData getTradeData () {
        return tradeData;
    }

    public double getQuantity() {
        return quantity;
    }

}

