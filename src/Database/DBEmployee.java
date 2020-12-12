/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Logic.Booking;
import Logic.Employee;
import Logic.TravelAgency;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mathias
 */
public class DBEmployee {
    //returns all employees in the database
    public static ArrayList<Employee> getAllEmployees() throws DBException{
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT employeeID, firstName, lastName, password "
              +"FROM Employee";
      ResultSet srs = stmt.executeQuery(sql);
      
      String firstName, lastName, password, employeeID;
      ArrayList<Employee> employees = new ArrayList<>();
      
      if (srs.next()) {
          srs.previous();
          while(srs.next()){
	employeeID = srs.getString("employeeID");
        firstName = srs.getString("firstName");
        lastName = srs.getString("lastName");
        password = srs.getString("password");
        Employee employee = new Employee(employeeID, password, firstName, lastName);
        employees.add(employee);
          }
      } else {
	DBConnector.closeConnection(con);
	return null;
      }
      DBConnector.closeConnection(con);
      return employees; 

    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    }
    
    //This returns a hashmap of all employeeIDs as keys and the number of bookings they made as corresponding integer value
        public static HashMap<String,Integer> getEmployeePerformance() throws DBException {
    Connection con = null;
    try{
        con = DBConnector.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        String sql = "SELECT employeeID FROM employee";
        ResultSet srs = stmt.executeQuery(sql);
        
        HashMap<String,Integer> hash = new HashMap<>();
        if(srs.next()){
          srs.previous();
          while(srs.next()){
              hash.put(srs.getString("employeeID"), 0);
          }
        }else{
           DBConnector.closeConnection(con);
           return null; 
        }
        ArrayList<Booking> bookings = TravelAgency.getBookings();
        for(Booking b: bookings){
            String employeeID = b.getEmployee().getEmployeeID();
            hash.replace(employeeID, hash.get(employeeID)+1);
        }
        
        DBConnector.closeConnection(con);
        return hash;
    } catch (Exception ex) {
         ex.printStackTrace();
         DBConnector.closeConnection(con);
         throw new DBException(ex);
    } 
  }
}
