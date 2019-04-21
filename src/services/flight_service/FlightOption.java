package services.flight_service;

public class FlightOption {
    int flightID;
    public int guests;
    String cityTo;
    public String cityFrom;
    public int flightTime;
    public int availability;
    public String name;
    public int price;
    public String make;
    public String model;
    public int time;
    
    public FlightOption(int flightID, int availability, String cityTo, String cityFrom, int price, int time){
        this.flightID = flightID;
        this.availability = availability;
        this.cityTo = cityTo;
        this.cityFrom = cityFrom;
        this.price = price;
        this.time = time;
    }
    
    public FlightOption(){};
    
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
    
    @Override
    public String toString(){
        String result = "flight,ID:" + flightID + ",availability:" + availability + ",time:" + time + ",price:" + price + ",cityTo:" + cityTo + ",cityFrom:" + cityFrom + "|";
        
        return result;
    }
    

    
    
    
}
