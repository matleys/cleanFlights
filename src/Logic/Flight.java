package Logic;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 *
 * @author mathias
 */
public class Flight {
   private String flightNumber;
   private double emissionPerKm, flightEmission, distance;
   private Airline airline;
   private Airport Destination, Origin;
   private double flightPrice;
   private OffsetTime arrivalTime, departureTime;
   private Duration duration;

    public Flight(String flightNumber, Airline airline, double emissionPerKm, Airport Origin, Airport Destination, 
            double flightPrice, LocalTime departureTime, LocalTime arrivalTime, double distance) 
    {
        this.airline = airline;
        this.emissionPerKm = emissionPerKm;
        this.distance = distance;
        this.flightEmission = this.emissionPerKm * this.distance;
        
        //the two lines below round the flight emission to two decimal places
        this.flightEmission = this.flightEmission*100;
        this.flightEmission = Math.round(this.flightEmission)/100;
        
        this.Destination = Destination;
        this.Origin = Origin;
        this.flightPrice = flightPrice;
        
        /*since you depart from the origin airport and arrive at the destination airport, arrival time is offset with the
        destination time zone and departure time is offset with the origin time zone.*/
        this.arrivalTime = arrivalTime.atOffset(Destination.getTimeZone());
        this.departureTime = departureTime.atOffset(Origin.getTimeZone());
        
        this.flightNumber = flightNumber;
        this.duration = Duration.between(this.departureTime, this.arrivalTime);
        
        /*if the duration < 0, it means that you will 'arrive before you depart'. Realistically this means that we 
        are dealing with a 'midnight flight', so in this case we calculate the duration as the time between departure time
        and midnight + the time between 0:00 and arrivaltime.*/
        if(this.duration.toMinutes() < 0)
            this.duration = Duration.between(this.departureTime, LocalTime.MAX.atOffset(ZoneOffset.UTC)).plus(Duration.between(LocalTime.MIN.atOffset(ZoneOffset.UTC), this.arrivalTime)).plusNanos(1);
    }

    public Airline getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getEmissionPerKm() {
        return emissionPerKm;
    }

    public double getFlightEmission() {
        return flightEmission;
    }

    public double getDistance() {
        return distance;
    }
    
    public Airport getDestination() {
        return Destination;
    }

    public Airport getOrigin() {
        return Origin;
    }

    public double getFlightPrice() {
        return flightPrice;
    }

    public OffsetTime getArrivalTime() {
        return arrivalTime;
    }

    public OffsetTime getDepartureTime() {
        return departureTime;
    }

    public Duration getDuration() {
        return duration;
    }
    
    @Override
    public String toString(){
        return "Flight " + this.flightNumber + " from "+ this.Origin.getCity() + " to "+ this.Destination.getCity() + " with "+this.airline.getAirlineName()+" ("+this.airline.getAirlineCode()+")";
    }

}
