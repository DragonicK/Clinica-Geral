package Engine.Handler;

import Engine.Treatment;
import Engine.TreatmentProduct;
import Engine.Database.DBConfiguration;
import Engine.Database.DBTreatment;
import Engine.Database.DBTreatmentProduct;

import java.util.Date;
import java.util.List;

public class TreatmentHandler implements ITreatmentHandler {
    private final DBConfiguration configuration;
    
    public TreatmentHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public List<Treatment> GetTreatments() {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetTreatments();
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public void Delete(Treatment treatment) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(treatment);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Treatment treatment) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(treatment);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Treatment treatment) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(treatment);
                treatment.SetTreatmentId(db.GetLastInsertedId());
                db.Close();
            }
        }
    }
    
    @Override
    public List<Treatment> FindByEmployeeDocument(String document) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetByEmployeeDocument(document);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Treatment> FindByEmployeeName(String name) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetByEmployeeName(name);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Treatment> FindByPatientDocument(String document) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetByPatientDocument(document);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Treatment> FindByPatientName(String name) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetByPatientName(name);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Treatment> FindByDate(Date startDate, Date endDate) {
        var db = new DBTreatment(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetTreatmentsFromDate(startDate, endDate);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public List<TreatmentProduct> GetProducts(int treatmentId) {
        var db = new DBTreatmentProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.GetProducts(treatmentId);
                db.Close();
                
                return result;
            }
        }
        
        return null;
    }
    
    @Override
    public void UpdateProducts(List<TreatmentProduct> products) {
        var db = new DBTreatmentProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var count = products.size();
                
                for (var i = 0; i < count; ++i) {
                    var product = products.get(i);
                    
                    switch (product.OperationState){
                        case Delete -> db.Delete(product);
                        case Update -> db.Update(product);
                        case Insert -> db.Put(product);
                    }
                }
                
                db.Close();
            }
        }
    }
}