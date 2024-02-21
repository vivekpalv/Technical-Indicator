package NSE.Nifty50;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
@Setter
@Getter
public class StoreData {
    ArrayList<Date>  date=new ArrayList<>();
    ArrayList<Float>  open=new ArrayList<>();
    ArrayList<Float>  close=new ArrayList<>();
    ArrayList<Float>  previousClose=new ArrayList<>();

    public ArrayList<Date> getDate() {
        return date;
    }

    public void setDate(ArrayList<Date> date) {
        this.date = date;
    }

    public ArrayList<Float> getOpen() {
        return open;
    }

    public void setOpen(ArrayList<Float> open) {
        this.open = open;
    }

    public ArrayList<Float> getClose() {
        return close;
    }

    public void setClose(ArrayList<Float> close) {
        this.close = close;
    }

    public ArrayList<Float> getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(ArrayList<Float> previousClose) {
        this.previousClose = previousClose;
    }
}
