package Engine;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

public class Treatment extends Schedule {
    public int ScheduleId;
    public Date FinishedDate;
    public List<TreatmentProduct> Products;
    
    public Treatment() {
        Products = new ArrayList<TreatmentProduct>();
    }
    
    public void SetTreatmentId(int id) {
        Id = id;
        
        var count = Products.size();
        
        for (var i = 0; i < count; ++i) {
            Products.get(i).TreatmentId = id;
        }
    }
}