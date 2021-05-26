package Engine.Database;

import Engine.Supplier;
import Engine.Common.Strings;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.List;
import java.util.ArrayList;

public class DBSupplier extends DBTemplate {
    
    public DBSupplier(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public void Put(Supplier supplier) {
        var query = "INSERT INTO Supplier (PersonId, FantasyName) VALUES (?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(supplier.PersonId, DBType.Int32);
            command.AddParameter(supplier.FantasyName, DBType.String);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Supplier supplier) {
        var query = "UPDATE Supplier Set PersonId = ?, FantasyName = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(supplier.PersonId , DBType.Int32);
            command.AddParameter(supplier.FantasyName , DBType.String);
            command.AddParameter(supplier.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Delete(Supplier supplier) {
        var query = "DELETE FROM Supplier WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(supplier.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Supplier> GetByDocument(String document) {
        var query = "SELECT Supplier.Id, Supplier.PersonId, Supplier.FantasyName, ";
        query += "Person.Name, Person.Document, Person.Email FROM Supplier ";
        query += "INNER JOIN Person ON Supplier.PersonId = Person.Id WHERE Person.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Supplier> GetByName(String name) {
        var query = "SELECT Supplier.Id, Supplier.PersonId, Supplier.FantasyName, ";
        query += "Person.Name, Person.Document, Person.Email FROM Supplier ";
        query += "INNER JOIN Person ON Supplier.PersonId = Person.Id WHERE Person.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Supplier> GetByFantasyName(String name) {
        var query = "SELECT Supplier.Id, Supplier.PersonId, Supplier.FantasyName, ";
        query += "Person.Name, Person.Document, Person.Email FROM Supplier ";
        query += "INNER JOIN Person ON Supplier.PersonId = Person.Id WHERE Supplier.FantasyName LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Supplier> GetSuppliers() {
        var query = "SELECT Supplier.Id, Supplier.PersonId, Supplier.FantasyName, ";
        query += "Person.Name, Person.Document, Person.Email FROM Supplier ";
        query += "INNER JOIN Person ON Supplier.PersonId = Person.Id";
        
        return Get(Strings.Empty, query);
    }
    
    private List<Supplier> Get(String param, String query) {
        var list = new ArrayList<Supplier>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var supplier = new Supplier();
                
                supplier.Id = (int)reader.GetData("Id", DBType.Int32);
                supplier.PersonId = (int)reader.GetData("PersonId", DBType.Int32);
                supplier.FantasyName = (String)reader.GetData("FantasyName", DBType.String);
                supplier.Name = (String)reader.GetData("Name", DBType.String);
                supplier.Document = (String)reader.GetData("Document", DBType.String);
                supplier.Email= (String)reader.GetData("Email", DBType.String);
                
                list.add(supplier);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public boolean IsPersonUsed(int personId) {
        var query = "SELECT Id FROM Supplier WHERE PersonId = ?";
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(personId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
}