package Engine.Handler;

import Engine.Person;
import Engine.Address;
import Engine.Contact;
import Engine.Database.DBPerson;
import Engine.Database.DBAddress;
import Engine.Database.DBContact;
import Engine.Database.DBSupplier;
import Engine.Database.DBPatient;
import Engine.Database.DBEmployee;
import Engine.Database.DBConfiguration;

import java.util.List;

public class PersonHandler implements IPersonHandler {
    private DBConfiguration configuration;
    
    public PersonHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public boolean DocumentExists(String document) {
        var db = new DBPerson(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = db.Exists(document);
                db.Close();
            }
        }
        
        return result;
    }
    
    @Override
    public List<Person> FindByName(String name) {
        var db = new DBPerson(configuration);
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
    public List<Person> FindByDocument(String document) {
        var db = new DBPerson(configuration);
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
    public List<Person> GetPersons() {
        var db = new DBPerson(configuration);
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
    public boolean CanDelete(Person person) {
        if (IsPersonUsedAsSupplier(person)) {
            return false;
        }
        
        if (IsPersonUsedAsPatient(person)) {
            return false;
        }
        
        if (IsPersonUsedAsEmployee(person)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public void Delete(Person person) {
        var db = new DBPerson(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(person);
                db.Close();
            }
        }
    }
    
    @Override
    public void DeleteContacts(Person person) {
        var db = new DBContact(configuration);
        var error = db.Open();
        
        if (error.Code ==  0) {
            if (db.IsOpen()) {
                db.DeleteFromPersonId(person.Id);
                db.Close();
            }
        }
    }
    
    @Override
    public void DeleteAddresses(Person person) {
        var db = new DBAddress(configuration);
        var error = db.Open();
        
        if (error.Code ==  0) {
            if (db.IsOpen()) {
                db.DeleteFromPersonId(person.Id);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Person person) {
        var db = new DBPerson(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(person);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Address> GetAddresses(Person person) {
        var db = new DBAddress(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetAddresses(person.Id);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public void UpdateAddresses(List<Address> addresses) {
        var db = new DBAddress(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var count = addresses.size();
                
                for (var i = 0; i < count; ++i) {
                    var address = addresses.get(i);
                    
                    switch (address.OperationState){
                        case Delete -> db.Delete(address);
                        case Update -> db.Update(address);
                        case Insert -> db.Put(address);
                    }
                }
                
                db.Close();
            }
        }
    }
    
    @Override
    public List<Contact> GetPhones(Person person) {
        var db = new DBContact(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetContacts(person.Id);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public void UpdatePhones(List<Contact> contacts) {
        var db = new DBContact(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var count = contacts.size();
                
                for (var i = 0; i < count; ++i) {
                    var contact = contacts.get(i);
                    
                    switch (contact.OperationState){
                        case Delete -> db.Delete(contact);
                        case Update -> db.Update(contact);
                        case Insert -> db.Put(contact);
                    }
                }
                
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Person person) {
        var db = new DBPerson(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(person);
                
                var id = db.GetPersonId(person.Document);
                person.UpdatePersonId(id);
                
                db.Close();
            }
        }
    }
    
    private boolean IsPersonUsedAsSupplier(Person person) {
        var db = new DBSupplier(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsPersonUsed(person.Id);
                db.Close();
            }
        }
        
        return result;
    }
    
    private boolean IsPersonUsedAsPatient(Person person) {
        var db = new DBPatient(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsPersonUsed(person.Id);
                db.Close();
            }
        }
        
        return result;
    }
    
    private boolean IsPersonUsedAsEmployee(Person person) {
        var db = new DBEmployee(configuration);
        var error = db.Open();
        var result = false;
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                result = !db.IsPersonUsed(person.Id);
                db.Close();
            }
        }
        
        return result;
    }    
}