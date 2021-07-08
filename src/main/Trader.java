import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trader {
    public static void main(String[] args) {
        // Start
        boolean printout = true;
        long begin = System.currentTimeMillis();
        List<Feed> feeds = new ArrayList<Feed>();

        // Instantiate Classes
        Broker broker = new Broker(20000, true);
        feeds.add(new Feed("Apple Corp.", "AAPL"));
        Strategy strategy = new Strategy(broker, feeds);
        System.out.println(feeds.get(0).symbol);

        // Determine length of data.
        TradeData iter = feeds.get(0).data;
        int iter_length = iter.close.length;

        for(int i = 1; i < iter_length; i++) {
            // Pre-Trade
            broker.setBarCount(i);
            strategy.setIndex(i);


            // Trade
            strategy.runStrategy();
//            System.out.printf("Close: %5.2f %n", feeds.get(0).ohlcv.get(0).close[i]);


            // Post-Trade


        }

        // End
        long end = System.currentTimeMillis();
        System.out.printf("Elapsed time in seconds: %2.3f %n", (double) (end - begin) / 1000.0);
    }
}
