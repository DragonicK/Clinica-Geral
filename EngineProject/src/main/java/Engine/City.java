package Engine;

import Engine.Common.Strings;

public class City {
    public int Id;
    public String Name;
    public int CountryId;
    public String CountryName;
    
    public City() {
        Name = Strings.Empty;
        CountryName = Strings.Empty;  
    }
}