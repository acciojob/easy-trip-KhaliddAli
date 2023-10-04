package com.driver.repositories;

import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class PassengersDB {
    ArrayList<Passenger> listOfPassengers = new ArrayList<>();

    public void addPassenger(Passenger passenger) {
        listOfPassengers.add(passenger);
    }


}
