import java.util.Date;

public class TradeData {
    int size;
    Date[] date;
    double[] open;
    double[] high;
    double[] low;
    double[] close;
    int[] volume;

    TradeData(int s) {
        size = s;
        this.date = new Date[size];
        this.open = new double[size];
        this.high = new double[size];
        this.low = new double[size];
        this.close = new double[size];
        this.volume = new int[size];
    }


}