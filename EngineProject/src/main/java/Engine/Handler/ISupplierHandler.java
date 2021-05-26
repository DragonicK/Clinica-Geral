package Engine.Handler;

import Engine.Supplier;

import java.util.List;

public interface ISupplierHandler {
    boolean CanSelectPerson(int personId);
    boolean CanDelete(Supplier supplier);
    void Put(Supplier supplier);
    void Update(Supplier supplier);
    void Delete(Supplier supplier);
    List<Supplier> GetSuppliers();
    List<Supplier> FindByName(String name);
    List<Supplier> FindByDocument(String document);
    List<Supplier> FindByFantasyName(String name);
}