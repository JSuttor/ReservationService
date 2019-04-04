/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import containers.AbstractOption;
import containers.flightOption;
import containers.hotelOption;
import containers.vehicleOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Joshua Suttor
 */
public class DBService {

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
    
    public List<AbstractOption> fetchData(String cityTo, String cityFrom, int guests){
        List<AbstractOption> optionList = new ArrayList<AbstractOption>();
        try {
            ResultSet rs;
            AbstractOption AO;
            Statement stmt = con.createStatement();
            
            //construct sql request for flight data
            String flightSQL = "SELECT * FROM FLIGHTS WHERE CITYTO = '" + cityTo + "' AND CITYFROM = '" + cityFrom + "' AND AVAILABILITY > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(flightSQL);
            
            //process data from flight record
            while(rs.next()){
                AO = new flightOption(rs.getInt("AVAILABILITY"), rs.getString("CITYTO"), rs.getString("CITYFROM"), rs.getInt("PRICE"), rs.getInt("FLIGHTTIME"));
                optionList.add(AO);
            }
            
            //construct sql request for flight data
            String flightHomeSQL = "SELECT * FROM FLIGHTS WHERE CITYTO = '" + cityFrom + "' AND CITYFROM = '" + cityTo + "' AND AVAILABILITY > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(flightHomeSQL);
            
            //process data from flight record
            while(rs.next()){
                AO = new flightOption(rs.getInt("AVAILABILITY"), rs.getString("CITYTO"), rs.getString("CITYFROM"), rs.getInt("PRICE"), rs.getInt("FLIGHTTIME"));
                optionList.add(AO);
            }
            
            //construct sql request for hotel data
            String hotelSQL = "SELECT * FROM HOTEL_ROOMS WHERE CITY = '" + cityTo + "' AND MAXGUESTS > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(hotelSQL);
            
            //process data from hotel record
            while(rs.next()){
                AO = new hotelOption(rs.getInt("AVAILABILITY"), rs.getString("HOTELNAME"), rs.getInt("MAXGUESTS"), rs.getString("CITY"), rs.getInt("PRICE"));
                optionList.add(AO);
            }
            
            //construct sql request for vehicle data
            String vehicleSQL = "SELECT * FROM VEHICLE_RENTALS WHERE CITY = '" + cityTo + "' AND MAXPASSENGERS > " + guests + " ORDER BY PRICE";
            
            //execute query
            rs = stmt.executeQuery(vehicleSQL);
            
            //process data from hotel record
            while(rs.next()){
                AO = new vehicleOption(rs.getInt("AVAILABILITY"), rs.getString("CARMAKE"), rs.getString("CARMODEL"), rs.getInt("MAXPASSENGERS"), rs.getString("CITY"), rs.getInt("PRICE"));
                optionList.add(AO);
            }
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return optionList;
    }
    
    
    public static void main(String[] args){
        DBService db = new DBService();
        db.fetchData("Pittsburgh", "Erie", 3);
    }
}
