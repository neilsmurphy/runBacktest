import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Feed is a specific asset from InfluxDB combined with data.
 */
public class FeedInfluxDB {
    String name = "BNBUSDT_day";
    String symbol = "BNBUSDT";
    String measure = "BNBUSDT_day";
    String serverURL = "http://127.0.0.1:8086";
    String database = "runbacktest";
    String username = "admin";
    String password = "";
    String dateFormat="yyyy-MM-dd'T'HH:mm:ssXXX";
    int index = 0;
    TradeData data;

    public FeedInfluxDB() {}

    public FeedInfluxDB(String name, String symbol, String measure) {
        this.name = name;
        this.symbol = symbol;
        this.measure = measure;
    }

    public FeedInfluxDB(String name, String symbol, String measure, String database,
                        String username, String password) {
        this.name = name;
        this.symbol = symbol;
        this.measure = measure;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public Feed getFeed() {
        return new Feed(name, symbol, data=addTradeData());
    }
    /**
     * TradeData object holds ohlcv data for backtesting and gets attached to Feeds.
     *
     * @return TradeData        Object containing OHLCV lines.
     */
    public TradeData addTradeData() {
        final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);
        influxDB.setDatabase(database);

        String sql = String.format("SELECT * FROM %s", measure);
        QueryResult queryResult = influxDB.query(new Query(sql, database));
        QueryResult.Series series = queryResult.getResults().get(0).getSeries().get(0);

        TradeData td = new TradeData(series.getValues().size());

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        for (int i = 0; i < series.getValues().size(); i++) {
            List<Object> line = series.getValues().get(i);
            try {
                td.date[i] = format.parse((String) line.get(0));
            } catch (ParseException e) {
                System.out.printf("Error converting date %s", e);
                e.printStackTrace();
                System.exit(1);
            }
            td.open[i] = (double) line.get(1);
            td.high[i] = (double) line.get(2);
            td.low[i] = (double) line.get(3);
            td.close[i] = (double) line.get(4);
            td.volume[i] = (double) line.get(5);
        }

        influxDB.close();
        return td;
    }
     public void setIndex(int index) {
        this.index = index;
    }
}