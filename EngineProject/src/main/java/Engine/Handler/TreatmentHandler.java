package Engine.Handler;

import Engine.Database.DBConfiguration;
import Engine.Database.DBTreatment;
import Engine.Treatment;

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
}