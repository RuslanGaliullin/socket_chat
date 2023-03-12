package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class ThreadReader extends Thread implements AutoCloseable {

    private final InputStream inputStream;

    private final BroadcastHandler broadcastHandler;

    private volatile boolean isRunning = true;

    public ThreadReader(InputStream inputStream, String clientName, BroadcastHandler broadcastHandler) {
        this.inputStream = inputStream;
        this.broadcastHandler = broadcastHandler;
    }

    @Override
    public void run() {
        try (final var reader = new InputStreamReader(inputStream)) {
            final var bufferedReader = new BufferedReader(reader);
            String receivedData;
            do {
                receivedData = bufferedReader.readLine();
                System.out.println(MessageFormat.format(
                        "[{0}] Received Data: {1}",
                        receivedData.split(" ")[0], receivedData.split(" ", 2)[1]));
                broadcastHandler.broadcast(receivedData);
            } while (!receivedData.equals("exit") && isRunning);
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " exception");
        }
    }

    @Override
    public void close() throws Exception {
        isRunning = false;
    }
}