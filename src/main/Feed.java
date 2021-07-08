import java.io.FileNotFoundException;
import java.io.File;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Feed {
    String name;
    String symbol = "AAPL";
    String dir =  "data/";
    int index = 0;
    TradeData data;

    public Feed() {
        data = addTradeData();
    }

    public Feed(String n, String s) {
        name = n;
        symbol = s;
        data = addTradeData();
    }

    public TradeData addTradeData () {

        String filePath= new File(dir + symbol + ".csv").getAbsolutePath();
        File file = new File(filePath);

        // this gives you a 2-dimensional array of strings
        List<List<String>> bars = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                String line = inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                bars.add(Arrays.asList(values));
            }

            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TradeData td = new TradeData(bars.size());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i < bars.size(); i++) {
            List<String> line = bars.get(i);
            try {
                td.date[i] = format.parse(bars.get(i).get(0));
            } catch (ParseException e) {
                System.out.printf("Error converting date %s", e);
                e.printStackTrace();
                System.exit(1);
            }
            td.open[i] = Double.parseDouble(bars.get(i).get(1));
            td.high[i] = Double.parseDouble(bars.get(i).get(2));
            td.low[i] = Double.parseDouble(bars.get(i).get(3));
            td.close[i] = Double.parseDouble(bars.get(i).get(4));
            td.volume[i] = Integer.parseInt(bars.get(i).get(5));
        }

        return td;
    }

    public void setIndex (int i) {
        index = i;
    }
}