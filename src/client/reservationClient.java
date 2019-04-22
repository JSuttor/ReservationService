/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import client.Client.*;

//import services.database_service.FlightOption;
//import services.database_service.AbstractOption;
//import services.database_service.HotelOption;
//import services.database_service.VehicleOption;

/**
 *
 * @author JTS5732
 */
public class reservationClient {
    
    private static final String vurl = "http://localhost:8080/vehicle_service/VServ";
    private static final String hurl = "http://localhost:8080/hotel_service/HServ";
    private static final String furl = "http://localhost:8080/flight_service/FServ";
    private static final String cityTo = "Erie";
    private static final String cityFrom = "Pittsburgh";
    private static final int guests = 3;
    
    String url;
    
    /**
     * Creates new form CalculatorClient
     */
   
    
    private void sendRequest(String cityTo, String cityFrom, int guests){
        try {
            HttpURLConnection conn = null;
            String requestURL = url + "?cityTo=" + cityTo + "&cityFrom=" + cityFrom + "&guests=" + guests;
            //System.out.println(requestURL);
            conn = get_connection(requestURL, "GET");
            conn.addRequestProperty("accept", "text/plain");
            conn.connect();
            get_response(conn);
        }
        catch(IOException e) { System.err.println(e); }
        catch(NullPointerException e) { System.err.println(e); }
    }

    private void printResp(String resp){
        int availability = 0;
        int time = 0;
        int price = 0;
        String cityTo = "";
        String cityFrom = "";
        int guests = 0;
        String name = "";
        String make = "";
        String model = "";
        
        int flightNum = 0;
        int hotelNum = 0;
        int vehicleNum = 0;
               
        String[] objs = resp.split("\\|");
        for(String obj : objs){
            String[] fields = obj.split(",");
            if(fields[0].equals("flight")){
                if(flightNum == 0){
                    System.out.println("\nFlights:");
                }
                flightNum++;
                for(int i = 1; i < fields.length; i++){
                    String[] value = fields[i].split(":");
                    if(i == 1){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        time = Integer.parseInt(value[1]);
                    }
                    else if(i == 3){
                        price = Integer.parseInt(value[1]);
                    }
                    else if(i == 4){
                        cityTo = value[1];
                    }
                    else if(i == 5){
                        cityFrom = value[1];
                    }
                }
                if(cityTo.equals(this.cityTo)){
                    System.out.println("  Flight to:");
                }
                else{
                    System.out.println("  Return Flight:");
                }
                System.out.println("    Option " + flightNum + " From " + cityFrom + " to " + cityTo + " at " + time + "  Availability: " + availability + " seats");
            }
            else if(fields[0].equals("hotel")){
                if(hotelNum == 0){
                    System.out.println("\nHotels:");
                }
                hotelNum++;
                for(int i = 1; i < fields.length; i++){
                    String[] value = fields[i].split(":");
                    if(i == 1){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        name = value[1];
                    }
                    else if(i == 3){
                        guests = Integer.parseInt(value[1]);
                    }
                    else if(i == 4){
                        price = Integer.parseInt(value[1]);
                    }
                    else if(i == 5){
                        cityTo = value[1];
                    }
                }
                System.out.println("    Option " + hotelNum + " Hotel name: " + name + " in " + cityTo + " for up to " + guests + " guests.  Availability: " + availability + " rooms");
            }
            else{
                if(vehicleNum == 0){
                    System.out.println("\nVehicles:");
                }
                vehicleNum++;
                for(int i = 1; i < fields.length; i++){
                    String[] value = fields[i].split(":");
                    if(i == 1){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        make = value[1];
                    }
                    else if(i == 3){
                        model = value[1];
                    }
                    else if(i == 4){
                        guests = Integer.parseInt(value[1]);
                    }
                    else if(i == 5){
                        price = Integer.parseInt(value[1]);
                    }
                    else if(i == 6){
                        cityTo = value[1];
                    }
                }
                System.out.println("    Option " + vehicleNum + " Type: " + make + " " + model + " in " + cityTo + ".  Seats " + guests + " passenger.  Availability: " + availability + " seats");
            }
        } 
    }
    
    private HttpURLConnection get_connection(String url_string, String verb) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(url_string);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(verb);
            conn.setDoInput(true);
            conn.setDoOutput(true);
        }
        catch(MalformedURLException e) { System.err.println(e); }
        catch(IOException e) { System.err.println(e); }
        return conn;
    }
    
    private void get_response(HttpURLConnection conn) {
        try {
            String xml = "";
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next = null;
            while ((next = reader.readLine()) != null)
                xml += next;
            //System.out.println(xml);
            printResp(xml);

        }
        catch(IOException e) { System.err.println(e); }
    }
    
        public static void main(String args[]) {
            reservationClient rc = new reservationClient(); 
            LogRequestPortTypeService service = new LogRequestPortTypeService();
            LogRequestPortType s = service.getLogRequestPortTypePort();
            
            LoginComplete L = new LoginComplete();
            L.setUsername("Bob");
            L.setPassword("Joe");
 
            System.out.println(s.login(L).getCompleted()); //GetCompleted = get string
            
            
            
            rc.url = vurl;
            rc.sendRequest(cityTo, cityFrom, guests);
            rc.url = hurl;
            rc.sendRequest(cityTo, cityFrom, guests);
            rc.url = furl;
            rc.sendRequest(cityTo, cityFrom, guests);
            
        }
}
