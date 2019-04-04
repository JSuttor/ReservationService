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
public class flightOption extends AbstractOption{
    public flightOption(int availability, String cityTo, String cityFrom, int price, int time){
        this.availability = availability;
        this.cityTo = cityTo;
        this.cityFrom = cityFrom;
        this.price = price;
        this.time = time;
    }
    
    public int getAvailability(){
        return availability;
    }
    public int getTime(){
        return time;
    }
    public int getPrice(){
        return price;
    }
    public String getCityTo(){
        return cityTo;
    }
    public String getCityFrom(){
        return cityFrom;
    }
}
