package Database;
import Logic.Airport;
import Logic.Flight;
import Logic.Route;
import Logic.TravelAgency;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBRoute {
    
    //returns all possible routes from an origin airport to a destination airport (with a max of 2 transfers)
        public static ArrayList<Route> getRoutesFromTo(Airport Origin, Airport Destination) throws DBException {
    Connection con = null;
    String originAirportCode = Origin.getAirportCode();
    String destinationAirportCode = Destination.getAirportCode();
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ArrayList<Route> routes = new ArrayList<>();
      
      //direct routes (no transfers):
     String sql = "SELECT flightNumber " +
        "FROM FLIGHT " +
        "WHERE airportOriginCode = '" + originAirportCode+"' "+
        "AND airportDestinationCode = '" + destinationAirportCode+"'";
    ResultSet srs = stmt.executeQuery(sql);
    
    while(srs.next()){
            ArrayList<Flight> f = new ArrayList<>();
            for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        f.add(fl);
                        routes.add(new Route(f));
                }
            } 
        }
    
      //1 transfer:
      ArrayList<Flight> possibleFirstFlights = new ArrayList<>();
      sql = "SELECT flightNumber " +
            "FROM FLIGHT " +
            "WHERE (airportOriginCode = '"+ originAirportCode+"')"+
            "AND (airportDestinationCode = ANY(" +
            "SELECT airportOriginCode " +
            "FROM FLIGHT " +
            "WHERE airportDestinationCode = '"+destinationAirportCode+"'))";
      srs = stmt.executeQuery(sql);

      while(srs.next()){
          for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        possibleFirstFlights.add(fl);
                }
            } 
        }
      
      for(Flight f: possibleFirstFlights){
          sql = "SELECT flightNumber " +
            "FROM FLIGHT " +
            "WHERE airportOriginCode = '" + f.getDestination().getAirportCode() +"' "+
            "AND airportDestinationCode = '"+ destinationAirportCode +"'";
          srs = stmt.executeQuery(sql);
          
        while(srs.next()){
            ArrayList<Flight> flightArr = new ArrayList<>();
            flightArr.add(f);
            for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        flightArr.add(fl);
                        routes.add(new Route(flightArr));
                }
            }
        }
      }
      
     //2 transfers:
      ArrayList<Flight> possibleFirstFlights2 = new ArrayList<>();
      sql = "SELECT flightNumber FROM flight " +
        "WHERE (airportOriginCode = '"+originAirportCode +"') " +
        "AND (airportDestinationCode = ANY(SELECT airportOriginCode " +
        "FROM flight " +
        "WHERE airportDestinationCode = ANY(select airportOriginCode " +
        "FROM Flight " +
        "WHERE airportDestinationCode = '"+ destinationAirportCode+"'))) ";
      srs = stmt.executeQuery(sql);
      
      while(srs.next()){
          for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        possibleFirstFlights2.add(fl);
                }
            }
      }
      
      for(Flight f1: possibleFirstFlights2){
       ArrayList<Flight> possibleSecondFlights = new ArrayList<>();
      sql = "SELECT flightNumber " +
        "FROM FLIGHT " +
        "WHERE (airportOriginCode = '"+f1.getDestination().getAirportCode()+"') " +
        "AND (airportDestinationCode = " +
        "ANY(SELECT airportOriginCode " +
        "FROM FLIGHT " +
        "WHERE airportDestinationCode = '"+destinationAirportCode+"'))";
      srs = stmt.executeQuery(sql);
      while(srs.next()){
          for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        possibleSecondFlights.add(fl);
                }
            }
      }
      
      for(Flight f2: possibleSecondFlights){
          ArrayList<Flight> flights2Stops = new ArrayList<>();
          sql = "SELECT flightNumber " +
            "FROM FLIGHT " +
            "WHERE airportOriginCode = '"+ f2.getDestination().getAirportCode()+"'" +
            "AND airportDestinationCode = '"+destinationAirportCode+"'";
          srs = stmt.executeQuery(sql);
          while(srs.next()){
              for(Flight fl: TravelAgency.getFlights()){
                if(fl.getFlightNumber().equals(srs.getString("flightNumber"))){
                        flights2Stops.add(fl);
                }
            }
          } 
      
      for(Flight f3: flights2Stops){
          if((f1.getOrigin().getAirportCode().equals(f2.getDestination().getAirportCode()) == false)
                  && (f2.getOrigin().getAirportCode().equals(f3.getDestination().getAirportCode()) == false)){
          ArrayList<Flight> flightArr = new ArrayList<>();
          flightArr.add(f1);
          flightArr.add(f2);
          flightArr.add(f3);
          Route r = new Route(flightArr);
          routes.add(r);
          }
      }
      
      }
      }
      DBConnector.closeConnection(con);
      return routes;
      
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
    
}
