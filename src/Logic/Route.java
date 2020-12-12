package Logic;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mathias
 */
public class Route {
    
    private ArrayList<Flight> flights;
    private Airport Origin, Destination;
    private OffsetTime departureTime, arrivalTime; 
    private double routePrice, totalEmission, totalDistance;
    private Duration routeDuration;
    private int numberOfTransfers;
    
    public Route(ArrayList<Flight> flights){
        this.flights = flights;
        //The loop below will will sort the array of flights such that the destination of a given flight is always the origin of the next one.
        boolean b = true;
        int counter = 1;   
        while(b){
            //This if-statement will never be executed because our algorithm suggests the routes a client can choose from. So the statement exists solely to prevent an infinite loop if someone were to make a mistake while manually making a route.
            if(counter > 100){
            System.out.println("This combination of flights is incorrect.");
            b = false;
            }
            
            switch(this.flights.size()){
                case 2: 
                    if(this.flights.get(0).getDestination().getAirportCode().equals(this.flights.get(1).getOrigin().getAirportCode()))
                    b = false;
                break;
                case 3: if((this.flights.get(0).getDestination().getAirportCode().equals(this.flights.get(1).getOrigin().getAirportCode()))
                        && (this.flights.get(1).getDestination().getAirportCode().equals(this.flights.get(2).getOrigin().getAirportCode())))
                    b = false;
                break;
                default: b = false;
                break;
            }
            if(b){
                if(this.flights.size() == 2)
                    Collections.reverse(this.flights);
                else
                    Collections.shuffle(this.flights);
            }
            counter++;
        }
        //The origin and destination disregard 'destinations' and 'origins' of transfer flights.
        this.Origin = flights.get(0).getOrigin();
        this.Destination = flights.get(flights.size()-1).getDestination();
        
        this.routePrice = 0;
        this.totalDistance = 0;
        this.numberOfTransfers = flights.size()-1;
        this.totalEmission = 0;
        this.departureTime = flights.get(0).getDepartureTime();
        this.arrivalTime = flights.get(flights.size()-1).getArrivalTime();
        this.routeDuration = Duration.ZERO;
        for(int i = 0; i < flights.size(); i++){
            this.routeDuration = this.routeDuration.plus(flights.get(i).getDuration());
            //The code below will take waiting times into account (= the time between arrival at a transfer airport and departure of the connecting flight)
            if(i > 0){
                if(Duration.between(this.flights.get(i-1).getArrivalTime(), this.flights.get(i).getDepartureTime()).isNegative() == false)
                    this.routeDuration = this.routeDuration.plus(Duration.between(this.flights.get(i-1).getArrivalTime(), this.flights.get(i).getDepartureTime()));
                //if the waiting time is negative. It means you arrive before midnight and depart after midnight, so we adjust our duration accordingly
                else
                    this.routeDuration = this.routeDuration.plus(Duration.between(this.flights.get(i-1).getArrivalTime(), LocalTime.MAX.atOffset(ZoneOffset.UTC))).plus(Duration.between(LocalTime.MIN.atOffset(ZoneOffset.UTC), this.flights.get(i).getDepartureTime())).plusNanos(1);
            }
            
            this.totalDistance += flights.get(i).getDistance();
            this.routePrice += flights.get(i).getFlightPrice();
            this.totalEmission += flights.get(i).getFlightEmission();
        }
        //This will round the total emission to two decimal places
        this.totalEmission = this.totalEmission*100;
        this.totalEmission = Math.round(this.totalEmission)/100;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Airport getOrigin() {
        return Origin;
    }

    public Airport getDestination() {
        return Destination;
    }

    public OffsetTime getDepartureTime() {
        return departureTime;
    }

    public OffsetTime getArrivalTime() {
        return arrivalTime;
    }

    public double getRoutePrice() {
        return routePrice;
    }

    public double getTotalEmission() {
        return totalEmission;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public Duration getRouteDuration() {
        return routeDuration;
    }

    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }
    
    @Override
    public String toString(){
        String s= "Route from "+this.Origin.getCity()+" to "+this.Destination.getCity()+"\nwith flight(s):";
        for(Flight f: this.getFlights()){
            s+= "\n"+f.toString() + " with emission of " + f.getFlightEmission()+" tonnes of CO2.";
        }
        return s;
    }
    
    
}