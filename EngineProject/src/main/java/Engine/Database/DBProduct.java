package Engine.Database;

import Engine.Product;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.List;
import java.util.ArrayList;

public class DBProduct extends DBTemplate {
    
    public DBProduct(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    public boolean Exists(int id) {
        var query = "SELECT Id FROM Product WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(id, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
    
    public boolean CodeExists(int code) {
        var query = "SELECT Id FROM Product WHERE Code = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(code, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
    
    public void Delete(Product product) {
        var query = "DELETE FROM Product WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.Id, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Put(Product product) {
        var query = "INSERT INTO Product (Code, Name, Price, Quantity, SupplierId) ";
        query += "VALUES (?, ?, ?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.Code, DBType.Int32);
            command.AddParameter(product.Name, DBType.String);
            command.AddParameter(product.Price, DBType.Decimal);
            command.AddParameter(product.Quantity, DBType.Int32);
            command.AddParameter(product.SupplierId, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public void Update(Product product) {
        var query = "UPDATE Product SET Code = ?, Name = ?, Price = ?, Quantity = ?, SupplierId = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.Code, DBType.Int32);
            command.AddParameter(product.Name, DBType.String);
            command.AddParameter(product.Price, DBType.Decimal);
            command.AddParameter(product.Quantity, DBType.Int32);
            command.AddParameter(product.SupplierId, DBType.Int32);
            command.AddParameter(product.Id, DBType.Int32);
            
            command.ExecuteQuery();
        }
        
        command.Close();
    }
    
    public List<Product> Get() {
        var query ="SELECT Product.*, Supplier.FantasyName FROM Product ";
        query += "INNER JOIN Supplier ON Product.SupplierId = Supplier.Id";
        
        var list = new ArrayList<Product>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var product = new Product();
                
                product.Id = (int)reader.GetData("Id", DBType.Int32);
                product.Code = (int)reader.GetData("Code", DBType.Int32);
                product.Name = (String)reader.GetData("Name", DBType.String);
                product.Price = (double)reader.GetData("Price", DBType.Decimal);
                product.Quantity = (int)reader.GetData("Quantity", DBType.Int32);
                product.SupplierId = (int)reader.GetData("SupplierId", DBType.Int32);
                product.SupplierName = (String)reader.GetData("FantasyName", DBType.String);
                
                list.add(product);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public List<Product> Get(String name) {
        var query ="SELECT Product.*, Supplier.FantasyName FROM Product ";
        query += "INNER JOIN Supplier ON Product.SupplierId = Supplier.Id ";
        query += "WHERE Name LIKE ?";
        
        var list = new ArrayList<Product>();
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter("%" + name + "%", DBType.String);
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var product = new Product();
                
                product.Id = (int)reader.GetData("Id", DBType.Int32);
                product.Code = (int)reader.GetData("Code", DBType.Int32);
                product.Name = (String)reader.GetData("Name", DBType.String);
                product.Price = (double)reader.GetData("Price", DBType.Decimal);
                product.Quantity = (int)reader.GetData("Quantity", DBType.Int32);
                product.SupplierId = (int)reader.GetData("SupplierId", DBType.Int32);
                product.SupplierName = (String)reader.GetData("FantasyName", DBType.String);
                
                list.add(product);
            }
            
            reader.Close();
            command.Close();
        }
        
        return list;
    }
    
    public boolean IsSupplierUsed(int supplierId) {
        var query = "SELECT Id FROM Product WHERE SupplierId = ?";
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(supplierId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            var result = reader.MoveNext();
            
            reader.Close();
            command.Close();
            
            return result;
        }
        
        return false;
    }
}