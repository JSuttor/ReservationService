/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.flight_service;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;


/**
 *
 * @author docto
 */
public class FlightOption extends AbstractOption{
    public FlightOption(int availability, String cityTo, String cityFrom, int price, int time){
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
        String result = "flight,availability:" + availability + ",time:" + time + ",price:" + price + ",cityTo:" + cityTo + ",cityFrom:" + cityFrom + "|";
        
        return result;
    }
    
     private static final String url = "http://localhost:8080/database_service/dbServ";
    /**
     * Creates new form CalculatorClient
     */

    
    private void sendRequest(String optionType, String cityTo, String cityFrom, int guests){
        try {
            HttpURLConnection conn = null;
            String requestURL = url + "?optionType=" + optionType + "&cityTo=" + cityTo + "&cityFrom=" + cityFrom + "&guests=" + guests;
            System.out.println(requestURL);
            conn = get_connection(requestURL, "GET");
            conn.addRequestProperty("accept", "text/plain");
            conn.connect();
            get_response(conn);
        }
        catch(IOException e) { System.err.println(e); }
        catch(NullPointerException e) { System.err.println(e); }
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
            System.out.println(xml);
            //String[] parts = xml.split(", ");
            //for(String part : parts){
               
            //}
        }
        catch(IOException e) { System.err.println(e); }
    }
    
    
    public List<FlightOption> fetchFlightData(String cityTo, String cityFrom, int guests){
        List<FlightOption> optionList = new ArrayList<FlightOption>();
        FlightOption FO = new FlightOption();
        
        FO.sendRequest("flight", cityTo, cityFrom, guests);
        
        
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
            String resp = "";
            List<FlightOption> fOptionList = new ArrayList<FlightOption>();
            if(optionType.trim().equals("flight")){
                fOptionList = fetchFlightData(cityTo, cityFrom, guestNum);
                for(FlightOption fO : fOptionList){
                    resp += fO.toString();
                }
                System.out.println(resp);
            }
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
    
}