package Engine.Handler;

import Engine.Schedule;
import Engine.Database.DBSchedule;
import Engine.Database.DBConfiguration;

import java.util.List;

public class ScheduleHandler implements IScheduleHandler {
    private final DBConfiguration configuration;
    
    public ScheduleHandler(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public List<Schedule> GetSchedules() {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetSchedule();
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean CanDelete(Schedule schedule) {
        return true;
    }
    
    @Override
    public void Delete(Schedule schedule) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Delete(schedule);
                db.Close();
            }
        }
    }
    
    @Override
    public void Update(Schedule schedule) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Update(schedule);
                db.Close();
            }
        }
    }
    
    @Override
    public void Put(Schedule schedule) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                db.Put(schedule);
                db.Close();
            }
        }
    }
    
    @Override
    public List<Schedule> FindByPatientDocument(String document) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByPatientDocument(document);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Schedule> FindByPatientName(String name) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByPatientName(name);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Schedule> FindByEmployeeDocument(String document) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByEmployeeDocument(document);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Schedule> FindByEmployeeName(String name) {
        var db = new DBSchedule(configuration);
        var error = db.Open();
        
        if (error.Code == 0) {
            if (db.IsOpen()) {
                var list = db.GetByEmployeeName(name);
                db.Close();
                
                return list;
            }
        }
        
        return null;
    }  
    
}