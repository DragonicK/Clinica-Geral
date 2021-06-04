package Engine.Handler;

import Engine.Employee;
import java.util.List;

public interface IEmployeeHandler {
    boolean CanSelect(int personId);
    boolean CanDelete(Employee employee);
    void Update(Employee employee);
    void Put(Employee employee);
    void Delete(Employee employee);
    List<Employee> GetEmployees();
    List<Employee> FindByName(String name);
    List<Employee> FindByDocument(String document);
}