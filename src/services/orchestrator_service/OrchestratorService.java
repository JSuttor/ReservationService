/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.orchestrator_service;

import client.reservationClient;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;
import services.orchestrator_service.Client.LogRequestPortType;
import services.orchestrator_service.Client.LogRequestPortTypeService;
import services.orchestrator_service.Client.LoginComplete;

/**
 *
 * @author JTS5732
 */
public class OrchestratorService extends HttpServlet{
    private static final String vurl = "https://localhost:8443/vehicle_service/VServ";
    private static final String hurl = "http://localhost:8443/hotel_service/HServ";
    private static final String furl = "http://localhost:8443/flight_service/FServ";
    private static final String cityTo = "Erie";
    private static final String cityFrom = "Pittsburgh";
    private static final int guests = 3;
    
    HttpServletRequest request;
    HttpServletResponse response;
    LogRequestPortTypeService service;
    
    String url; 
    String xml = "";
    boolean login = false;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String valid = request.getParameter("username");
        if(valid != null && valid != ""){
            login = false;
        }
        
        
        if (login) {

            String cityTo = request.getParameter("cityTo");
            String cityFrom = request.getParameter("cityFrom");
            String guests = request.getParameter("guests");
            System.out.println(guests);
            int guestNum = Integer.parseInt(guests.trim());

            try {
                url = vurl;
                sendRequest(cityTo, cityFrom, guestNum);
                url = hurl;
                sendRequest(cityTo, cityFrom, guestNum);
                url = furl;
                sendRequest(cityTo, cityFrom, guestNum);
            } catch (NumberFormatException e) {
                send_typed_response(request, response, -1);
            }
            login = false;
        }
        else{
            login = false;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println ("user: " + username);
            System.out.println ("pass: " + password);

            service = new LogRequestPortTypeService(); 
            LogRequestPortType s = service.getLogRequestPortTypePort();
            
            LoginComplete L = new LoginComplete();
            L.setUsername(username);
            L.setPassword(password);
            String testSuccess = s.login(L).getCompleted();
            if(testSuccess.equals("Login Successful!"))
                login = true;
            System.out.println(testSuccess);
            send_typed_response(request, response, testSuccess);
        }
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String entry = request.getParameter("entry");
        if (entry == null)
            throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);

        // Extract the integers from a string such as: "[1, 2, 3]"

        String[ ] parts = entry.split(",");
        String message = "";
        int i = 0;
        for (String next : parts) {
            next = next.trim();
            if(i == 0){
                url = vurl;
                message = next;
                sendPostRequest(message);
                message = "";
            }
            else if(i == 1){
                url = hurl;
                message = next;
                sendPostRequest(message);
                message = "";
            }
            else if(i == 2){
                message = next;
            }
            else if(i == 3){
                url = furl;
                message += ("," + next);
                System.out.println(message);
                sendPostRequest(message);
                message = "";
            }
            i++;
        }
        String resp = "";
        send_typed_response(request, response, resp);
    }
    
    
    private void sendPostRequest(String entry) {
        try {
            String payload = URLEncoder.encode("entry", "UTF-8") + "=" + URLEncoder.encode(entry.toString(), "UTF-8");
            HttpURLConnection conn = null;
            conn = get_connection(url, "POST");
            conn.setRequestProperty("accept", "text/plain");

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(payload);
            out.flush();
            get_response(conn);
        } catch (IOException ex) {
            Logger.getLogger(reservationClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    private void send_typed_response(HttpServletRequest request, HttpServletResponse response, Object data) {
        send_plain(response, data);

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
        
    private void sendRequest(String cityTo, String cityFrom, int guests){
        try {
            HttpURLConnection conn;
            String requestURL = url + "?cityTo=" + cityTo + "&cityFrom=" + cityFrom + "&guests=" + guests;
            conn = get_connection(requestURL, "GET");
            conn.addRequestProperty("accept", "text/plain");
            conn.connect();
            get_response(conn);
        }
        catch(IOException | NullPointerException e) { System.err.println(e); }
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
        int id = 0;
        
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
                        id = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 3){
                        time = Integer.parseInt(value[1]);
                    }
                    else if(i == 4){
                        price = Integer.parseInt(value[1]);
                    }
                    else if(i == 5){
                        cityTo = value[1];
                    }
                    else if(i == 6){
                        cityFrom = value[1];
                    }
                }
                if(cityTo.equals(OrchestratorService.cityTo)){
                    results += "  Flight to:" + " $endl";
                }
                else{
                    results += "  Return Flight:" + " $endl";
                }  
                results += "    Option " + id + " From " + cityFrom + " to " + cityTo + " at " + time + "  Availability: " + availability + " seats" + "  Price: $" + price + " $endl";
            }
            else if(fields[0].equals("hotel")){
                if(hotelNum == 0){
                    results += "\nHotels:" + " $endl";
                }
                hotelNum++;
                for(int i = 1; i < fields.length; i++){
                    String[] value = fields[i].split(":");
                    if(i == 1){
                        id = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 3){
                        name = value[1];
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
                results += "    Option " + id + " Hotel name: " + name + " in " + cityTo + " for up to " + guests + " guests.  Availability: " + availability + " rooms" + "  Price: $" + price + " $endl";
            }
            else{
                if(vehicleNum == 0){
                    results += "\nVehicles:" + " $endl";
                }
                vehicleNum++;
                for(int i = 1; i < fields.length; i++){
                    String[] value = fields[i].split(":");
                    if(i == 1){
                        id = Integer.parseInt(value[1]);
                    }
                    else if(i == 2){
                        availability = Integer.parseInt(value[1]);
                    }
                    else if(i == 3){
                        make = value[1];
                    }
                    else if(i == 4){
                        model = value[1];
                    }
                    else if(i == 5){
                        guests = Integer.parseInt(value[1]);
                    }
                    else if(i == 6){
                        price = Integer.parseInt(value[1]);
                    }
                    else if(i == 7){
                        cityTo = value[1];
                    }
                }
                results += "    Option " + id + " Type: " + make + " " + model + " in " + cityTo + ".  Seats " + guests + " passenger.  Availability: " + availability + " cars" + "  Price: $" + price + " $endl";
            }
        } 
        return results;
    }
    
    private HttpURLConnection get_connection(String url_string, String verb) {
        HttpURLConnection conn = null;
        try {
            URL receivedURL = new URL(url_string);
            conn = (HttpURLConnection) receivedURL.openConnection();
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
            String next;
            
            while ((next = reader.readLine()) != null) xml += next;
            
            if(urlStr.contains("FServ")){
                String resp = formatResp(xml);
                send_typed_response(request, response, resp);
                xml = "";
            } 
        }
        catch(IOException e) { System.err.println(e); }
    }
}
