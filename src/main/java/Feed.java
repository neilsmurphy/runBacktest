import java.io.FileNotFoundException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feed is a specific asset combined with data.
 */
public class Feed {
    String name;
    String symbol = "AAPL";
    String dir = "data/";
    int index = 0;
    String dateFormat="yyyy-MM-dd";
    TradeData data;

    public Feed() {
        data = addTradeData();
    }

    public Feed(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        data = addTradeData();
    }

    public Feed(String name, String symbol, String format) {
        this.name = name;
        this.symbol = symbol;
        this.dateFormat = format;
        data = addTradeData();
    }

    /**
     * TradeData object holds ohlcv data for backtesting and gets attached to Feeds.
     *
     * @return TradeData        Object containing OHLCV lines.
     */
    public TradeData addTradeData() {
        String filePath = new File(dir + symbol + ".csv").getAbsolutePath();
        File file = new File(filePath);

        List<List<String>> bars = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String line = inputStream.nextLine();
                String[] values = line.split(",");
                bars.add(Arrays.asList(values));
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TradeData td = new TradeData(bars.size());

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        for (int i = 1; i < bars.size(); i++) {
            List<String> line = bars.get(i);
            try {
                td.date[i] = format.parse(line.get(0));
            } catch (ParseException e) {
                System.out.printf("Error converting date %s", e);
                e.printStackTrace();
                System.exit(1);
            }
            td.open[i] = Double.parseDouble(line.get(1));
            td.high[i] = Double.parseDouble(line.get(2));
            td.low[i] = Double.parseDouble(line.get(3));
            td.close[i] = Double.parseDouble(line.get(4));
            td.volume[i] = Integer.parseInt(line.get(5));
        }
        return td;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
