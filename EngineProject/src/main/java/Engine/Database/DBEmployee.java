package Engine.Database;

import Engine.Common.Strings;
import Engine.Employee;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

public class DBEmployee extends DBTemplate {
    
    public DBEmployee(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean IsPersonUsed(int personId) {
        var query = "UPDATE Id FROM Employee WHERE PersonId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var result = false;
        
        if (error.Code == 0) {
            command.AddParameter(personId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            result = reader.MoveNext();
            
            reader.Close();
            command.Close();
        }
        
        return result;
    }
    
    public void Put(Employee employee) {
        var query = "INSERT INTO Employee (PersonId, AdmissionDate, Role) VALUES (?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(employee.PersonId , DBType.Int32);
            command.AddParameter(employee.AdmissionDate , DBType.Date);
            command.AddParameter(employee.Role , DBType.String);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Update(Employee employee) {
        var query = "UPDATE Employee SET PersonId = ?, AdmissionDate = ?, Role = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(employee.PersonId , DBType.Int32);
            command.AddParameter(employee.AdmissionDate , DBType.Date);
            command.AddParameter(employee.Role , DBType.String);
            command.AddParameter(employee.Id , DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Delete(Employee employee) {
        var query = "DELETE FROM Employee WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(employee.Id , DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public List<Employee> GetByName(String name) {
        var query = "SELECT Employee.*, Person.Name, Person.Document FROM Employee ";
        query += "INNER JOIN Person ON Employee.PersonId = Person.Id WHERE Person.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Employee> GetByDocument(String name) {
        var query = "SELECT Employee.*, Person.Name, Person.Document FROM Employee ";
        query += "INNER JOIN Person ON Employee.PersonId = Person.Id WHERE Person.Document LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Employee> GetEmployees() {
        var query = "SELECT Employee.*, Person.Name, Person.Document FROM Employee ";
        query += "INNER JOIN Person ON Employee.PersonId = Person.Id";
        
        return Get(Strings.Empty, query);
    }
    
    private List<Employee> Get(String param, String query) {
        var list = new ArrayList<Employee>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var employee = new Employee();
                
                employee.Id = (int)reader.GetData("Id", DBType.Int32);
                employee.PersonId = (int)reader.GetData("PersonId", DBType.Int32);
                employee.AdmissionDate = (Date)reader.GetData("AdmissionDate", DBType.Date);
                employee.Role = (String)reader.GetData("Role", DBType.String);
                employee.Name = (String)reader.GetData("Name", DBType.String);
                employee.Document = (String)reader.GetData("Document", DBType.String);
                
                list.add(employee);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
}