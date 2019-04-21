/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.orchestrator_service;

import services.hotel_service.HotelOption;
import services.vehicle_service.VehicleOption;
import services.flight_service.FlightOption;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;

/**
 *
 * @author JTS5732
 */
public class OrchestratorService extends HttpServlet{
    private static final String vurl = "http://localhost:8080/vehicle_service/VServ";
    private static final String hurl = "http://localhost:8080/hotel_service/HServ";
    private static final String furl = "http://localhost:8080/flight_service/FServ";
    private static final String cityTo = "Erie";
    private static final String cityFrom = "Pittsburgh";
    private static final int guests = 3;
    
    HttpServletRequest request;
    HttpServletResponse response;
    
    String url; 
    String xml = "";
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        String cityTo = request.getParameter("cityTo");
        String cityFrom = request.getParameter("cityFrom");
        String guests = request.getParameter("guests");
        int guestNum = Integer.parseInt(guests.trim());
        String type = request.getHeader("accept");
        
        try {  
            url = vurl;
            sendRequest(cityTo, cityFrom, guestNum); 
            url = hurl;
            sendRequest(cityTo, cityFrom, guestNum); 
            url = furl;
            sendRequest(cityTo, cityFrom, guestNum); 
        }
        catch(NumberFormatException e) {
            send_typed_response(request, response, -1);
        }
    }
    
    private void send_typed_response(HttpServletRequest request, HttpServletResponse response, Object data) {
        String desired_type = request.getHeader("accept");

        // If client requests plain text or HTML, send it; else XML.
        if (desired_type.contains("text/plain"))
            send_plain(response, data);
        else if (desired_type.contains("text/html"))
            send_html(response, data);
        else
            send_xml(response, data);
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

    private String formatResp(String resp){
        String results = "";
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
                    results += "\nFlights:" + " $endl";
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
                    results += "  Flight to:" + " $endl";
                }
                else{
                    results += "  Return Flight:" + " $endl";
                }
                results += "    Option " + flightNum + " From " + cityFrom + " to " + cityTo + " at " + time + "  Availability: " + availability + " seats" + " $endl";
            }
            else if(fields[0].equals("hotel")){
                if(hotelNum == 0){
                    results += "\nHotels:" + " $endl";
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
                results += "    Option " + hotelNum + " Hotel name: " + name + " in " + cityTo + " for up to " + guests + " guests.  Availability: " + availability + " rooms" + " $endl";
            }
            else{
                if(vehicleNum == 0){
                    results += "\nVehicles:" + " $endl";
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
                results += "    Option " + vehicleNum + " Type: " + make + " " + model + " in " + cityTo + ".  Seats " + guests + " passenger.  Availability: " + availability + " seats" + " $endl";
            }
        } 
        return results;
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
    int i = 0;
    private void get_response(HttpURLConnection conn) {
        URL connUrl= conn.getURL();
        String urlStr = connUrl.toString();
        try {
            i++;
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next = null;
            while ((next = reader.readLine()) != null)
            xml += next;
            System.out.println("hello " + xml);
            if(urlStr.contains("FServ")){
                String resp = formatResp(xml);
                send_typed_response(request, response, resp);
                xml = "";
            }
        }
        catch(IOException e) { System.err.println(e); }
    }
}
