package Engine.Database;

import Engine.Common.Strings;
import Engine.Person;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DBPerson extends DBTemplate {
    
    public DBPerson(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean Exists(String document) {
        var query = "SELECT Id FROM Person WHERE Document = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(document, DBType.String);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
    
    public void Delete(Person person) {
        var query = "DELETE FROM Person WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(person.Id, DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
        
    }
    
    public void Put(Person person) {
        var query = "INSERT INTO Person (Name, Document, Email, Birthday) VALUES (?, ?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(person.Name , DBType.String);
            command.AddParameter(person.Document , DBType.String);
            command.AddParameter(person.Email , DBType.String);
            command.AddParameter(person.Birthday , DBType.Date);
            command.ExecuteQuery();
            command.Close();
        }     
    }
    
    public void Update(Person person) {
        var query = "UPDATE Person SET Name = ?, Document = ?, Email = ?, Birthday = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(person.Name, DBType.String);
            command.AddParameter(person.Document, DBType.String);
            command.AddParameter(person.Email, DBType.String);
            command.AddParameter(person.Birthday, DBType.Date);
            command.AddParameter(person.Id, DBType.Int32);
            
            command.ExecuteQuery();
            command.Close();
        }
        
    }
    
    public int GetPersonId(String document) {
        var query = "SELECT Id FROM Person WHERE Document = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var personId = 0;
        
        if (error.Code == 0) {
            command.AddParameter(document, DBType.String);
            var reader = command.ExecuteReader();
            
            if (reader.MoveNext()) {
                personId = (int)reader.GetData("Id", DBType.Int32);
            }
            
            reader.Close();
            command.Close();
        }
        
        return personId;
    }
    
    public List<Person> GetByDocument(String document) {
        var query = "SELECT * FROM Person WHERE Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Person> GetByName(String name) {
        var query = "SELECT * FROM Person WHERE Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Person> Get() {
        var query = "SELECT * FROM Person";
        
        return Get(Strings.Empty, query);
    }
    
    private List<Person> Get(String param, String query) {
        var list = new ArrayList<Person>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var person = new Person();
                
                person.Id = (int)reader.GetData("Id", DBType.Int32);
                person.Name = (String)reader.GetData("Name", DBType.String);
                person.Document = (String)reader.GetData("Document", DBType.String);
                person.Email= (String)reader.GetData("Email", DBType.String);
                person.Birthday = (Date)reader.GetData("Birthday", DBType.Date);
                
                list.add(person);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
}