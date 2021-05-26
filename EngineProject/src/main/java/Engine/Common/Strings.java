package Engine.Common;

public final class Strings {
    public static String Empty = "";
    
    public static boolean HasLetters(String text) {
        if (text == null) {
            return false;
        }
        
        var len = text.length();
        
        for (int i = 0; i < len; i++) {         
            if ((Character.isLetter(text.charAt(i)) == true)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static String ConvertToDocument(String text) {
        return text.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}