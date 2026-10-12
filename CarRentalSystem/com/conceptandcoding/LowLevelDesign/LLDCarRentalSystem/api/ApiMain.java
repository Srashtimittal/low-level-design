package com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.api;

import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Store;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.User;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Product.Car;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Product.Vehicle;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Product.VehicleType;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMain {

    public static void main(String[] args) throws Exception {

        User user = new User();
        user.setUserId(1);

        Vehicle vehicle1 = new Car();
        vehicle1.setVehicleID(1);
        vehicle1.setVehicleType(VehicleType.CAR);

        Vehicle vehicle2 = new Car();
        vehicle2.setVehicleID(2);
        vehicle2.setVehicleType(VehicleType.CAR);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        Store store = new Store();
        store.setVehicles(vehicles);

        Map<Integer, User> usersById = new HashMap<>();
        usersById.put(user.getUserId(), user);

        Map<Integer, Vehicle> vehiclesById = new HashMap<>();
        vehiclesById.put(vehicle1.getVehicleID(), vehicle1);
        vehiclesById.put(vehicle2.getVehicleID(), vehicle2);

        CarRentalApiHandler handler =
                new CarRentalApiHandler(store, usersById, vehiclesById);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        handler.register(server);
        server.start();

        System.out.println("Car Rental API started on port 8080");
    }
}
