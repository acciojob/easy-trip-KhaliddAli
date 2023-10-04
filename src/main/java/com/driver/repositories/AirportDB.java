package com.driver.repositories;

import com.driver.model.Airport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class AirportDB {

    ArrayList<Airport> DB = new ArrayList<>();

    public ArrayList<Airport> getListOfAirports() {
        return this.DB;
    }

    public void add(Airport airport) {
        DB.add(airport);
    }

    public Airport getAirportByName(String airportName) {
        for(Airport a: DB) {
            if(a.getAirportName().equals(airportName)) return a;
        }
        return new Airport();
    }
}
