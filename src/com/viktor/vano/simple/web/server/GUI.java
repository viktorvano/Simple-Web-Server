package com.viktor.vano.simple.web.server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.viktor.vano.simple.web.server.FileManager.*;

public class GUI extends Application {
    private final String version = "20230215";
    private final int width = 400;
    private final int height = 120;

    private Server server;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String portString = readOrCreateFile("webServerPort.txt");
        int port;
        try{
            port = Integer.parseInt(portString);
        }catch (Exception e)
        {
            e.printStackTrace();
            port = 9000;
            writeToFile("webServerPort.txt", String.valueOf(port));
        }
        server = new Server(port);
        server.start();

        Pane pane = new Pane();

        Scene scene = new Scene(pane, width, height);

        stage.setTitle("Simple Web Server " + version);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());
        stage.setResizable(false);

        Label labelPort = new Label("Port: " + port);
        labelPort.setFont(Font.font("Arial", 24));
        labelPort.setLayoutX(130);
        labelPort.setLayoutY(50);
        pane.getChildren().add(labelPort);

        try
        {
            Image icon = new Image(getClass().getResourceAsStream("web.jpg"));
            stage.getIcons().add(icon);
            System.out.println("Icon loaded on the first attempt...");
        }catch(Exception e)
        {
            try
            {
                Image icon = new Image("web.jpg");
                stage.getIcons().add(icon);
                System.out.println("Icon loaded on the second attempt...");
            }catch(Exception e1)
            {
                System.out.println("Icon failed to load...");
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        server.stopServer();
    }
}
