package Engine.Database;

import Engine.TreatmentProduct;
import Engine.Database.MySQL.DBCommand;
import Engine.Database.MySQL.DBConnection;

import java.util.List;
import java.util.ArrayList;

public class DBTreatmentProduct extends DBTemplate {
    
    public DBTreatmentProduct(DBConfiguration dbConfiguration) {
        configuration = dbConfiguration;
        connection = new DBConnection(dbConfiguration);
    }
    
    // Verifica se od id de um produto está sendo usado no tratamento.
    public boolean IsProductUsed(int productId) {
        var query = "SELECT Id FROM Treatment_Product WHERE ProductId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var result = false;
        
        if (error.Code == 0) {
            command.AddParameter(productId, DBType.Int32);
            
            var reader = command.ExecuteReader();     
            result = reader.MoveNext();
            
            reader.Close();
            command.Close();
        }
        
        return result;
    }
    
    public List<TreatmentProduct> GetProducts(int treatmentId) {
        var query = "SELECT Treatment_Product.*, Product.Code, Product.Name, Product.Price FROM Treatment_Product ";
        query += "INNER JOIN Product ON Product.Id = Treatment_Product.ProductId ";
        query += "WHERE Treatment_Product.TreatmentId = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        var list = new ArrayList<TreatmentProduct>();
        
        if (error.Code == 0) {
            command.AddParameter(treatmentId, DBType.Int32);
            
            var reader = command.ExecuteReader();
            
            while (reader.MoveNext()) {
                var product = new TreatmentProduct();
                
                product.Id = (int)reader.GetData("Id", DBType.Int32);
                product.TreatmentId = (int)reader.GetData("TreatmentId", DBType.Int32);
                product.ProductId = (int)reader.GetData("ProductId", DBType.Int32);
                product.Count = (int)reader.GetData("ProductCount", DBType.Int32);
                product.Code = (int)reader.GetData("Code", DBType.Int32);
                product.Name = (String)reader.GetData("Name", DBType.String);
                product.Price = (double)reader.GetData("Price", DBType.Decimal);
                product.OperationState = DBOperationState.None;
                
                list.add(product);
            }
            
            command.Close();
        }
        
        return list;
    }
    
    public void Delete(TreatmentProduct product) {
        var query = "DELETE FROM Treatment_Product WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.Id , DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Update(TreatmentProduct product) {
        var query = "UPDATE Treatment_Product SET TreatmentId = ?, ProductId = ?, ProductCount = ? WHERE Id = ?";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.TreatmentId, DBType.Int32);
            command.AddParameter(product.ProductId, DBType.Int32);
            command.AddParameter(product.Count, DBType.Int32);
            command.AddParameter(product.Id, DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
    public void Put(TreatmentProduct product) {
        var query = "INSERT INTO Treatment_Product (TreatmentId, ProductId, ProductCount) VALUES (?, ?, ?)";
        
        var command = new DBCommand(connection);
        var error = command.CreateQuery(query);
        
        if (error.Code == 0) {
            command.AddParameter(product.TreatmentId, DBType.Int32);
            command.AddParameter(product.ProductId, DBType.Int32);
            command.AddParameter(product.Count, DBType.Int32);
            command.ExecuteQuery();
            command.Close();
        }
    }
    
}