package services.vehicle_service;

public class VehicleOption {
    int vehicleID;
    public int guests;
    String cityTo;
    public int availability;
    public String name;
    public int price;
    public String make;
    public String model;
    
    public VehicleOption(int vehicleID, int availability, String make, String model, int guests, String cityTo, int price){
        this.vehicleID = vehicleID;
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
    @Override
    public String toString(){
        String result = "vehicle,ID:" + vehicleID + ",availability:" + availability + ",make:" + make + ",model:" + model + ",guests:" + guests + ",price:" + price + ",cityTo:" + cityTo + "|";
        
        return result;
    }
    
}
