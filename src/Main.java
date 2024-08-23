import com.sun.net.httpserver.HttpServer;

import controllers.UserController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/users", new UserController());
        server.start();
        System.out.println("Server running on port 8000");
    }
}