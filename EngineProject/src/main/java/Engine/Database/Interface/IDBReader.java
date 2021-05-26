package Engine.Database.Interface;

import Engine.Database.DBType;

public interface IDBReader {
    boolean MoveNext();
    Object GetData(String column, DBType type);
    void Close();
}