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
    

    
    
    
}
