package NSE.Nifty50;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

public class TechnicalIndicator {
    public double rsiNifty50(Operation o){
        double currentRsi=0;
        int count = (int)o.count();
        ArrayList<Float> gain = new ArrayList<>();
        ArrayList<Float> loss = new ArrayList<>();
        for (int i=count;i>count-29;i--){
            Optional<NiftyFifty> byId = o.findById(i);
            float open = Float.parseFloat(byId.get().getOpen());
            float close= Float.parseFloat(byId.get().getClose());
            float previousClose=previousDayClose(o,i);
            float difference=open-close;
            float differencePercentage=(difference*100)/previousClose;
            if (differencePercentage>0){
                gain.add(differencePercentage);
            } else if (differencePercentage<0) {
                loss.add(differencePercentage);
            }
        }
        double gainAverage=average(gain);
        double lossAverage=average(loss);

        currentRsi=100-(100/(1+((gainAverage+gain.get(13))/(Math.abs(loss.get(13))+lossAverage))));
        return currentRsi;
    }
    private float average(ArrayList<Float> doAverage){
        float average=0;
        for (int i=0;i<13;i++){
            average+=Math.abs(doAverage.get(i));
        }
        return average/13;
    }
    private float previousDayClose(Operation o,int i){
        String close = o.findById(i - 1).get().getClose();
        return Float.parseFloat(close);
    }
    public StoreData saveToList() throws CsvValidationException, IOException, ParseException {
        CSVReader cr=new CSVReader(new FileReader("C:\\Users\\KINJAL PAL\\Downloads\\TCS.csv"));
        StoreData storeData=new StoreData();
        ArrayList<Date> dates=new ArrayList<>();
        ArrayList<Float> open=new ArrayList<>();
        ArrayList<Float> close=new ArrayList<>();
        ArrayList<Float> prevClose=new ArrayList<>();
        String[] strings = cr.readNext();
        while ((strings=cr.readNext())!=null){
            SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
            Date parsedDate = format.parse(strings[0]);
            dates.add(parsedDate);
            open.add(Float.parseFloat(strings[2].replace(",","")));
            close.add(Float.parseFloat(strings[7].replace(",","")));
            prevClose.add(Float.parseFloat(strings[5].replace(",","")));
        }
        storeData.setDate(dates);
        storeData.setOpen(open);
        storeData.setClose(close);
        storeData.setPreviousClose(prevClose);
        return storeData;
    }

    public float mfi(Operation o){
        int maxLength = (int)o.count();
        ArrayList<Float> positiveMoneyFlow=new ArrayList<>();
        ArrayList<Float> negativeMoneyFlow=new ArrayList<>();
        for (int i=maxLength;i>maxLength-49;i--){
            Optional<NiftyFifty> byId = o.findById(i);
            float low=Float.parseFloat(byId.get().getLow());
            float close=Float.parseFloat(byId.get().getClose());
            float high=Float.parseFloat(byId.get().getHigh());
            float typicalprice=(low+close+high)/3;
            float volume=Float.parseFloat(byId.get().getSharesTraded());
            float moneyflow=typicalprice*volume;
            float previousclose = Float.parseFloat(o.findById(i-1).get().getClose());
            if (close>previousclose){
                positiveMoneyFlow.add(moneyflow);
            }else {
                negativeMoneyFlow.add(moneyflow);
            }
            if (positiveMoneyFlow.size()>=14 && negativeMoneyFlow.size()>=14){
                break;
            }
        }
        float sumofpositivemoneyflow=0;
        float sumofnegativemoneyflow=0;
        System.out.println(positiveMoneyFlow.size()+" : "+negativeMoneyFlow.size());
        for (int i = 0; i < 14; i++) {
            sumofpositivemoneyflow+=positiveMoneyFlow.get(i);
            sumofnegativemoneyflow+=negativeMoneyFlow.get(i);
        }
        return 100-(100/(1+sumofpositivemoneyflow/sumofnegativemoneyflow));
    }
}
