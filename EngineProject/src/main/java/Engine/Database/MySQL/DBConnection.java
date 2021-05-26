package Engine.Database.MySQL;

import Engine.Database.DBConfiguration;
import Engine.Database.DBError;
import Engine.Database.Interface.IDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements IDBConnection {
    
    private Connection connection;
    private final DBConfiguration configuration;
    
    public DBConnection(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
    }
    
    @Override
    public DBError Open(){
        var error= new DBError();
                    
        var host = configuration.GetHostString();
        var user = configuration.User;
        var passphrase = configuration.Passphrase;
        
        try {         
            if (!IsOpen()) {            
                connection = DriverManager.getConnection(host, user, passphrase);
            }
        }
        catch (SQLException ex) {                      
            error.Code = ex.getErrorCode();
            error.Message = ex.getMessage();
        }
        
        return error;
    }
    
    @Override
    public boolean IsOpen() {
        try {
            if (connection != null) {
                return !connection.isClosed();
            }
        }
        catch (SQLException ex) {
            
        }
        
        return false;
    }
    
    @Override
    public void Close() {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException ex) {
            
        }
    } 
    
    public Connection GetConnection() {
        return connection;
    }
}