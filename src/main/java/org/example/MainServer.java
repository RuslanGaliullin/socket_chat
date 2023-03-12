package org.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MainServer {

    public static final int SERVER_PORT = 50001;

    public static void main(String[] args) {
        try (final var server = new ServerSocket(SERVER_PORT);
        ) {
            int number = 0;
            while (true) {
                System.out.println("now");
                final Socket clientConnection = server.accept();

                final var threadReader = new ThreadReader(
                        clientConnection.getInputStream(),
                        "Server"
                );
                threadReader.start();

                System.out.printf("Client%d was connected", number);
                final DataOutput serverOutput = new DataOutputStream(clientConnection.getOutputStream());
                final var threadHandler = new Thread(() -> new ServerHandler(serverOutput));
                threadHandler.start();
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}