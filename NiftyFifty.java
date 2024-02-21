package NSE.Nifty50;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Nifty50")
public class NiftyFifty {
    @Id
    int serialNo;
    Date date;
    String open;
    String high;
    String low;
    String close;
    String sharesTraded;
    String turnover₹;

    public int getSerialNo() {
        return serialNo;
    }

    public Date getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getSharesTraded() {
        return sharesTraded;
    }

    public String getTurnover₹() {
        return turnover₹;
    }
}
