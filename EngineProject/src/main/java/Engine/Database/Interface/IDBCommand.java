/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Engine.Database.Interface;

import Engine.Database.DBError;
import Engine.Database.DBType;

public interface IDBCommand {
    DBError CreateQuery(String query);
    
    void AddParameter(Object value, DBType type);
    
    void Clear();
    void Close();
    
    void ExecuteQuery();
    IDBReader ExecuteReader();
}
