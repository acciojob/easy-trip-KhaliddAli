package com.driver.repositories;

import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FlightDb {
    ArrayList<Flight> flights = new ArrayList<>();

    HashMap<Integer , ArrayList<Integer>> passengersInFlight = new HashMap<>();

    public ArrayList<Flight> getFlights() {
        return this.flights;
    }

    public void addPassengerInFlight(Integer f_ID , Integer p_ID) {
        ArrayList<Integer> list =  passengersInFlight.getOrDefault(f_ID,new ArrayList<>());
        list.add(p_ID);
        passengersInFlight.put(f_ID ,list);
    }
    public String removePassengerInFlight(Integer f_ID , Integer p_ID) {
        ArrayList<Integer> list =  passengersInFlight.getOrDefault(f_ID,new ArrayList<>());
        if(list.isEmpty()) return "FAILURE";
        if(!list.contains(p_ID)) return "FAILURE";

        list.remove(Integer.valueOf(p_ID));
        passengersInFlight.put(f_ID ,list);
        return "SUCCESS";
    }

    public int getPassengersInFlight(int id) {
        return passengersInFlight.getOrDefault(id , new ArrayList<>()).size();
    }
    public ArrayList<Integer> getPassengersList(int id) {
        return passengersInFlight.getOrDefault(id , new ArrayList<>());
    }

    public Flight getFlight(int id) {
        for(Flight f : flights) {
            if(f.getFlightId() == id) return f;
        }
        return new Flight();
    }

    public HashMap<Integer , ArrayList<Integer>> getFlightsAndPassengers(){
        return passengersInFlight;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }
}
