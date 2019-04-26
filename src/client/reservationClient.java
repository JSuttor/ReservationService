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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class reservationClient {
    
    private static final String url = "https://localhost:8443/orchestrator_service/OServ";  
    String testSuccess = "";
    
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
    
    private void sendLoginRequest(String username, String password){
        try {
            HttpURLConnection conn = null;
            String requestURL = url + "?username=" + username + "&password=" + password;
            conn = get_connection(requestURL, "GET");
            conn.addRequestProperty("accept", "text/plain");
            conn.connect();
            get_login_response(conn);
        }
        catch(IOException e) { System.err.println(e); }
        catch(NullPointerException e) { System.err.println(e); }
        
    }

    private void sendPostRequest(String entry) {
        try {
            String payload = URLEncoder.encode("entry", "UTF-8") + "=" + URLEncoder.encode(entry, "UTF-8");
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
    
    private void get_login_response(HttpURLConnection conn) {
        try {
            String xml = "";
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next = null;
            while ((next = reader.readLine()) != null)
                xml += next;
            testSuccess = xml;
        }
        catch(IOException e) { }
    }

        public static void main(String args[]) {
            //SSL
            System.setProperty("javax.net.ssl.trustStore", "tomcat/tomcathome/my.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            System.setProperty("javax.net.ssl.keyStore", "tomcat/tomcathome/my.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
            
            //SOAP Login
            reservationClient rc = new reservationClient(); 

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            
            boolean login = false;
            
            while(login == false){
                System.out.println("Enter username");
                String username = myObj.nextLine();
                System.out.println("Enter password");
                String password = myObj.nextLine();
                //Bob
                //Joe
                //REST Service
                rc.sendLoginRequest(username, password);
                if(rc.testSuccess.equals("Login Successful!")){
                    login = true;
                    System.out.println("Login Successful!");
                }
                else{
                    rc.sendLoginRequest(username, password);
                }
                if(!rc.testSuccess.equals("Login Successful!")){
                    System.out.println("Login failed. Please try again.");
                }
            }
            
            System.out.println("Enter the city you're going to");
            String cityTo = myObj.nextLine();
            System.out.println("Enter the city you're coming from");
            String cityFrom = myObj.nextLine();
            System.out.println("Enter number of guests");
            String guests = myObj.nextLine();
            int guestNum = Integer.parseInt(guests.trim());
       
            rc.sendRequest(cityTo, cityFrom, guestNum);
            
            System.out.println("Enter option number of chosen car");
            String carOption = myObj.nextLine();
            System.out.println("Enter option number of chosen hotel");
            String hotelOption = myObj.nextLine();
            System.out.println("Enter option number of chosen flight to your destination");
            String flightOption1 = myObj.nextLine();
            System.out.println("Enter option number of chosen return flight");
            String flightOption2 = myObj.nextLine();
            
            String request = carOption + " " + guestNum + "," + hotelOption + " " + guestNum + "," + flightOption1 + " " + guestNum + "," + flightOption2 + " " + guestNum;
            rc.sendPostRequest(request);
            
            //clean servlets
            //rc.sendRequest(cityTo, cityFrom, guestNum);
        }
}
