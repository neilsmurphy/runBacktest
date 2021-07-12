import java.util.ArrayList;
import java.util.List;

public class Trader {
    public static void main(String[] args) {
        // Start
        boolean printout = true;
        long begin = System.currentTimeMillis();
        List<Feed> feeds = new ArrayList<>();

        // Instantiate Classes
        feeds.add(new Feed("Apple Corp.", "AAPL"));
        // feeds.add(new Feed("Apple Corp.", "dev", "yyyy-MM-dd HH:mm:dd"));
        Broker broker = new Broker(20000, feeds, true);
        Strategy strategy = new Strategy(broker, feeds);

        // Determine length of data.
        Feed thisFeed = feeds.get(0);
        TradeData iter = thisFeed.data;
        int iter_length = iter.close.length;

        for(int i = 1; i < iter_length; i++) {
            // Pre-Trade
            broker.setIndex(i);
            strategy.setIndex(i);


            // Trade
            strategy.runStrategy();
            if (i < 15) {
                System.out.printf("Symbol: %s, Close: %5.2f, Cash: %5.2f, Value: %5.2f %n",
                        thisFeed.symbol, iter.close[i], broker.getCash(), broker.getValue());
            }

            // Post-Trade


        }

        // End
        long end = System.currentTimeMillis();
        System.out.printf("Elapsed time in seconds: %2.3f %n", (double) (end - begin) / 1000.0);
    }
}
