package Engine.Handler;

import Engine.Product;
import Engine.Database.DBProduct;
import Engine.Database.DBTreatmentProduct;
import Engine.Database.DBConfiguration;

import java.util.List;

public class ProductHandler implements IProductHandler {
    private DBConfiguration configuration;
    
    public ProductHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public boolean Exists(int id) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = db.Exists(id);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public boolean CodeExists(int code) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = db.CodeExists(code);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public void Delete(Product product) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(product);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Product product) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(product);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Product product) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(product);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Product> GetProducts() {
        var db = new DBProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.Get();
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Product> Find(String name) {
        var db = new DBProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.Get(name);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean CanDelete(Product product) {
        var db = new DBTreatmentProduct(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = !db.IsProductUsed(product.Id);
                db.Close();
                
                return result;
            }
        }
        
        return true;
    }
}