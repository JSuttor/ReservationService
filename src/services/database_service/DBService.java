/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.database_service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;
/**
 *
 * @author Joshua Suttor
 */
public class DBService extends HttpServlet{

    Connection con;
    
    public DBService(){
        try {
            String host = "jdbc:derby://localhost:1527/reservationDB";
            String username = "u_name";
            String password = "password1";
            con = DriverManager.getConnection(host, username, password);
           
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public List<FlightOption> fetchFlightData(String cityTo, String cityFrom, int guests){
        List<FlightOption> optionList = new ArrayList<FlightOption>();
        
        try {
            ResultSet rs;
            FlightOption FO;
            Statement stmt = con.createStatement();
            
            //construct sql request for flight data
            String flightSQL = "SELECT * FROM FLIGHTS WHERE CITYTO = '" + cityTo + "' AND CITYFROM = '" + cityFrom + "' AND AVAILABILITY > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(flightSQL);
            
            //process data from flight record
            while(rs.next()){
                FO = new FlightOption(rs.getInt("AVAILABILITY"), rs.getString("CITYTO"), rs.getString("CITYFROM"), rs.getInt("PRICE"), rs.getInt("FLIGHTTIME"));
                optionList.add(FO);
            }
            
            //construct sql request for flight data
            String flightHomeSQL = "SELECT * FROM FLIGHTS WHERE CITYTO = '" + cityFrom + "' AND CITYFROM = '" + cityTo + "' AND AVAILABILITY > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(flightHomeSQL);
            
            //process data from flight record
            while(rs.next()){
                FO = new FlightOption(rs.getInt("AVAILABILITY"), rs.getString("CITYTO"), rs.getString("CITYFROM"), rs.getInt("PRICE"), rs.getInt("FLIGHTTIME"));
                optionList.add(FO);
            }
            
            /*
            
            
            */
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return optionList;
    }
    
    public List<HotelOption> fetchHotelData(String cityTo, int guests){
        List<HotelOption> optionList = new ArrayList<HotelOption>();
        
        try {
            ResultSet rs;
            HotelOption HO;
            Statement stmt = con.createStatement();
            //construct sql request for hotel data
            String hotelSQL = "SELECT * FROM HOTEL_ROOMS WHERE CITY = '" + cityTo + "' AND MAXGUESTS > " + guests + " ORDER BY PRICE";


            //execute query
            rs = stmt.executeQuery(hotelSQL);


            //process data from hotel record
            while(rs.next()){
                HO = new HotelOption(rs.getInt("AVAILABILITY"), rs.getString("HOTELNAME"), rs.getInt("MAXGUESTS"), rs.getString("CITY"), rs.getInt("PRICE"));
                optionList.add(HO);
            }
        }
        catch(SQLException err){
            
        }
        
        return optionList;
    }
    
    public List<VehicleOption> fetchVehicleData(String cityTo, int guests){
        List<VehicleOption> optionList = new ArrayList<VehicleOption>();
        
        try {
            ResultSet rs;
            VehicleOption VO;
            Statement stmt = con.createStatement();
            //construct sql request for vehicle data
            String vehicleSQL = "SELECT * FROM VEHICLE_RENTALS WHERE CITY = '" + cityTo + "' AND MAXPASSENGERS > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(vehicleSQL);
            
            //process data from hotel record
            while(rs.next()){
                VO = new VehicleOption(rs.getInt("AVAILABILITY"), rs.getString("CARMAKE"), rs.getString("CARMODEL"), rs.getInt("MAXPASSENGERS"), rs.getString("CITY"), rs.getInt("PRICE"));
                optionList.add(VO);
            }
        }
        catch(SQLException err){
            
        }
        
        return optionList;
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String optionType = request.getParameter("optionType");
        String cityTo = request.getParameter("cityTo");
        String cityFrom = request.getParameter("cityFrom");
        String guests = request.getParameter("guests");
        String type = request.getHeader("accept");
        
        try {
            int guestNum = Integer.parseInt(guests.trim());
            Object resp;
            if(optionType.trim().equals("flight"))
                resp = fetchFlightData(cityTo, cityFrom, guestNum);
            else if(optionType.trim().equals("hotel"))
                resp = fetchHotelData(cityTo, guestNum);
            else if(optionType.trim().equals("vehicle"))
                resp = fetchVehicleData(cityTo, guestNum);
            else
                resp = "not a valid input";
            send_typed_response(request, response, resp);
        }
        catch(NumberFormatException e) {
            send_typed_response(request, response, -1);
        }
  
    }

    
    private void send_typed_response(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Object data) {
        String desired_type = request.getHeader("accept");

        // If client requests plain text or HTML, send it; else XML.
        if (desired_type.contains("text/plain"))
            send_plain(response, data);
        else if (desired_type.contains("text/html"))
            send_html(response, data);
        else
            send_xml(response, data);
    }
    
        private void send_xml(HttpServletResponse response, Object data) {
        try {
            XMLEncoder enc = new XMLEncoder(response.getOutputStream());
            enc.writeObject(data.toString());
            enc.close();
        }
        catch(IOException e) {
            throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    private void send_html(HttpServletResponse response, Object data) {
        String html_start =
            "<html><head><title>send_html response</title></head><body><div>";
        String html_end = "</div></body></html>";
        String html_doc = html_start + data.toString() + html_end;
        send_plain(response, html_doc);
    }

    private void send_plain(HttpServletResponse response, Object data) {
        try {
            OutputStream out = response.getOutputStream();
            out.write(data.toString().getBytes());
            out.flush();
        }
        catch(IOException e) {
            throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    public static void main(String[] args){
        DBService db = new DBService();
        
        //Get Flights
        List<FlightOption> flightList = db.fetchFlightData("Pittsburgh", "Erie", 3);
        System.out.println("Flights:");
        for(FlightOption item : flightList){
            System.out.println(item.getCityFrom() + " " + item.getCityTo() + " " + item.getPrice());
        }
        
        //Get Hotels
        List<HotelOption> hotelList = db.fetchHotelData("Erie", 3);
        System.out.println("Hotels:");
        for(HotelOption item : hotelList){
            System.out.println(item.getName());
        }
        
        //Get Vehicles
        List<VehicleOption> vehicleList = db.fetchVehicleData("Erie", 3);
        System.out.println("Vehicles:");
        for(VehicleOption item : vehicleList){
            System.out.println(item.getMake() + " " + item.getModel());
        }
    }
}
