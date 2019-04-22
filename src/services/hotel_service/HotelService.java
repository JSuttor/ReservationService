/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.hotel_service;

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


public class HotelService extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;    
    private static final String url = "http://localhost:8080/database_service/dbServ";    
     
    private void sendRequest(String optionType, String cityTo, String cityFrom, int guests){
        try {
            HttpURLConnection conn;
            String requestURL = url + "?optionType=" + optionType + "&cityTo=" + cityTo + "&cityFrom=" + cityFrom + "&guests=" + guests;
            System.out.println(requestURL);
            conn = get_connection(requestURL, "GET");
            conn.addRequestProperty("accept", "text/plain");
            conn.connect();
            get_response(conn);
        }
        catch(IOException | NullPointerException e) { System.err.println(e); }
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
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String entry = request.getParameter("entry");
        if (entry == null)
            throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);

        if(!entry.trim().equals("0"))
            sendPostRequest(entry);
        
        String resp = "reservation succeeded";
        send_typed_response(request, response, resp);
    }
    
    private void sendPostRequest(String entry) {
        try {
            String payload = URLEncoder.encode("entry", "UTF-8") + "=" + URLEncoder.encode("hotel," + entry.toString(), "UTF-8");
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
    
    private void get_response(HttpURLConnection conn) {
        try {
            String xml = "";
            
            //Convert to BufferedReader
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next;
            while ((next = reader.readLine()) != null)
                xml += next;
            System.out.println(xml);
            send_typed_response(request, response, xml);
        }
        catch(IOException e) { System.err.println(e); }
    }

    //Called by client and sends request
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        String cityTo = request.getParameter("cityTo");
        String cityFrom = request.getParameter("cityFrom");
        String guests = request.getParameter("guests");
        int guestNum = Integer.parseInt(guests.trim());
        String type = request.getHeader("accept");
        
        try {  
            sendRequest("hotel", cityTo, cityFrom, guestNum);           
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
        
        private void send_xml(HttpServletResponse response, Object data) {
        try {
            try (XMLEncoder enc = new XMLEncoder(response.getOutputStream())) {
                enc.writeObject(data.toString());
            }
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
