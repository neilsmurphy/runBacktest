import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Feed is a specific asset from CSV combined with data.
 */
public class Feed {
    String name;
    String symbol ;
    TradeData data;
    int index = 0;


    public Feed(String name, String symbol, TradeData data) {
        this.name = name;
        this.symbol = symbol;
        this.data = data;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
