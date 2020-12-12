
package Database;

import Logic.Airline;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class DBAirline {
    
    //retrieves airline with a specified code
    public static Airline getAirline(String code) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT airlineCode, airlineName "
              +"FROM Airline "
              +"WHERE airlineCode = \"" + code+"\"";
      ResultSet srs = stmt.executeQuery(sql);
      
      String name;
      
      if (srs.next()) {
	name = srs.getString("airlineName");
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
      Airline airline = new Airline(code, name);
      DBConnector.closeConnection(con);
      return airline;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    //returns an arraylist of all airlines in the database
        public static ArrayList<Airline> getAirlines() throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT airlineCode, airlineName "
              +"FROM Airline";
      ResultSet srs = stmt.executeQuery(sql);
      
      String name, code;
      ArrayList<Airline> airlines = new ArrayList<>();
      
      if (srs.next()) {
          srs.previous();
          while(srs.next()){
	name = srs.getString("airlineName");
        code = srs.getString("airlineCode");
        Airline airline = new Airline(code, name);
        airlines.add(airline);
          }
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      DBConnector.closeConnection(con);
      return airlines; 

    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
  
  
    
}
