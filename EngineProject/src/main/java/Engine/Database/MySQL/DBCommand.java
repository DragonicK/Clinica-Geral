package Engine.Database.MySQL;

import Engine.Database.DBType;
import Engine.Database.DBError;
import Engine.Database.Interface.IDBReader;
import Engine.Database.Interface.IDBCommand;
import Engine.Database.Interface.IDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class DBCommand implements IDBCommand {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int paramCount;
    
    public DBCommand(IDBConnection dbConnection) {
        connection = dbConnection.GetConnection();
    }
    
    @Override
    public DBError CreateQuery(String query) {
        var error = new DBError();
        
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException ex) {
            error.Code = ex.getErrorCode();
            error.Message = ex.getMessage();
        }
        
        return error;
    }
    
    @Override
    public void ExecuteQuery() {
        try {
            preparedStatement.execute();
        }
        catch (SQLException ex) {
            System.out.print(ex.getMessage());
                 
        }     
    }
    
    /**
     * 
     * @return a new db reader.
     */
    @Override
    public IDBReader ExecuteReader() {
        try {
            return new DBReader(preparedStatement.executeQuery());
        }
        catch (SQLException ex){
            
        }
        
        return null;
    }
    
    @Override
    public void AddParameter(Object value, DBType type) {
        paramCount++;
        
        try {
            switch (type) {
                case Boolean -> {
                    preparedStatement.setBoolean(paramCount, (boolean)value);
                }
                case Byte -> {
                    preparedStatement.setByte(paramCount, (byte)value);
                }
                case Int16 -> {
                    preparedStatement.setShort(paramCount, (short)value);
                }
                case Int32 -> {
                    preparedStatement.setInt(paramCount, (int)value);
                }
                case Int64 -> {
                    preparedStatement.setLong(paramCount, (long)value);
                }
                case Decimal -> {
                    preparedStatement.setDouble(paramCount, (double)value);
                }
                case String -> {
                    preparedStatement.setString(paramCount, (String)value);
                }
                case Date -> {
                    preparedStatement.setDate(paramCount, ConvertDate((java.util.Date)value));
                }
            }
        }
        catch (SQLException ex) {
        }
    }
    
    @Override
    public void Clear() {
        paramCount = 0;
        
        try {
            if (preparedStatement != null) {
                preparedStatement.clearParameters();
            }
        } catch (SQLException ex) {

        }
    }
    
    @Override
    public void Close() {
        try {
            preparedStatement.close();
        }
        catch (SQLException ex) {
            
        }
    }    
    
    private Date ConvertDate(java.util.Date date) {
        return new Date(date.getTime());
    }
}