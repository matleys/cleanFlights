
package Logic;

import Database.DBAirline;
import Database.DBAirport;
import Database.DBBooking;
import Database.DBCustomer;
import Database.DBEmployee;
import Database.DBException;
import Database.DBFlight;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathias
 */
public class TravelAgency {
    private static ArrayList<Airline> airlines;
    private static ArrayList<Flight> flights;
    private static ArrayList<Airport> airports;
    private static ArrayList<Customer> customers;
    private static ArrayList<Booking> bookings;
    private static ArrayList<Employee> employees;
    //'ingelogdeEmployee' allows us to keep track of which bookings were sold by which employees
    private static Employee ingelogdeEmployee;

    public static Employee getIngelogdeEmployee() {
        return ingelogdeEmployee;
    }

    public static void setIngelogdeEmployee(Employee ingelogdeEmployee) {
        TravelAgency.ingelogdeEmployee = ingelogdeEmployee;
    }
    
    public static ArrayList<Airline> getAirlines() {
        return airlines;
    }

    public static void setAirlines(ArrayList<Airline> airlines) {
        TravelAgency.airlines = airlines;
    }

    public static ArrayList<Flight> getFlights() {
        return flights;
    }

    public static void setFlights(ArrayList<Flight> flights) {
        TravelAgency.flights = flights;
    }

    public static ArrayList<Airport> getAirports() {
        return airports;
    }

    public static void setAirports(ArrayList<Airport> airports) {
        TravelAgency.airports = airports;
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        TravelAgency.customers = customers;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static void setBookings(ArrayList<Booking> bookings) {
        TravelAgency.bookings = bookings;
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static void setEmployees(ArrayList<Employee> employees) {
        TravelAgency.employees = employees;
    }

    public static void loadAirlines(){
        try {
            TravelAgency.setAirlines(DBAirline.getAirlines());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadAirports(){
        try {
            TravelAgency.setAirports(DBAirport.getAirports());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadCustomers(){
        try {
            TravelAgency.setCustomers(DBCustomer.getAllCustomers());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadFlights(){
        try {
            TravelAgency.setFlights(DBFlight.getFlights());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadEmployees(){
        try {
            TravelAgency.setEmployees(DBEmployee.getAllEmployees());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadBookings(){
        try {
            TravelAgency.setBookings(DBBooking.getAllBookings());
        } catch (DBException ex) {
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void load(){
        TravelAgency.loadEmployees();
        TravelAgency.loadAirlines();  
        TravelAgency.loadAirports();
        TravelAgency.loadCustomers();
        TravelAgency.loadFlights();
        TravelAgency.loadBookings();
    }
    
 
}
