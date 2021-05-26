package Engine.Handler;

import Engine.Patient;
import Engine.Database.DBConfiguration;
import Engine.Database.DBPatient;

import java.util.List;

public class PatientHandler implements IPatientHandler {
    private DBConfiguration configuration;
    
    public PatientHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public boolean CanSelect(int personId) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsPersonAsPatient(personId);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public boolean CanDelete(Patient patient) {
        return true;
    }
    
    @Override
    public void Delete(Patient patient) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(patient);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Patient patient) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(patient);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Patient patient) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(patient);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Patient> GetPatients() {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetPatients();
                db.Close();
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Patient> FindByName(String name) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByName(name);
                db.Close();
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Patient> FindByDocument(String document) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByDocument(document);
                db.Close();
                return list;
            }
        }
        
        return null;
    }
    
}