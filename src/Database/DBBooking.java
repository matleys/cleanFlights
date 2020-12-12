package Database;


import Logic.Booking;
import Logic.Customer;
import Logic.Employee;
import Logic.Flight;
import Logic.Route;
import Logic.TravelAgency;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 *
 * @author mathias
 */

public class DBBooking {
    
    public static void createTables() throws DBException {
    try {
      Connection con = DBConnector.getConnection();
      Statement stmt = con.createStatement();
      String sql = "";
      stmt.executeUpdate(sql);
      DBConnector.closeConnection(con);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
    
    //returns how much money the spcified customer has spent on bookings  with the travel agency
    public static double getTotalSpending(Customer c){
       double spending = 0;
       for(Booking b: TravelAgency.getBookings()){
           if(b.getClients().contains(c))
               spending = spending + b.getTotalPrice();
       }
       return spending;
    }
    
       public static double getSpendingOfCustomer(Customer c){   // dezelfde methode als hierboven maar die werkt wel als de klant niet komt van TravelAgency
       double spending = 0;
       ArrayList<Booking > bookings =  TravelAgency.getBookings();
       for(int i = 0; i < bookings.size(); i++){
           for ( int i2 = 0; i2 < bookings.get(i).getClients().size(); i2++ ){
           if(  bookings.get(i).getClients().get(i2).getPassportNumber().equalsIgnoreCase(c.getPassportNumber())){
               spending = spending +  bookings.get(i).getTotalPrice();}
       }
       }
       return spending;
    }
       
       //returns the number of bookings a customer has made
        public static int numberOfBookings(Customer c){
       int number = 0;
       for(Booking b: TravelAgency.getBookings()){
           if(b.getClients().contains(c))
               number++;
       }
       return number; 
    }
        
   public static int getNumberOfBookings(Customer c){   // idem vorige
       int number = 0;
       ArrayList<Booking > bookings =  TravelAgency.getBookings();
       for(int i = 0; i < bookings.size(); i++){
           for ( int i2 = 0; i2 < bookings.get(i).getClients().size(); i2++ ){
           if(  bookings.get(i).getClients().get(i2).getPassportNumber().equalsIgnoreCase(c.getPassportNumber())){
             number++;}
       }
       }
       return number;
    }
      
    //returns the largest booking number in the database. This method is used to generate a new booking number (simply take this number and add 1 to it)
    public static int getLatestBookingNr() throws DBException{
        Connection con = null;
        try{
        con = DBConnector.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int number;
        String sql = "select distinct MAX(bookingNumber) from booking";
        ResultSet srs = stmt.executeQuery(sql);
        if(srs.next()){
             number = srs.getInt("MAX(bookingNumber)");
        }else{
            DBConnector.closeConnection(con);
            return 0;
        }
        DBConnector.closeConnection(con);
        return number;
        }catch (Exception ex) {
         ex.printStackTrace();
         DBConnector.closeConnection(con);
         throw new DBException(ex);
    }
    }
    
    //This returns a hashmap of all airport codes as keys and the number of times they have been booked as destinations of a route as corresponding integer value
    public static HashMap<String,Integer> getPopularityAirports() throws DBException {
    Connection con = null;
    try{
        con = DBConnector.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        String sql = "SELECT airportCode FROM airport";
        ResultSet srs = stmt.executeQuery(sql);
        
        HashMap<String,Integer> hash = new HashMap<>();
        if(srs.next()){
          srs.previous();
          while(srs.next()){
              hash.put(srs.getString("airportCode"), 0);
          }
        }else{
           DBConnector.closeConnection(con);
           return null; 
        }
        ArrayList<Booking> bookings = TravelAgency.getBookings();
        for(Booking b: bookings){
            String Destination = b.getRoute().getDestination().getAirportCode();
            hash.replace(Destination, hash.get(Destination)+1);
        }
        
        DBConnector.closeConnection(con);
        return hash;
    } catch (Exception ex) {
         ex.printStackTrace();
         DBConnector.closeConnection(con);
         throw new DBException(ex);
    } 
  }
    
    //returns booking with a specific booking number
        public static Booking getBooking(int bookingNumber) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      //gets the route the booking was for
      String sql = "SELECT DISTINCT flightNumber "+
            "FROM booking "+
            "WHERE bookingNumber = "+ bookingNumber;

      ResultSet srs = stmt.executeQuery(sql);
      ArrayList<Flight> flights = new ArrayList<>();
      LocalDate flightDate, bookingDate;
      String passportNumber = null, flightNumber = null;
      
      if(srs.next()){
        srs.previous();
        while(srs.next()){
            flightNumber = srs.getString("flightNumber");
            for(Flight f: TravelAgency.getFlights()){
                if(f.getFlightNumber().equals(flightNumber))
                   flights.add(f);
            }
        }
      } else{
          DBConnector.closeConnection(con);
          return null;
      } 
      
      Route route = new Route(flights);
      //gets all customers the booking was for
      ArrayList<Customer> clients = new ArrayList<>();
      sql = "SELECT DISTINCT passportNumber "+
              "FROM booking "+
              "WHERE bookingNumber = "+ bookingNumber;
      srs = stmt.executeQuery(sql);
      if(srs.next()){
        srs.previous();
        while(srs.next()){
            passportNumber = srs.getString("passportNumber");
            for(Customer c: TravelAgency.getCustomers()){
                if(c.getPassportNumber().equals(passportNumber))
                  clients.add(c);
            }
        }
      } else{
          DBConnector.closeConnection(con);
          return null;
      }
      
      Employee employee = null;
      //gets the employee who sold the booking
      sql = "SELECT employeeID "+
            "FROM Booking "+
            "WHERE bookingNumber = " + bookingNumber;
      
      srs = stmt.executeQuery(sql);
      if (srs.next()) {
       for(Employee e: TravelAgency.getEmployees()){
           if(e.getEmployeeID().equals(srs.getString("employeeID")))
              employee = e; 
       }
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
      
      //gets the date the customers booked for and the date when the booking was made
      sql = "SELECT flightDate, bookingDate "+
            "FROM Booking "+
            "WHERE bookingNumber = " + bookingNumber;
      
      srs = stmt.executeQuery(sql);
      if (srs.next()) {
       flightDate = srs.getDate("flightDate").toLocalDate();
       bookingDate = srs.getDate("bookingDate").toLocalDate();
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      
      Booking b = new Booking(bookingNumber, clients, route, employee, flightDate, bookingDate);
      DBConnector.closeConnection(con);
      return b;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }  
    //returns all bookings a specified customer has made
      public static ArrayList<Booking> getBookings(String passportNumber) throws DBException{
          Connection con = null;
          try{
          con = DBConnector.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ArrayList<Integer> bookingNumbers = new ArrayList<>();
          ArrayList<Booking> b = new ArrayList<>();
          
          String sql = "SELECT bookingNumber " +
                "FROM booking " +
                "WHERE passportNumber = '"+ passportNumber+"' ORDER BY bookingNumber asc";
          ResultSet srs = stmt.executeQuery(sql);
          if(srs.next()){
             srs.previous();
             while(srs.next()){
                 bookingNumbers.add(srs.getInt("bookingNumber"));
             }
             bookingNumbers = new ArrayList<>(new LinkedHashSet(bookingNumbers));
             for(int i: bookingNumbers){
                 b.add(DBBooking.getBooking(i));
             }
             
          }else{
             DBConnector.closeConnection(con);
             return null; 
          }
          
          DBConnector.closeConnection(con);
          return b;
          }catch(Exception ex){
              ex.printStackTrace();
              DBConnector.closeConnection(con);
              throw new DBException(ex);
          }
          
          
      }
      
      //returns bookings for a specific month in a specific year
      public static ArrayList<Booking> getBookingsOfMonth(int month, int year) throws DBException{
          Connection con = null;
          try{
              con = DBConnector.getConnection();
              Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              ArrayList<Booking> b = new ArrayList<>();
              
              String sql = "SELECT bookingNumber "
               + " FROM booking "
               + " WHERE (EXTRACT(MONTH FROM bookingDate)= "+ month
               + " AND EXTRACT(YEAR FROM bookingDate)= "+year+") ORDER BY bookingNumber asc";
              ResultSet srs = stmt.executeQuery(sql);
              if(srs.next()){
                  srs.previous();
                  while(srs.next()){
                      b.add(DBBooking.getBooking(srs.getInt("bookingNumber")));
                  }
              }else{
                  DBConnector.closeConnection(con);
                  return null;
              }
              DBConnector.closeConnection(con);
              return b;
          }catch(Exception ex){
              ex.printStackTrace();
              DBConnector.closeConnection(con);
              throw new DBException(ex);
          }
      }
     
      //returns all bookings
      public static ArrayList<Booking> getAllBookings() throws DBException{
          Connection con = null;
          try{
              con = DBConnector.getConnection();
              Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              String sql = "SELECT DISTINCT bookingNumber "
                      + "FROM booking ORDER BY bookingNumber asc";
              ResultSet srs = stmt.executeQuery(sql);
              ArrayList<Booking> b = new ArrayList<>();
              
              if(srs.next()){
                  srs.previous();
                  while(srs.next()){
                      b.add(DBBooking.getBooking(srs.getInt("bookingNumber")));
                  }
              }else{
                 DBConnector.closeConnection(con);
                 return null; 
              }
              DBConnector.closeConnection(con);
              
              return b;
          }catch(Exception ex){
              ex.printStackTrace();
              DBConnector.closeConnection(con);
              throw new DBException(ex);
          }
      }
        
     
    public static void save(Booking b) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT bookingNumber "
              + "FROM booking "
              + "WHERE bookingNumber = " + b.getBookingNumber();
      ResultSet srs = stmt.executeQuery(sql);
      
      if (srs.next()) {
            //if the booking with the booking number already exists, it will simply do nothing. But this can't happen because when we make a new booking, we generate a new booking number for it that does not exists yet
      } else {
        for(Customer c: b.getClients()){
        for(Flight f: b.getRoute().getFlights()){
	sql = "INSERT into booking "
                + "(bookingNumber, flightNumber, passportNumber, flightDate, bookingDate, employeeID) "
		+ "VALUES (" + b.getBookingNumber()
		+ ", '" + f.getFlightNumber()+"'"
                + ", '" + c.getPassportNumber()+"'"
                + ", '" + b.getFlightDate()+"'"
                + ", '" + b.getBookingDate()+"'"
                + ", '" + TravelAgency.getIngelogdeEmployee().getEmployeeID()+"'"
                +")";
        stmt.executeUpdate(sql);
        }
        }
        //we also update the booking arraylist in the TravelAgency class
        TravelAgency.loadBookings();
      }
      DBConnector.closeConnection(con);
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
}