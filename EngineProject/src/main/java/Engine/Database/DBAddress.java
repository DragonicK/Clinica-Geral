package Engine.Database;

import Engine.Address;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class DBAddress extends DBTemplate {
    
    public DBAddress(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public void Put(Address address) {
        var query = "INSERT INTO Address (PersonId, Address, CityId) VALUES (?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(address.PersonId , DBType.Int32);
            command.AddParameter(address.Address , DBType.String);
            command.AddParameter(address.CityId , DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Delete(Address address) {
        var query = "DELETE FROM Address WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(address.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Address address) {
        var query = "UPDATE Address Set PersonId = ?, Address = ?, CityId = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(address.PersonId , DBType.Int32);
            command.AddParameter(address.Address , DBType.String);
            command.AddParameter(address.CityId , DBType.Int32);
            command.AddParameter(address.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void DeleteFromPersonId(int personId) {
        var query = "DELETE FROM Address WHERE PersonId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(personId , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Address> GetAddresses(int personId) {
        var list = new ArrayList<Address>();
        var query = "SELECT * FROM Address WHERE PersonId = ?";
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(personId, DBType.Int32);
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var address = new Address();
                
                address.Id = (int)reader.GetData("Id", DBType.Int32);
                address.PersonId = (int)reader.GetData("PersonId", DBType.Int32);
                address.Address = (String)reader.GetData("Address", DBType.String);
                address.CityId = (int)reader.GetData("CityId", DBType.Int32);
                
                list.add(address);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public boolean IsCityUsed(int cityId) {
        var query = "SELECT Id FROM Address WHERE CityId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(cityId , DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
}