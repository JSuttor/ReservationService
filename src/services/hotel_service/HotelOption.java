package services.hotel_service;

public class HotelOption {
    
    int hotelID;
    public int guests;
    String cityTo;
    public int availability;
    public String name;
    public int price;
    public int time;
  
    public HotelOption(int hotelID, int availability, String name, int guests, String cityTo, int price){
        this.hotelID = hotelID;
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
    
    @Override
    public String toString(){
        String result = "hotel,ID:" + hotelID + ",availability:" + availability + ",name:" + name + ",guests:" + guests + ",price:" + price + ",cityTo:" + cityTo + "|";
        
        return result;
    }
}
