package Engine.Handler;

import Engine.Treatment;
import Engine.TreatmentProduct;

import java.util.Date;
import java.util.List;

public interface ITreatmentHandler {
    List<Treatment> GetTreatments();
    
    void Delete(Treatment treatment);
    void Update(Treatment treatment);
    void Put(Treatment treatment);
    
    List<TreatmentProduct> GetProducts(int treatmentId);
    
    List<Treatment> FindByEmployeeDocument(String document);
    List<Treatment> FindByEmployeeName(String name);
    List<Treatment> FindByPatientDocument(String document);
    List<Treatment> FindByPatientName(String name);
    List<Treatment> FindByDate(Date startDate, Date endDate);
    void UpdateProducts(List<TreatmentProduct> products);
}