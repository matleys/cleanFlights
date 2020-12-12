/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Database.DBBooking;
import Database.DBEmployee;
import Database.DBException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathias
 */

public class SalesReport {
    
    public static double RevenueOfMonth(int m, int y){
        try {
            double rev = 0;
            ArrayList<Booking> bookings = DBBooking.getBookingsOfMonth(m,y);
            for(Booking b: bookings){
                rev += b.getTotalPrice();
            }
            return rev;
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public static String printRevenueOfMonth(int month, int year){
        return "Total revenue of month ("+Month.of(month)+" "+year+") is €" +SalesReport.RevenueOfMonth(month, year);
    }
    
    public static String printRevenueThisMonth(){
        return "Total revenue this month ("+LocalDate.now().getMonth()+") is €"+SalesReport.RevenueOfMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }
    
    public static String printOverviewOfBookings(){
        String s = "Overview of bookings: \n\n";
        ArrayList<Booking> bookings = TravelAgency.getBookings();
        for(Booking b: bookings){
            s+=b.toString()+"\n";
        }
        return s;
    }
    
    public static String printPopularityAirports(){
        try {
            String s ="The most popular airports are:\n";
            //This returns a hashmap of all airport codes as keys and the number of times they have been booked as destinations of a route as corresponding integer value
            HashMap<String, Integer> hash = DBBooking.getPopularityAirports();
            ArrayList<Airport> topAirports = TravelAgency.getAirports();
            int counter = 1;
            for(Airport a: topAirports){
                s+=counter+") "+a.toString()+": "+hash.get(a.getAirportCode())+" booking(s)\n";
                counter++;
            }
            return s;
        } catch (DBException ex) {
            Logger.getLogger(SalesReport.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static String printEmployeeReport(){
        try {
            String s = "Employee sales are as follows:\n";
            //This returns a hashmap of all employeeIDs as keys and the number of bookings they made as corresponding integer value
            HashMap<String,Integer> map = DBEmployee.getEmployeePerformance();
            ArrayList<Employee> employees = TravelAgency.getEmployees();
            int counter = 1;
            for(Employee e: employees){
                s+=counter+") "+e.toString()+": "+map.get(e.getEmployeeID())+" booking(s)\n";
                counter++;
            }
            return s;
        } catch (DBException ex) {
            Logger.getLogger(SalesReport.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
    }
    
    public static String print(){
        try{
        String s = LocalDate.now() + ": Sales Report\n";
        s += "----------------------------------------\n";
        s += SalesReport.printOverviewOfBookings()+"\n";
        s += SalesReport.printEmployeeReport()+"\n";
        s += SalesReport.printPopularityAirports()+"\n";
        s+= SalesReport.printRevenueThisMonth();
        return s;
        }catch(Exception ex){
            return "Something went wrong. Please try again.";
        }
    }
}