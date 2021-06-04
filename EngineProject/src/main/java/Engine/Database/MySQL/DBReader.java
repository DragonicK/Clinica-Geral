package Engine.Database.MySQL;

import Engine.Database.DBType;
import Engine.Database.Interface.IDBReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

public class DBReader implements IDBReader {
    private ResultSet resultSet;
    
    public DBReader(ResultSet result){
        resultSet = result;
    }
    
    @Override
    public boolean MoveNext() {
        try {
            return resultSet.next();
        }
        catch (SQLException ex){
            
        }
        
        return false;
    }
    
    @Override
    public Object GetData(String column, DBType type) {
        try {
            switch (type) {
                case Boolean -> {
                    return resultSet.getBoolean(column);
                }
                case Byte -> {
                    return resultSet.getByte(column);
                }
                case Int16 -> {
                    return resultSet.getShort(column);
                }
                case Int32 -> {
                    return resultSet.getInt(column);
                }
                case Int64 -> {
                    return resultSet.getLong(column);
                }
                case Decimal -> {
                    return resultSet.getDouble(column);
                }
                case String -> {
                    return resultSet.getString(column);
                }
                case Date -> {
                    return ConvertDate(resultSet.getTimestamp(column));
                }
            }
        }
        catch (SQLException ex) {
            
        }
        
        return null;
    }
    
    @Override
    public void Close() {
        try {
            resultSet.close();
        }
        catch (SQLException ex){
            
        }
    }
    
    private Date ConvertDate(java.sql.Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
}