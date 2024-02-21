package NSE.Nifty50;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
public class RestControl {
    @Autowired
    Operation o;

    @GetMapping("/store")
    public String store() throws IOException, CsvValidationException, ParseException {
        CSVReader csvReader=new CSVReader(new FileReader("C:\\Users\\KINJAL PAL\\Downloads\\NIFTY.csv"));
        String[] strings = csvReader.readNext();
        int i=1;
        while ((strings=csvReader.readNext())!=null){
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            //from java 17 "sep" not considered as september now it considered as "sept"
            //so, converting/replace "sep" to "sept"
            if (strings[0].contains("SEP")){
                strings[0] = strings[0].replace("SEP", "SEPT");
            }
            Date parsedate = format.parse(strings[0]);
            NiftyFifty nf=new NiftyFifty(i,parsedate,strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]);
            NiftyFifty save = o.save(nf);
            i++;
        }
        o.findById(1);
        return "uploaded";
    }
    @GetMapping("/fetch/{serialNo}")
    private NiftyFifty get(@PathVariable("serialNo") String i) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        Date parse = simpleDateFormat.parse(i);

        NiftyFifty byDate = o.findByDate(parse);
        return byDate;
    }
    @GetMapping("/rsi")
    private double rsi(){
        TechnicalIndicator ti=new TechnicalIndicator();
        return ti.rsiNifty50(o);
    }
    @GetMapping("/mfi")
    private float mfi(){
        TechnicalIndicator ti=new TechnicalIndicator();
        float mfi = ti.mfi(o);
        return mfi;
    }
    private float adx(){
        return 4f;
    }
}
