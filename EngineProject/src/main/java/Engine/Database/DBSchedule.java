package Engine.Database;

import Engine.Common.Strings;
import Engine.Schedule;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class DBSchedule extends DBTemplate {
    
    public DBSchedule(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public void Delete(Schedule schedule) {
        var query = "DELETE FROM Schedule WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(schedule.Id, DBType.Int32);
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Put(Schedule schedule) {
        var query = "INSERT INTO Schedule (PatientId, EmployeeId, Date) VALUES (?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(schedule.PatientId, DBType.Int32);
            command.AddParameter(schedule.EmployeeId, DBType.Int32);
            command.AddParameter(schedule.Date, DBType.Date);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Schedule schedule) {
        var query = "UPDATE Schedule SET PatientId = ?, EmployeeId = ?, Date = ?, State = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(schedule.PatientId, DBType.Int32);
            command.AddParameter(schedule.EmployeeId, DBType.Int32);
            command.AddParameter(schedule.Date, DBType.Date);
            command.AddParameter(schedule.State, DBType.Byte);
            command.AddParameter(schedule.Id, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Schedule> GetSchedule() {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id";
        
        return Get(Strings.Empty, query);
    }
    
    public List<Schedule> GetScheduleFromDate(Date date) {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id ";
        query += "WHERE Date >= ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var list = new ArrayList<Schedule>();
        
        if (error.Code == 0) {     
            command.AddParameter(date, DBType.Date);
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()){
                var schedule = new Schedule();
                
                schedule.Id = (int)reader.GetData("Id", DBType.Int32);
                schedule.PatientId= (int)reader.GetData("PatientId", DBType.Int32);
                schedule.EmployeeId = (int)reader.GetData("EmployeeId", DBType.Int32);
                schedule.Date = (Date)reader.GetData("Date", DBType.Date);
                schedule.State = (byte)reader.GetData("State", DBType.Byte);
                schedule.PatientName = (String)reader.GetData("PatientName", DBType.String);
                schedule.PatientDocument = (String)reader.GetData("PatientDocument", DBType.String);
                schedule.EmployeeName = (String)reader.GetData("EmployeeName", DBType.String);
                
                list.add(schedule);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public List<Schedule> GetByPatientName(String name) {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName, PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id ";
        query += "WHERE PatientPerson.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Schedule> GetByPatientDocument(String document) {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName,  PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id ";
        query += "WHERE PatientPerson.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    public List<Schedule> GetByEmployeeName(String name) {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName,  PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id ";
        query += "WHERE EmployeePerson.Name LIKE ?";
        
        return Get("%" + name + "%", query);
    }
    
    public List<Schedule> GetByEmployeeDocument(String document) {
        var query = "SELECT Schedule.*, PatientPerson.Name AS PatientName,  PatientPerson.Document AS PatientDocument, EmployeePerson.Name AS EmployeeName FROM Schedule ";
        query += "INNER JOIN Employee ";
        query += "INNER JOIN Person AS PatientPerson ON PatientId = PatientPerson.Id ";
        query += "INNER JOIN Person as EmployeePerson ON Employee.PersonId = EmployeePerson.Id ";
        query += "WHERE EmployeePerson.Document LIKE ?";
        
        return Get("%" + document + "%", query);
    }
    
    private List<Schedule> Get(String param, String query) {
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var list = new ArrayList<Schedule>();
        
        if (error.Code == 0) {
            if (param.length() > 0) {
                command.AddParameter(param, DBType.String);
            }
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()){
                var schedule = new Schedule();
                
                schedule.Id = (int)reader.GetData("Id", DBType.Int32);
                schedule.PatientId= (int)reader.GetData("PatientId", DBType.Int32);
                schedule.EmployeeId = (int)reader.GetData("EmployeeId", DBType.Int32);
                schedule.Date = (Date)reader.GetData("Date", DBType.Date);
                schedule.State = (byte)reader.GetData("State", DBType.Byte);
                schedule.PatientName = (String)reader.GetData("PatientName", DBType.String);
                schedule.PatientDocument = (String)reader.GetData("PatientDocument", DBType.String);
                schedule.EmployeeName = (String)reader.GetData("EmployeeName", DBType.String);
                
                list.add(schedule);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
}