package Engine;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Person {
    public int Id;
    public String Name;
    public String Document;
    public String Email;
    public Date Birthday;
    
    public List<Address> Addresses;
    public List<Contact> Contacts;
    
    public Person() {
        Addresses = new ArrayList<>();
        Contacts = new ArrayList<>();
    }
    
    public void UpdatePersonId(int id) {
        Id = id;
        
        var count = Addresses.size();
        
        for (var i = 0; i < count; ++i) {
            var address = Addresses.get(i);
            address.PersonId = id;
        }
        
        count = Contacts.size();
        
        for (var i = 0; i < count; ++i) {
            var contact = Contacts.get(i);
            contact.PersonId = id;
        } 
    }  
}