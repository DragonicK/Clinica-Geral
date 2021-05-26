package Engine.Common;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Hash {
    
    public String Compute(String value) {
        try{
            var digest = MessageDigest.getInstance("SHA-256");
            var encoded = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            
            return GetHex(encoded);
        }
        catch (Exception ex){
            return Strings.Empty;
        }
    }
    
    private String GetHex(byte[] array) {
        if (array != null) {
            var builder = new StringBuilder(array.length * 2);
            
            for (var i = 0; i < array.length; i++){
                builder.append(String.format("%X", array[i]));
            }
            
            return builder.toString();
        }
        
        return Strings.Empty;
    }
    
}