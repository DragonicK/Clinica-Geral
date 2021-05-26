package Engine.Handler;

import Engine.City;
import Engine.Country;
import Engine.Database.DBCity;
import Engine.Database.DBCountry;
import Engine.Database.DBAddress;
import Engine.Database.DBConfiguration;

import java.util.List;

public class RegionHandler implements IRegionHandler {
    private DBConfiguration configuration;
    
    public RegionHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public void Delete(Country country) {
        var db = new DBCountry(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(country.Id);
                db.Close();
            }
        }
    }
    
    @Override
    public void Delete(City city) {
        var db = new DBCity(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(city);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Country> GetCountries() {
        var db = new DBCountry(configuration);
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
    public List<City> GetCities() {
        var db = new DBCity(configuration);
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
    public boolean ExistsCountry(String name) {
        var db = new DBCountry(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.Exists(name);
                db.Close();
                
                return result;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean ExistsCity(String name, int countryId) {
        var db = new DBCity(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var result = db.Exists(name, countryId);
                db.Close();
                
                return result;
            }
        }
        
        return false;
    }
    
    @Override
    public void Put(Country country) {
        var db = new DBCountry(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(country);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(City city) {
        var db = new DBCity(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(city);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Country country) {
        var db = new DBCountry(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(country);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(City city) {
        var db = new DBCity(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(city);
                db.Close();
            }
        }
    }
    
    @Override
    public boolean CanDelete(Country country) {
        var db = new DBCity(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsCountryUsed(country.Id);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public boolean CanDelete(City city) {     
        var db = new DBAddress(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsCityUsed(city.Id);
                db.Close();
            }
        }
        
        return result;     
    }
}