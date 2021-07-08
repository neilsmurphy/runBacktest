import java.util.Date;

public class Trader {
    public static void main(String[] args) {
        // Start
        boolean printout = true;
        long begin = System.currentTimeMillis();

        // Instantiate Classes
        Broker broker = new Broker(20000, true);
        Feeds feeds = new Feeds("Apple Corp.", "AAPL");
        feeds.addTradeData();
//        Strategy strategy = new Strategy(broker);
//        System.out.println(tradeData.ohlcv.get(0).asset.symbol);

        // Pre-Trade
        TradeData iter = feeds.ohlcv.get(0);
        int iter_length = iter.close.length;

        // Trade

        for(int i = 1; i < iter_length; i++) {
            broker.setBarCount(i);
//            strategy.runStrategy(i);
            System.out.printf("Close: %5.2f %n", feeds.ohlcv.get(0).close[i]);

        }
         // Post-Trade
        long end = System.currentTimeMillis();
        System.out.printf("Elapsed time in seconds: %2.3f %n", (double) (end - begin) / 1000.0);

        // End
    }
}
