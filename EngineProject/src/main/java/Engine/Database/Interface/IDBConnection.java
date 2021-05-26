package Engine.Database.Interface;

import Engine.Database.DBError;
import java.sql.Connection;

public interface IDBConnection {
    DBError Open();
    boolean IsOpen();
    void Close(); 
    Connection GetConnection();
}