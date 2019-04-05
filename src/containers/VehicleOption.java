/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

/**
 *
 * @author docto
 */
public class VehicleOption extends AbstractOption{
    public VehicleOption(int availability, String make, String model, int guests, String cityTo, int price){
        this.guests = guests;
        this.availability = availability;
        this.cityTo = cityTo;
        this.price = price;
        this.make = make;
        this.model = model;
    }
    
    public int getAvailability(){
        return availability;
    }
    public String getMake(){
        return make;
    }
    public String getModel(){
        return model;
    }
    public int getGuests(){
        return guests;
    }
    public int getPrice(){
        return price;
    }
    public String getCityTo(){
        return cityTo;
    }
}
