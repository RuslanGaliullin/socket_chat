package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServerThreadReader {
    private final Set<Socket> connections = Collections.synchronizedSet(new HashSet<Socket>());

    public void broadcast(String message) {
        try {
            for (Socket i : connections) {
                var now = new PrintStream(i.getOutputStream(), true);
                now.println(message);
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " exception");
        }
    }

    public void add(Socket new_connection) {
        connections.add(new_connection);
    }
}