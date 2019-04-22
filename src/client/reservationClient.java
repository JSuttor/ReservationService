/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import client.Client.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class reservationClient {
    
private static final String url = "http://localhost:8080/orchestrator_service/OServ";  
    
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
            xml = xml.replace("$endl", "\n");
            System.out.println(xml);

        }
        catch(IOException e) { }
    }

        public static void main(String args[]) {
            //SOAP Login
            reservationClient rc = new reservationClient(); 
            LogRequestPortTypeService service = new LogRequestPortTypeService();
            LogRequestPortType s = service.getLogRequestPortTypePort();
            
            LoginComplete L = new LoginComplete();
            L.setUsername("Bob");
            L.setPassword("Joe");
 
            //REST Service
            System.out.println(s.login(L).getCompleted()); //GetCompleted = get string
            String cityTo = "Pittsburgh";
            String cityFrom = "Erie";
            int guests = 3;
            
            rc.sendRequest(cityTo, cityFrom, guests);
            
            rc.sendPostRequest("4 3,3 3,1 3,2 3");

            rc.sendRequest(cityTo, cityFrom, guests);
        }
}
