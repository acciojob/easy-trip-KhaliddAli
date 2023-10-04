package com.driver.repositories;

import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FlightDb {
    HashMap<Integer , Flight> flights = new HashMap<>();

    HashMap<Integer , ArrayList<Integer>> passengersInFlight = new HashMap<>();

    public HashMap<Integer,Flight> getFlights() {
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
        int indx = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i)==p_ID) indx = i;
        }
        if(indx == -1) return "FAILURE";
        list.remove(indx);
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
        if(flights.containsKey(id)) return flights.get(id);
        return new Flight();
    }

    public HashMap<Integer , ArrayList<Integer>> getFlightsAndPassengers(){
        return passengersInFlight;
    }

    public void addFlight(Flight flight) {
        flights.put(flight.getFlightId() , flight);
    }
}
