package Engine.Database;

import Engine.Patient;
import Engine.Common.Strings;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class DBPatient extends DBTemplate {
    
    public DBPatient(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean IsPersonAsPatient(int personId) {
        var query = "SELECT Id FROM Patient WHERE PersonId = ?";
        
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
    
    public void Delete(Patient patient) {
        var query = "DELETE FROM Patient WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(patient.Id, DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Put(Patient patient) {
        var query = "INSERT INTO Patient (PersonId, CompanionPersonId) VALUES (?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(patient.PersonId , DBType.Int32);
            command.AddParameter(patient.CompanionPersonId , DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Patient patient) {
        var query = "UPDATE Patient SET PersonId = ?, CompanionPersonId = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(patient.PersonId, DBType.Int32);
            command.AddParameter(patient.CompanionPersonId, DBType.Int32);
            command.AddParameter(patient.Id, DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Patient> GetByDocument(String document) {
        var query = "SELECT Patient.Id, Patient.PersonId, Patient.CompanionPersonId, ";
        query += "PatientPerson.Name As PatientName, PatientPerson.Document as PatientDocument, PatientPerson.Email as PatientEmail, ";
        query += "Companion.Name as CompanionName, Companion.Document as CompanionDocument, Companion.Email as CompanionEmail ";
        query += "FROM Patient ";
        query += "INNER JOIN Person as PatientPerson ON Patient.PersonId = PatientPerson.Id ";
        query += "INNER JOIN Person as Companion ON Patient.CompanionPersonId = Companion.Id ";
        query += "WHERE PatientPerson.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Patient> GetByName(String name) {
        var query = "SELECT Patient.Id, Patient.PersonId, Patient.CompanionPersonId, ";
        query += "PatientPerson.Name As PatientName, PatientPerson.Document as PatientDocument, PatientPerson.Email as PatientEmail, ";
        query += "Companion.Name as CompanionName, Companion.Document as CompanionDocument, Companion.Email as CompanionEmail ";
        query += "FROM Patient ";
        query += "INNER JOIN Person as PatientPerson ON Patient.PersonId = PatientPerson.Id ";
        query += "INNER JOIN Person as Companion ON Patient.CompanionPersonId = Companion.Id ";
        query += "WHERE PatientPerson.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Patient> GetPatients() {
        var query = "SELECT Patient.Id, Patient.PersonId, Patient.CompanionPersonId, ";
        query += "PatientPerson.Name As PatientName, PatientPerson.Document as PatientDocument, PatientPerson.Email as PatientEmail, ";
        query += "Companion.Name as CompanionName, Companion.Document as CompanionDocument, Companion.Email as CompanionEmail ";
        query += "FROM Patient ";
        query += "INNER JOIN Person as PatientPerson ON Patient.PersonId = PatientPerson.Id ";
        query += "INNER JOIN Person as Companion ON Patient.CompanionPersonId = Companion.Id ";
        
        return Get(Strings.Empty, query);
    }
       
    private List<Patient> Get(String param, String query) {
        var list = new ArrayList<Patient>();
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var patient = new Patient();
                
                patient.Id = (int)reader.GetData("Id", DBType.Int32);
                patient.PersonId = (int)reader.GetData("PersonId", DBType.Int32);
                patient.CompanionPersonId = (int)reader.GetData("CompanionPersonId", DBType.Int32);
                patient.Name = (String)reader.GetData("PatientName", DBType.String);
                patient.Document = (String)reader.GetData("PatientDocument", DBType.String);
                patient.Email = (String)reader.GetData("PatientEmail", DBType.String);
                patient.CompanionName = (String)reader.GetData("CompanionName", DBType.String);
                patient.CompanionDocument = (String)reader.GetData("CompanionDocument", DBType.String);
                patient.CompanionEmail = (String)reader.GetData("CompaniontEmail", DBType.String);               
                
                list.add(patient);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
}