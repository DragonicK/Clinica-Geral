package Engine.Database;

import Engine.Contact;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class DBContact extends DBTemplate  {
    
    public DBContact(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public void Put(Contact contact) {
        var query = "INSERT INTO Contact (PersonId, Phone) VALUES (?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(contact.PersonId , DBType.Int32);
            command.AddParameter(contact.Phone , DBType.String);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Delete(Contact contact) {
        var query = "DELETE FROM Contact WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(contact.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Contact contact) {
        var query = "UPDATE Contact Set PersonId = ?, Phone = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(contact.PersonId , DBType.Int32);
            command.AddParameter(contact.Phone , DBType.String);
            command.AddParameter(contact.Id , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void DeleteFromPersonId(int personId) {
        var query = "DELETE FROM Contact WHERE PersonId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(personId , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Contact> GetContacts(int personId) {
        var list = new ArrayList<Contact>();
        var query = "SELECT * FROM Contact WHERE PersonId = ?";
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(personId, DBType.Int32);
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var contact = new Contact();
                
                contact.Id = (int)reader.GetData("Id", DBType.Int32);
                contact.PersonId = (int)reader.GetData("PersonId", DBType.Int32);
                contact.Phone = (String)reader.GetData("Phone", DBType.String);
                
                list.add(contact);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
}