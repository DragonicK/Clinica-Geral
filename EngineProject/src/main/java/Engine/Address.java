package Engine;

import Engine.Common.Strings;
import Engine.Database.DBOperationState;

public class Address {
    public int Id;
    public int PersonId;
    public String Address;
    public int CityId;   
    
    // Indica o tipo de operação que será executado quando em contato com o banco.
    public DBOperationState OperationState;
    
    public Address() {
        Address = Strings.Empty;
        OperationState= DBOperationState.None;
    }
}