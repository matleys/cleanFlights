package Logic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kiren
 */
public class Airline {
    private String airlineCode;
    private String airlineName;

    public Airline(String code, String name) {
        this.airlineName = name;
        this.airlineCode = code;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }
    
    @Override
    public String toString(){
        return "Airline " + this.getAirlineName() + " with code "+ this.getAirlineCode();
    }
    
}





