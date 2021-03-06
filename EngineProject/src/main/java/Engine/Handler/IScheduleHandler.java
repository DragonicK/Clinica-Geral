package Engine.Handler;

import Engine.Schedule;

import java.util.List;
import java.util.Date;

public interface IScheduleHandler {   
    Schedule Get(int id);
    List<Schedule> GetSchedules();
    List<Schedule> FindByDate(Date date);
    List<Schedule> FindByPatientDocument(String document);
    List<Schedule> FindByPatientName(String name);
    List<Schedule> FindByEmployeeDocument(String document);
    List<Schedule> FindByEmployeeName(String name);
    
    boolean CanDelete(Schedule schedule);
    void Delete(Schedule schedule);
    void Update(Schedule schedule);
    void Put(Schedule schedule);
}