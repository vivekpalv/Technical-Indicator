package NSE.Nifty50;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface Operation extends CrudRepository<NiftyFifty,Integer> {
    NiftyFifty findByDate(Date date);
}
