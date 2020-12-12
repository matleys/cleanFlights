
package Database;
import Logic.Customer;
import Logic.TravelAgency;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mathias
 */
public class DBCustomer {
    public static void createTables() throws DBException {
    try {
      Connection con = DBConnector.getConnection();
      Statement stmt = con.createStatement();
      String sql = "CREATE TABLE Customer (" +
            "passportNumber varchar(45) NOT NULL, " +
            "firstName varchar(45) NOT NULL, " +
            "dateOfBirth date NOT NULL, " +
            "lastName varchar(45) NOT NULL, " +
            "country varchar(45) NOT NULL, " +
            "PRIMARY KEY (passportNumber)" +
            ")";
      stmt.executeUpdate(sql);
      DBConnector.closeConnection(con);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    //gets a customer with a specified passport number
        public static Customer getCustomer(String passportNumber) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT firstName, lastName, passportNumber, country, dateOfBirth " +
            "FROM Customer " +
            "WHERE passportNumber = \"" + passportNumber+"\"";

      ResultSet srs = stmt.executeQuery(sql);
      
      String firstName, lastName, passportNum, country;
      Date sqldate;
      LocalDate dateOfBirth;
      
      if (srs.next()) {
	firstName = srs.getString("firstName");
	lastName = srs.getString("lastName");
        passportNum = srs.getString("passportNumber");
        country = srs.getString("country");
        sqldate = (Date) srs.getObject("DateOfBirth");
        dateOfBirth = sqldate.toLocalDate();
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      Customer customer = new Customer(passportNumber,firstName,lastName,country, dateOfBirth);
      DBConnector.closeConnection(con);
      return customer;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
        //returns all customers in the database
             public static ArrayList<Customer> getAllCustomers() throws DBException {
    Connection con = null;
    ArrayList<Customer> customers = new ArrayList<Customer>();
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT firstName, lastName, passportNumber, country, dateOfBirth " +
            "FROM Customer "; 

      ResultSet srs = stmt.executeQuery(sql);
      
      String firstName, lastName, passportNum, country;
      Date sqldate;
      LocalDate dateOfBirth;
      
      while (srs.next()) {
	firstName = srs.getString("firstName");
	lastName = srs.getString("lastName");
        passportNum = srs.getString("passportNumber");
        country = srs.getString("country");
        sqldate = (Date) srs.getObject("DateOfBirth");
        dateOfBirth = sqldate.toLocalDate();
           Customer customer = new Customer(passportNum,firstName,lastName,country, dateOfBirth);
           customers.add(customer);
      }
     
      DBConnector.closeConnection(con);
      return customers;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }      
        
public static void save(Customer c) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT passportNumber "
              + "FROM Customer "
              + "WHERE passportNumber = \"" + c.getPassportNumber()+"\"";
      ResultSet srs = stmt.executeQuery(sql);
      if (srs.next()) {
          //if the customer with the specified passport number already exists, the 'new customer' will simply replace the 'old one'
	sql = "UPDATE customer "
                + "SET passportNumber = '" + c.getPassportNumber() + "'"
		+ ", firstName = '" + c.getFirstName()+"'"
		+ ", lastName = '" + c.getLastName()+"'"
		+ ", dateOfBirth = '" + c.getDateOfBirth() + "'"
                + ", country = '" + c.getCountry() + "'"
                + "WHERE passportNumber = \"" + c.getPassportNumber()+"\"";
        stmt.executeUpdate(sql);
        
      } else {
	sql = "INSERT into customer "
                + "(passportNumber, firstName, lastName, dateOfBirth, country) "
		+ "VALUES ('" + c.getPassportNumber()+"'"
                + ", '" + c.getFirstName() + "'"
		+ ", '" + c.getLastName() + "'"
		+ ", '" + c.getDateOfBirth()+"'"
		+ ", '" + c.getCountry() + "')";
        stmt.executeUpdate(sql);
      }
      //we also update the customers arraylist in the TravelAgency class
      TravelAgency.loadCustomers();
      DBConnector.closeConnection(con);
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }

/*returns number of 'dummy customers'. These are customers who were deleted but already had bookings so they are replaced with
a 'dummy enitity'. This method is used to generate a unique passport number for these 'dummy customers'; these passport numbers look like the following "#"+number of 'dummy customers'*/
public static int getNumberOfDeletedCustomers() throws DBException{
        Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "select passportNumber " +
                "from customer " +
                "WHERE passportNumber LIKE '#%'";
      ResultSet srs = stmt.executeQuery(sql);
      int counter = 0;
      if(srs.next()){
          srs.previous();
          while(srs.next())
              counter++;
      } else{
          DBConnector.closeConnection(con);
          return counter;
      }
        DBConnector.closeConnection(con);
        return counter;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
}
  
public static void delete(String passportNumber) throws DBException{
    Connection con = null;
    try {
        //if the customer has no bookings, he/she will simply be deleted
      if(DBBooking.getBookings(passportNumber) == null){
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT passportNumber "
              + "FROM Customer "
              + "WHERE passportNumber = \"" +passportNumber+"\"";
      ResultSet srs = stmt.executeQuery(sql);
      if(srs.next()){
          sql = "DELETE FROM customer WHERE (passportNumber = '"+passportNumber+"')";
          stmt.executeUpdate(sql);
      } else{
          System.out.println("Customer with passport number "+ passportNumber+" does not exist.");
          DBConnector.closeConnection(con);
          return;
      }
        //we also update the customers arraylist in the TravelAgency class
        TravelAgency.loadCustomers();
        DBConnector.closeConnection(con);
      }
      
      else{
      /*if the customer does have bookings, we run into a small problem because we logically can not delete the corresponding bookings
          as this will unfairly impact our sales etc. So when this happens we simply replace the customer with a 'dummy customer' whose name
          is DELETED CUSTOMER and we generate a new unique passport number for this 'dummy customer' of the form "#" + the number of customers who have been deleted in this
          manner and thus also have a "#" in their passport number*/
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT passportNumber "
              + "FROM Customer "
              + "WHERE passportNumber = \"" +passportNumber+"\"";
      ResultSet srs = stmt.executeQuery(sql);
      if(srs.next()){
          sql = "UPDATE customer "
                + "SET passportNumber = '" + "#" +getNumberOfDeletedCustomers()+ "'"
		+ ", firstName = '" + "DELETED"+"'"
		+ ", lastName = '" + "CUSTOMER"+"'"
		+ ", dateOfBirth = '" + LocalDate.of(2000, 1, 1) + "'"
                + ", country = '" +  " " + "'"
                + "WHERE passportNumber = \"" + passportNumber+"\"";
        stmt.executeUpdate(sql);  
      } else{
          System.out.println("Customer with passport number "+ passportNumber+" does not exist.");
          DBConnector.closeConnection(con);
          return;
      }
        //we also update the customers arraylist in the TravelAgency class
        TravelAgency.loadCustomers();
        DBConnector.closeConnection(con);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    }
  }