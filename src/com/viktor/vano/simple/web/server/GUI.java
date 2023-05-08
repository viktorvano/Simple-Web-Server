package com.viktor.vano.simple.web.server;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.viktor.vano.simple.web.server.FileManager.*;

public class GUI extends Application {
    private final String version = "20230508";
    private final int width = 400;
    private final int height = 120;

    private Server server;
    private Label labelVisits;
    public static int visits = 0;
    private Timeline timelineGUI;

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

        String visitsString = readOrCreateFile("webVisits.txt");
        try{
            visits = Integer.parseInt(visitsString);
        }catch (Exception e)
        {
            e.printStackTrace();
            visits = 0;
            writeToFile("webVisits.txt", String.valueOf(visits));
        }

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

        labelVisits = new Label("Visits: " + visits);
        labelVisits.setFont(Font.font("Arial", 18));
        labelVisits.setLayoutX(130);
        labelVisits.setLayoutY(15);
        pane.getChildren().add(labelVisits);

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

        timelineGUI = new Timeline(new KeyFrame(Duration.millis(2500), event -> {
            labelVisits.setText("Visits: " + visits);
        }));
        timelineGUI.setCycleCount(Timeline.INDEFINITE);
        timelineGUI.play();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        server.stopServer();
    }
}
