package Engine.Database;

public class DBConfiguration {
    public String Host;
    public int Port;
    public String User;
    public String Passphrase;
    public String Database;   
    
    public DBConfiguration() {
        Port = 3306;
    }
    
    public String GetHostString() {
       return "jdbc:mysql://" + Host + ":" + Port + "/" + Database;
    }
}