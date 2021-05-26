package Engine;

import Engine.Common.Strings;
import Engine.Database.DBOperationState;

public class Contact {
    public int Id;
    public int PersonId;
    public String Phone;
    
    // Indica o tipo de operação que será executado quando em contato com o banco.
    public DBOperationState OperationState;
    
    public Contact() {
        Phone = Strings.Empty;
        OperationState = DBOperationState.None;
    }
}