/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.vehicle_service;

import services.database_service.*;

/**
 *
 * @author docto
 */
public abstract class AbstractOption {
    int guests;
    String cityTo;
    String cityFrom;
    int flightTime;
    int availability;
    String name;
    int price;
    String make;
    String model;
    int time;
    
    @Override
    abstract public String toString();
    
}
