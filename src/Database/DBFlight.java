
package Database;

import Logic.Airline;
import Logic.Airport;
import Logic.Flight;
import Logic.TravelAgency;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author mathias
 */
public class DBFlight {
   
    //returns a flight with a specified flight number
        public static Flight getFlight(String flightNumber) throws DBException {
    
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT flightNumber, airlineCode, airportOriginCode, airportDestinationCode, emissionPerKm, flightPrice, arrivalTime, departureTime, duration, distance, airlineCode " +
            "FROM flight " +
            "WHERE flightNumber = \""+flightNumber+"\"";

      ResultSet srs = stmt.executeQuery(sql);
      double emissionPerKm, distance;
      Airport Origin = null, Destination = null;
      Airline airline = null;
      String originCode, destCode;
      String airlineCode;
      double flightPrice;
      LocalTime departureTime, arrivalTime;
      Time depsql, arrsql;
      
      if (srs.next()) {
	emissionPerKm = srs.getDouble("emissionPerKm");
        distance = srs.getDouble("distance");
        originCode = srs.getString("airportOriginCode");
        destCode = srs.getString("airportDestinationCode");
        for(Airport a: TravelAgency.getAirports()){
            if(a.getAirportCode().equals(originCode))
                Origin = a;
            if(a.getAirportCode().equals(destCode))
                Destination = a;
        }
        flightPrice = srs.getDouble("flightprice");
        depsql = (Time) srs.getTime("departureTime");
        departureTime = depsql.toLocalTime();
        arrsql = srs.getTime("arrivalTime");
        arrivalTime = arrsql.toLocalTime();
        airlineCode = srs.getString("airlineCode");
        for(Airline a: TravelAgency.getAirlines()){
            if(a.getAirlineCode().equals(airlineCode))
                airline = a;
        }
        Flight f = new Flight(flightNumber,airline, emissionPerKm,Origin,Destination,flightPrice,departureTime,arrivalTime, distance);
        DBConnector.closeConnection(con);
        return f;
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
   
    //returns all flights in the database
    public static ArrayList<Flight> getFlights() throws DBException {
    
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT flightNumber, airlineCode, airportOriginCode, airportDestinationCode, emissionPerKm, flightPrice, arrivalTime, departureTime, duration, distance, airlineCode " +
            "FROM flight";

      ResultSet srs = stmt.executeQuery(sql);
      ArrayList<Flight> flights = new ArrayList<>();
      double emissionPerKm, distance;
      Airport Origin = null, Destination = null;
      Airline airline = null;
      String originCode, destCode;
      String flightNumber;
      double flightPrice;
      LocalTime departureTime, arrivalTime;
      Time depsql, arrsql;
      String airlineCode;
      
      if (srs.next()) {
          srs.previous();
        while(srs.next()){
	emissionPerKm = srs.getDouble("emissionPerKm");
        distance = srs.getDouble("distance");
        originCode = srs.getString("airportOriginCode");
        destCode = srs.getString("airportDestinationCode");
        for(Airport a: TravelAgency.getAirports()){
            if(a.getAirportCode().equals(originCode))
                Origin = a;
            if(a.getAirportCode().equals(destCode))
                Destination = a;
        }
        flightPrice = srs.getDouble("flightprice");
        depsql = (Time) srs.getTime("departureTime");
        departureTime = depsql.toLocalTime();
        arrsql = srs.getTime("arrivalTime");
        arrivalTime = arrsql.toLocalTime();
        airlineCode = srs.getString("airlineCode");
        for(Airline a: TravelAgency.getAirlines()){
            if(a.getAirlineCode().equals(airlineCode))
                airline = a;
        }
        flightNumber = srs.getString("flightNumber");
        Flight f = new Flight(flightNumber,airline, emissionPerKm,Origin,Destination,flightPrice,departureTime,arrivalTime, distance);
        flights.add(f);
        }
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
        DBConnector.closeConnection(con);
        return flights;
      
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
        
    
}
