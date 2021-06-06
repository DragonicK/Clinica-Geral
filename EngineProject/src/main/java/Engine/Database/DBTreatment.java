package Engine.Database;

import Engine.Treatment;
import Engine.Common.Strings;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DBTreatment extends DBTemplate {
    
    public DBTreatment(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public void Put(Treatment treatment) {
        var query = "INSERT INTO Treatment (ScheduleId) VALUES (?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(treatment.ScheduleId , DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Update(Treatment treatment) {
        var query = "UPDATE Treatment SET ScheduleId = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(treatment.ScheduleId, DBType.Int32);
            command.AddParameter(treatment.Id, DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Delete(Treatment treatment) {
        var query = "DELETE FROM Treatment WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(treatment.Id , DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public List<Treatment> GetTreatments(){
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id";
        
        return Get(Strings.Empty, query);
    }
    
    public List<Treatment> GetTreatmentsFromDate(Date startDate, Date endDate) {
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id ";
        query += "WHERE Date BETWEEN ? AND ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var list = new ArrayList<Treatment>();
        
        if (error.Code == 0) {
            command.AddParameter(startDate, DBType.Date);
            command.AddParameter(endDate, DBType.Date);
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()){
                var treatment = new Treatment();
                
                treatment.Id = (int)reader.GetData("Id", DBType.Int32);
                treatment.ScheduleId = (int)reader.GetData("ScheduleId", DBType.Int32);
                treatment.Date = (Date)reader.GetData("Date", DBType.Date);
                treatment.State = (byte)reader.GetData("State", DBType.Byte);
                treatment.PatientName = (String)reader.GetData("PatientName", DBType.String);
                treatment.PatientDocument = (String)reader.GetData("PatientDocument", DBType.String);
                treatment.EmployeeName = (String)reader.GetData("EmployeeName", DBType.String);
                
                list.add(treatment);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public List<Treatment> GetByPatientName(String name) {
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id ";
        query += "WHERE PatientPerson.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Treatment> GetByPatientDocument(String document) {
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id ";
        query += "WHERE PatientPerson.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Treatment> GetByEmployeeName(String name) {
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id ";
        query += "WHERE EmployeePerson.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Treatment> GetByEmployeeDocument(String document) {
        var query = "SELECT Treatment.Id, Treatment.ScheduleId, Schedule.Date, Schedule.State, ";
        query += "PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, ";
        query += "EmployeePerson.Name AS EmployeeName FROM Treatment ";
        query += "INNER JOIN Schedule ON Schedule.State = 1 AND Schedule.Id = Treatment.ScheduleId ";
        query += "INNER JOIN Person as PatientPerson ON Schedule.PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Schedule.EmployeeId = EmployeePerson.Id ";
        query += "WHERE EmployeePerson.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Treatment> Get(String param, String query) {
        var list = new ArrayList<Treatment>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var treatment = new Treatment();
                
                treatment.Id = (int)reader.GetData("Id", DBType.Int32);
                treatment.ScheduleId = (int)reader.GetData("ScheduleId", DBType.Int32);
                treatment.Date = (Date)reader.GetData("Date", DBType.Date);
                treatment.State = (byte)reader.GetData("State", DBType.Byte);
                treatment.PatientName = (String)reader.GetData("PatientName", DBType.String);
                treatment.PatientDocument = (String)reader.GetData("PatientDocument", DBType.String);
                treatment.EmployeeName = (String)reader.GetData("EmployeeName", DBType.String);
                
                list.add(treatment);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
}