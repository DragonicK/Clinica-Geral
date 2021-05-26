package Engine.Handler;

import Engine.Patient;
import java.util.List;

public interface IPatientHandler {
    boolean CanSelect(int personId);
    boolean CanDelete(Patient patient);
    void Delete(Patient patient);
    void Put(Patient patient);
    void Update(Patient patient);   
    List<Patient> GetPatients();
    List<Patient> FindByName(String name);
    List<Patient> FindByDocument(String document);
}