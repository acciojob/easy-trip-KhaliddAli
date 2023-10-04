package com.driver.Services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repositories.AirportDB;
import com.driver.repositories.CityDB;
import com.driver.repositories.FlightDb;
import com.driver.repositories.PassengersDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Service
public class AirportServices {
    @Autowired
    AirportDB airportDB;
    @Autowired
    CityDB cityDB;
    @Autowired
    FlightDb flightDb;
    @Autowired
    PassengersDB passengersDB;

    public void addtoDB(Airport airport) {

        airportDB.add(airport);
    }

    public String getLargestAirport() {
        String ans = "";
        ArrayList<Airport> list = airportDB.getListOfAirports();
        int num =0;
        for(Airport port : list) {
            int portNumbers = port.getNoOfTerminals();
            if(portNumbers == num) {
                ans = compareNames(ans , port.getAirportName());
            }
            else if(portNumbers > num) {
                num = portNumbers;
                ans = port.getAirportName();
            }
        }

        return ans;
    }
    public String compareNames(String s1 , String s2) {
        int val = s1.compareTo(s2);
        if(val<0) return s2;
        else if(val == 0) return s1;
        else return s1;
    }

    public double getShortestFlight(City fromcity , City toCity) {
        HashMap<Integer,Flight> listOfFlights = flightDb.getFlights();
        double duration = Double.MAX_VALUE;
        for(Flight f : listOfFlights.values()) {
            City c1 = f.getFromCity();
            City c2 = f.getToCity();
            if(c1 == fromcity && c2 == toCity){
                duration= Math.min(duration , f.getDuration());
            }
        }
        if(duration == Double.MAX_VALUE) return -1;
        return duration;
    }

    public int getNumberOfPeople(Date date, String airportName) {
        Airport airport = airportDB.getAirportByName(airportName);
        City city = airport.getCity();

        HashMap<Integer,Flight> flights= flightDb.getFlights();
        int count = 0;
        for(Flight f : flights.values()) {
            Date d_date = f.getFlightDate();
            Date f_date = f.getFlightDate();
            double duration = f.getDuration();
            if(duration>=24.00){
                int days = (int)duration%24;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(f_date);
                calendar.add(Calendar.DAY_OF_MONTH,days);
                f_date = calendar.getTime();

            }
            City c1 = f.getFromCity();
            if((f_date == date || d_date==date) && c1 == city) {
                count += flightDb.getPassengersInFlight(f.getFlightId());
            }
        }

        return count;
    }

    public int getFare(Integer flightId) {
        int countOfPassengers = flightDb.getPassengersInFlight(flightId);
        return 3000 + (countOfPassengers*50);
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        ArrayList<Integer> list = flightDb.getPassengersList(flightId);
        Flight flight = flightDb.getFlight(flightId);

        if(list.size()> flight.getMaxCapacity()) return "FAILURE";

        if(list.contains(passengerId)) return "FAILURE";


        flightDb.addPassengerInFlight(flightId , passengerId);

        return "SUCCESS";

    }

    public String cancelTicket(Integer flightId, Integer passengerId) {
        return flightDb.removePassengerInFlight(flightId , passengerId);
    }

    public int getTotalBookings(Integer passengerId) {
        HashMap<Integer , ArrayList<Integer>> map = flightDb.getFlightsAndPassengers();
        int count =0;
        for(ArrayList<Integer> list : map.values()) {
            if(list.contains(passengerId)) {
                count++;
            }
        }
        return count;
    }

    public void addFlight(Flight flight) {
        flightDb.addFlight(flight);
    }

    public String getAirportName(Integer flightId) {
        Flight f = flightDb.getFlight(flightId);
        City c = f.getFromCity();

        ArrayList<Airport> airportArrayList = airportDB.getListOfAirports();
        for(Airport a : airportArrayList) {
            if(a.getCity().equals(c)) return a.getAirportName();
        }

        return null;
    }

    public int getRevenue(Integer flightId) {
        //number of passengers
        int n = flightDb.getPassengersInFlight(flightId);
        if(n==0) return 3000;
        int revenue = n*3000;
        revenue += sumation(n);
        return revenue;
    }

    private int sumation(int n) {
        int sum =0;

        for(int i=1;i<=n;i++){
            sum += (i*50);
        }
        return sum;
    }

    public void addPassenger(Passenger passenger) {
        passengersDB.addPassenger(passenger);
    }
}
