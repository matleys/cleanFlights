package Logic;

import Database.DBBooking;
import Database.DBException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathias
 */

public class Booking {
    
    private ArrayList<Customer> clients;
    private Employee employee;
    private Route route;
    private LocalDate flightDate, bookingDate;
    private double feePercentage = 0.05;
    private double totalPrice;
    private int bookingNumber;
    
    //This constructor creates a new booking and generates a booking number for it and sets the booking date to today. We will use this constructor to create a new booking.
    public Booking (ArrayList<Customer> clients, Employee employee, Route route, LocalDate flightDate){
        try {
            this.bookingNumber = DBBooking.getLatestBookingNr() + 1;
        } catch (DBException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bookingDate = LocalDate.now();
        this.employee = employee;
        this.clients = clients;
        this.route = route;
        this.flightDate = flightDate;
        this.totalPrice = this.route.getRoutePrice() * (1+this.feePercentage);
    }

    //This constructor allows us to create an instance of the Booking class with a specific booking number and booking date. We will mainly use this constructor in Database methods.
    public Booking (int bookingNumber, ArrayList<Customer> clients, Route route, Employee employee, LocalDate flightDate, LocalDate bookingDate){
        this.bookingNumber = bookingNumber;
        this.employee = employee;
        this.bookingDate = bookingDate;
        this.clients = clients;
        this.route = route;
        this.flightDate = flightDate;
        this.totalPrice = this.route.getRoutePrice() * (1+this.feePercentage);
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    public ArrayList<Customer> getClients() {
        return clients;
    }
    
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public double getFeePercentage() {
        return feePercentage;
    }

    public void setFeePercentage(double feePercentage) {
        this.feePercentage = feePercentage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }
    
    @Override
    public String toString(){
        String s = this.flightDate+": Booking "+ this.bookingNumber +" with people ";
        for(Customer c: this.clients){
            s += c.toString()+" ";
        }
        s += "of "+this.route.toString();
        return s;
    }
    
}
