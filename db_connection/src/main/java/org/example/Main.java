package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    private static final Gson gson = new Gson();
    private static final UserDao userDao = new UserDao();

    public static void main(String[] args) throws IOException {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );

        server.createContext("/users", Main::handleUsers);

        server.start();

        System.out.println(
                "Server running at http://localhost:8080"
        );
    }

    private static void handleUsers(HttpExchange exchange) {

        try {

            String method = exchange.getRequestMethod();

            switch (method) {

                case "GET" ->
                        getUsers(exchange);

                case "POST" ->
                        createUser(exchange);

                case "PUT" ->
                        updateUser(exchange);

                case "DELETE" ->
                        deleteUser(exchange);

                default ->
                        sendResponse(
                                exchange,
                                405,
                                "Method not allowed"
                        );
            }

        } catch (IllegalArgumentException e) {

            try {
                sendResponse(
                        exchange,
                        400,
                        e.getMessage()
                );
            } catch (IOException ignored) {
            }

        } catch (Exception e) {

            e.printStackTrace();

            try {
                sendResponse(
                        exchange,
                        500,
                        "Internal server error"
                );
            } catch (IOException ignored) {
            }
        }
    }

    private static void getUsers(HttpExchange exchange)
            throws Exception {

        String response =
                gson.toJson(userDao.getAllUsers());

        sendJson(
                exchange,
                200,
                response
        );
    }

    private static void createUser(HttpExchange exchange)
            throws Exception {

        UserRequest request =
                readRequest(exchange);

        validateRequest(request);

        UserDao.User user =
                userDao.createUser(
                        request.name(),
                        request.email()
                );

        sendJson(
                exchange,
                201,
                gson.toJson(user)
        );
    }

    private static void updateUser(HttpExchange exchange)
            throws Exception {

        int id = getUserId(exchange);

        UserRequest request =
                readRequest(exchange);

        validateRequest(request);

        boolean updated =
                userDao.updateUser(
                        id,
                        request.name(),
                        request.email()
                );

        if (!updated) {
            sendResponse(
                    exchange,
                    404,
                    "User not found"
            );
            return;
        }

        sendResponse(
                exchange,
                200,
                "User updated"
        );
    }

    private static void deleteUser(HttpExchange exchange)
            throws Exception {

        int id = getUserId(exchange);

        boolean deleted =
                userDao.deleteUser(id);

        if (!deleted) {
            sendResponse(
                    exchange,
                    404,
                    "User not found"
            );
            return;
        }

        sendResponse(
                exchange,
                200,
                "User deleted"
        );
    }

    private static UserRequest readRequest(
            HttpExchange exchange
    ) throws IOException {

        String body =
                new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );

        return gson.fromJson(
                body,
                UserRequest.class
        );
    }

    private static int getUserId(
            HttpExchange exchange
    ) {

        String path =
                exchange.getRequestURI().getPath();

        String[] parts =
                path.split("/");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "User id is required"
            );
        }

        try {
            return Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid user id"
            );
        }
    }

    private static void validateRequest(
            UserRequest request
    ) {

        if (request == null
                || request.name() == null
                || request.name().isBlank()
                || request.email() == null
                || request.email().isBlank()) {

            throw new IllegalArgumentException(
                    "Name and email are required"
            );
        }
    }

    private static void sendJson(
            HttpExchange exchange,
            int status,
            String response
    ) throws IOException {

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json"
        );

        sendResponse(
                exchange,
                status,
                response
        );
    }

    private static void sendResponse(
            HttpExchange exchange,
            int status,
            String response
    ) throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        exchange.getResponseBody()
                .write(bytes);

        exchange.getResponseBody()
                .close();
    }

    private record UserRequest(
            String name,
            String email
    ) {}
}