package Engine.Database;

import Engine.Country;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class DBCountry extends DBTemplate {
    
    public DBCountry(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean Exists(String name) {
        var query = "SELECT Id FROM Country WHERE Name = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(name, DBType.String);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
    
    public void Delete(int id) {
        var query = "DELETE FROM Country WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(id, DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Put(Country country) {
        var query = "INSERT INTO Country (Name) VALUES (?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(country.Name , DBType.String);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Country country) {
        var query = "UPDATE Country SET Name = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(country.Name, DBType.String);
            command.AddParameter(country.Id, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }

    public List<Country> Get() {
        var list = new ArrayList<Country>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery("SELECT * FROM Country");
        
        if (error.Code == 0) {
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var country = new Country();
                
                country.Id = (int)reader.GetData("Id", DBType.Int32);
                country.Name = (String)reader.GetData("Name", DBType.String);
        
                list.add(country);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
 
}