
package Database;

import Logic.Airport;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class DBAirport {
    
    //returns an airport with a specified code
    public static Airport getAirport(String airportCode) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT airportCode, city, timeZone" +
                " FROM Airport" +
                " WHERE airportCode = \"" + airportCode+"\"";

      ResultSet srs = stmt.executeQuery(sql);
      
      String code, city;
      int hours, minutes;
      String timeZoneStr;
      ZoneOffset timeZone;
      
      if (srs.next()) {
	code = srs.getString("airportCode");
	city = srs.getString("city");
        timeZoneStr = srs.getString("timeZone");
        //s will contain two values: for example if the timezone is '-05:30', s[0] is -5 and s[1] is '30'. So s[0] represents the hours and s[1] represents the minutes
        String[] s = timeZoneStr.split(":");
        hours = Integer.parseInt(s[0]);
        /*if hours < 0, minutes will have to be turned into a negative number as well.
        For example if the time zone is '-05:30', hours = -5 and minutes = 30. But if we don't
        transform 30 into -30 the timezone will add 30 minutes to -5 hours and the time zone will be '-04:30' instead of '-05:30'*/
        if(hours < 0)
            minutes = (-1)*Integer.parseInt(s[1]);
        else
            minutes = Integer.parseInt(s[1]);
        timeZone = ZoneOffset.ofHoursMinutes(hours, minutes);
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
      Airport airport = new Airport(code, city, timeZone);
      DBConnector.closeConnection(con);
      return airport;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
      //returns all airports in the database
        public static ArrayList<Airport> getAirports() throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT airportCode, city, timeZone" +
                " FROM Airport";

      ResultSet srs = stmt.executeQuery(sql);
      
      ArrayList<Airport> airports = new ArrayList<>();
      String code, city;
      int hours, minutes;
      String timeZoneStr;
      ZoneOffset timeZone;
      
      if (srs.next()) {
          srs.previous();
          while(srs.next()){
	code = srs.getString("airportCode");
	city = srs.getString("city");
        timeZoneStr = srs.getString("timeZone");
        //s will contain two values: for example if the timezone is '-05:30', s[0] is -5 and s[1] is '30'. So s[0] represents the hours and s[1] represents the minutes
        String[] s = timeZoneStr.split(":");
        hours = Integer.parseInt(s[0]);
        /*if hours < 0, minutes will have to be turned into a negative number as well.
        For example if the time zone is '-05:30', hours = -5 and minutes = 30. But if we don't
        transform 30 into -30 the timezone will add 30 minutes to -5 hours and the time zone will be '-04:30' instead of '-05:30'*/
        if(hours < 0)
            minutes = (-1)*Integer.parseInt(s[1]);
        else
            minutes = Integer.parseInt(s[1]);
        timeZone = ZoneOffset.ofHoursMinutes(hours, minutes);
        
        Airport airport = new Airport(code, city, timeZone);
        airports.add(airport);
          }
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
      DBConnector.closeConnection(con);
      return airports;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
}
