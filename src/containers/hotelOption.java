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
public class hotelOption extends AbstractOption{
  
    public hotelOption(int availability, String name, int guests, String cityTo, int price){
        this.guests = guests;
        this.availability = availability;
        this.cityTo = cityTo;
        this.price = price;
        this.name = name;
    }
    
    public int getAvailability(){
        return availability;
    }
    public String getName(){
        return name;
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
