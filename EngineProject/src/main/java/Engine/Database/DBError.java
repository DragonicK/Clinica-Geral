package Engine.Database;

import Engine.Common.Strings;

public class DBError {
    public int Code;
    public String Message;    
    
    public DBError() {
        Message = Strings.Empty;
    }
}