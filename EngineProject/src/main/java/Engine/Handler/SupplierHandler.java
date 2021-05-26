package Engine.Handler;

import Engine.Supplier;
import Engine.Database.DBProduct;
import Engine.Database.DBSupplier;
import Engine.Database.DBConfiguration;

import java.util.List;

public class SupplierHandler implements ISupplierHandler {
    private final DBConfiguration configuration;
    
    public SupplierHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public boolean CanSelectPerson(int personId) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsPersonUsed(personId);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public boolean CanDelete(Supplier supplier) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsSupplierUsed(supplier.Id);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public void Put(Supplier supplier) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(supplier);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Supplier supplier) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(supplier);
                db.Close();
            }
        }
    }
    
    @Override
    public void Delete(Supplier supplier) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(supplier);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Supplier> GetSuppliers() {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetSuppliers();
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Supplier> FindByName(String name) {
        var db = new DBSupplier(configuration);
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
    public List<Supplier> FindByDocument(String document) {
        var db = new DBSupplier(configuration);
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
    
    @Override
    public List<Supplier> FindByFantasyName(String name) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByFantasyName(name);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
}