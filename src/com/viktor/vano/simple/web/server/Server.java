package com.viktor.vano.simple.web.server;

import java.io.*;
import java.net.*;

import static com.viktor.vano.simple.web.server.FileManager.readOrCreateFile;

public class Server extends Thread{
    private int port;
    private boolean run = true;
    private String request = "";

    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;

    public Server(int port)
    {
        this.port = port;
    }

    public void stopServer()
    {
        this.run = false;
        try {
            if(socket!=null)
                socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(server!=null)
                server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (run)
        {
            socket = null;
            server = null;

            // starts server and waits for a connection
            try
            {
                server = new ServerSocket(port);
                System.out.println("Server started");

                System.out.println("Waiting for a client ...");

                socket = server.accept();
                System.out.println("Client accepted");
                socket.setSoTimeout(60000);

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                try
                {
                    this.request = "";
                    this.request = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                if(request.contains("GET") || request.contains("get"))
                {
                    String web = readOrCreateFile("web.html");
                    String response = "HTTP/1.1 200 OK\r\nContent-Length: "
                            + web.length()
                            + "\r\nContent-Type: text/html\r\n\r\n"
                            + web;
                    out.write(response);
                }

                br.close();
                out.close();

            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            System.out.println("Closing connection");


            try {
                if(socket!=null)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if(server!=null)
                    server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Text server stopped successfully.");
    }
}