package Engine.Database;

import Engine.Database.DBConfiguration;
import Engine.Database.Interface.IDBConnection;

public abstract class DBTemplate {
    protected IDBConnection connection;      
    protected DBConfiguration configuration;
     
    public DBError Open() {
        return connection.Open();
    }
    
    public boolean IsOpen() {
        return connection.IsOpen();
    }
    
    public void Close() {
        connection.Close();
    }
}