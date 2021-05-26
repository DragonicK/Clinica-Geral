package Engine.Handler;

import Engine.Product;

import java.util.List;

public interface IProductHandler {
    boolean Exists(int id);
    boolean CodeExists(int code);
    void Delete(Product product);
    void Put(Product product);
    void Update(Product product);   
    boolean CanDelete(Product product);
    List<Product> GetProducts();
    List<Product> Find(String name);
}