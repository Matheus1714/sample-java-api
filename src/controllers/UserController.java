package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import models.UserModel;
import views.UserView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserController implements HttpHandler {
    private static List<UserModel> users = new ArrayList<>();
    private UserView userView = new UserView();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            this.handleGetUsers(exchange);
        } else if ("POST".equals(method)) {
            this.handleCreateUser(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handleGetUsers(HttpExchange exchange) throws IOException {
        String userResponse = users.stream()
            .map(user -> this.userView.render(user))
            .collect(Collectors.joining(",", "[", "]"));

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, userResponse.getBytes().length);
        
        OutputStream os = exchange.getResponseBody();
        os.write(userResponse.getBytes());
        os.close();
    }

    private void handleCreateUser(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        String[] pairs = body.replace("{", "").replace("}", "").split(",");

        String name = null;
        String email = null;

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            if (key.equals("name")) {
                name = value;
            } else if (key.equals("email")) {
                email = value;
            }
        }

        if (name != null && email != null) {
            UserModel newUser = new UserModel(UUID.randomUUID().toString(), name, email);
            users.add(newUser);

            String response = userView.render(newUser);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(201, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(400, -1);
        }
    }
}
