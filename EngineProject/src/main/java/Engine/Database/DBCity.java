package Engine.Database;

import Engine.City;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class DBCity extends DBTemplate {
    
    public DBCity(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean Exists(String name, int countryId) {
        var query = "SELECT Id FROM City WHERE Name = ? AND CountryId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(name, DBType.String);
            command.AddParameter(countryId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
    
    public void Delete(City city) {
        var query = "DELETE FROM City WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(city.Id, DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Put(City city) {
        var query = "INSERT INTO City (Name, CountryId) VALUES (?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(city.Name , DBType.String);
            command.AddParameter(city.CountryId , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(City city) {
        var query = "UPDATE City SET Name = ?, CountryId = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(city.Name, DBType.String);
            command.AddParameter(city.CountryId, DBType.Int32);
            command.AddParameter(city.Id, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
        
    public List<City> Get() {
        var query = "SELECT City.Id, City.Name, City.CountryId, Country.Name as CountryName ";
        query += "FROM City INNER JOIN Country ON City.CountryId = Country.Id";
        
        var list = new ArrayList<City>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var city = new City();
                
                city.Id = (int)reader.GetData("Id", DBType.Int32);
                city.Name = (String)reader.GetData("Name", DBType.String);
                city.CountryId = (int)reader.GetData("CountryId", DBType.Int32);
                city.CountryName = (String)reader.GetData("CountryName", DBType.String);
                
                list.add(city);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public boolean IsCountryUsed(int countryId) {
        var query = "SELECT Id FROM City WHERE CountryId = ?";
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(countryId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            command.Close();
            reader.Close();
            
            return result;
        }
        
        return false;
    }
}