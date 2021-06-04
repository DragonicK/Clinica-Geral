package Engine.Handler;

import Engine.Employee;
import Engine.Database.DBEmployee;
import Engine.Database.DBConfiguration;

import java.util.List;

public class EmployeeHandler implements IEmployeeHandler {
    private final DBConfiguration configuration;
    
    public EmployeeHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public boolean CanSelect(int personId) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                var result = !db.IsPersonUsed(personId);
                db.Close();
                
                return result;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean CanDelete(Employee employee) {
        return true;
    }
    
    @Override
    public void Update(Employee employee) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                db.Update(employee);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Employee employee) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                db.Put(employee);
                db.Close();
            }
        }
    }
    
    @Override
    public void Delete(Employee employee) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                db.Delete(employee);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Employee> GetEmployees() {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                var list = db.GetEmployees();
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Employee> FindByName(String name) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                var list = db.GetByName(name);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Employee> FindByDocument(String document) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()){
                var list = db.GetByDocument(document);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
}