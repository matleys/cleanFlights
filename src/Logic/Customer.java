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

public class Customer {
    private String passportNumber;
    private String firstName;
    private String lastName;
    private String country;
    private LocalDate dateOfBirth;
    
    public Customer(String passportNumber,String firstName,String lastName, String country, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public  String createEmissionReport(){
        double totalEmission = 0;
        String s = "Emission Report for "+this.toString()+":\n";
        s += "----------------------------------------\n";
        ArrayList<Booking> bookings = new ArrayList<>();
       //retrieves all bookings of this specific customer. 
        try {
            bookings = DBBooking.getBookings(this.passportNumber);
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Booking b: bookings){
            s+= b.toString()+"\n";
            totalEmission += b.getRoute().getTotalEmission();
        }
        s += "----------------------------------------\n";
        s += "Total emission of: "+totalEmission+" tonnes of CO2.\n";
        s += "To compensate for this emission, please consider donating to MyClimate: https://co2.myclimate.org/en/contribution_calculators/new?localized_currency=EUR";

        return s;        
    }
    
    @Override
    public String toString(){
        return this.lastName + " "+ this.firstName +" ("+this.passportNumber+")";
    }
    
    
    
}
