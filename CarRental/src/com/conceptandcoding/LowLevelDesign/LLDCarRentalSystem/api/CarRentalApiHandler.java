package com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.api;

import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Reservation;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Store;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.User;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Product.Vehicle;
import com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.Product.VehicleType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRentalApiHandler {

    private final Store store;
    private final Map<Integer, User> usersById;
    private final Map<Integer, Vehicle> vehiclesById;
    private final Map<Integer, Reservation> reservationsById = new HashMap<>();
    private int nextReservationId = 1;

    public CarRentalApiHandler(
            Store store,
            Map<Integer, User> usersById,
            Map<Integer, Vehicle> vehiclesById) {

        this.store = store;
        this.usersById = usersById;
        this.vehiclesById = vehiclesById;
    }

    public void register(HttpServer server) {
        server.createContext("/vehicles", this::handleGetVehicles);
        server.createContext("/reservations", this::handleCreateReservation);
        server.createContext("/reservations/complete", this::handleCompleteReservation);
    }

    private void handleGetVehicles(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> query = parseQuery(exchange.getRequestURI());
            String type = query.getOrDefault("type", "CAR");

            List<Vehicle> vehicles =
                    store.getVehicles(VehicleType.valueOf(type.toUpperCase()));

            StringBuilder response = new StringBuilder("[");

            for (Vehicle vehicle : vehicles) {
                if (response.length() > 1) response.append(",");

                response.append("{")
                        .append("\"vehicleId\":").append(vehicle.getVehicleID())
                        .append(",\"vehicleType\":\"").append(vehicle.getVehicleType()).append("\"")
                        .append("}");
            }

            response.append("]");
            send(exchange, 200, response.toString());

        } catch (Exception e) {
            send(exchange, 400, "{\"error\":\"Invalid vehicle type\"}");
        }
    }

    private void handleCreateReservation(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> request = SimpleJson.parse(readBody(exchange));

            int userId = Integer.parseInt(request.get("userId"));
            int vehicleId = Integer.parseInt(request.get("vehicleId"));

            User user = usersById.get(userId);
            Vehicle vehicle = vehiclesById.get(vehicleId);

            if (user == null) {
                send(exchange, 404, "{\"error\":\"User not found\"}");
                return;
            }

            if (vehicle == null) {
                send(exchange, 404, "{\"error\":\"Vehicle not found\"}");
                return;
            }

            Reservation reservation = store.createReservation(vehicle, user);

            int reservationId = nextReservationId++;
            reservationsById.put(reservationId, reservation);

            send(exchange, 201,
                    "{\"reservationId\":" + reservationId
                            + ",\"userId\":" + userId
                            + ",\"vehicleId\":" + vehicleId
                            + "}");

        } catch (Exception e) {
            send(exchange, 400,
                    "{\"error\":\"userId and vehicleId are required\"}");
        }
    }

    private void handleCompleteReservation(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> request = SimpleJson.parse(readBody(exchange));
            int reservationId = Integer.parseInt(request.get("reservationId"));

            Reservation reservation = reservationsById.get(reservationId);

            if (reservation == null) {
                send(exchange, 404, "{\"error\":\"Reservation not found\"}");
                return;
            }

            boolean completed = store.completeReservation(reservationId);

            if (!completed) {
                send(exchange, 409, "{\"error\":\"Reservation could not be completed\"}");
                return;
            }

            reservationsById.remove(reservationId);

            send(exchange, 200,
                    "{\"message\":\"Reservation completed\"}");

        } catch (Exception e) {
            send(exchange, 400,
                    "{\"error\":\"reservationId is required\"}");
        }
    }

    private Map<String, String> parseQuery(URI uri) {
        Map<String, String> result = new HashMap<>();
        String query = uri.getQuery();

        if (query == null || query.isBlank()) return result;

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                result.put(parts[0], parts[1]);
            }
        }

        return result;
    }

    private String readBody(HttpExchange exchange) throws IOException {
        return new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8);
    }

    private void send(HttpExchange exchange, int status, String response)
            throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
