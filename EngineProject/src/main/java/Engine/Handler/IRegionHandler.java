package Engine.Handler;

import Engine.Country;
import Engine.City;

import java.util.List;

public interface IRegionHandler {
    boolean ExistsCountry(String name);
    boolean ExistsCity(String name, int countryId);
    void Delete(Country country);
    void Delete(City city);
    void Put(Country country);
    void Put(City city);
    void Update(Country country);
    void Update(City city);
    List<Country> GetCountries();
    List<City> GetCities(); 
    boolean CanDelete(Country country);
    boolean CanDelete(City city);
}