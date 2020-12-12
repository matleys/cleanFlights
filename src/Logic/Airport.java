package Logic;

import java.time.ZoneOffset;

/**
 *
 * @author mathias
 */
public class Airport {
    private String airportCode;
    private String city;
    private ZoneOffset timeZone;

    public Airport(String code, String city, ZoneOffset timeZone) {
        this.airportCode = code;
        this.city = city;
        this.timeZone = timeZone;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getCity() {
        return city;
    }

    public ZoneOffset getTimeZone() {
        return timeZone;
    }
   
    @Override
    public String toString(){
        return "Airport "+ this.getAirportCode() + " located in " + this.getCity();
    }
   
}
