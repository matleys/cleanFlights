/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author mathias
 */

public class Employee {
    private String firstName;
    private String lastName;
    private String employeeID;
    private String password;
    
    public Employee(String employeeID,String password, String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString(){
        return "Employee "+this.lastName+" "+this.firstName+" ("+this.employeeID+")";
    }
}
