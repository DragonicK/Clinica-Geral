package Engine.Handler;

import Engine.Person;
import Engine.Address;
import Engine.Contact;

import java.util.List;

public interface IPersonHandler {
    boolean DocumentExists(String document);
    List<Person> FindByName(String name);
    List<Person> FindByDocument(String document);
    List<Person> GetPersons();
    boolean CanDelete(Person person);
    void Delete(Person person);
    void Update(Person person);
    List<Address> GetAddresses(Person person);
    void UpdateAddresses(List<Address> addresses);
    List<Contact> GetPhones(Person person);
    void UpdatePhones(List<Contact> phones);
    void Put(Person person);
    void DeleteContacts(Person person);
    void DeleteAddresses(Person person);
}