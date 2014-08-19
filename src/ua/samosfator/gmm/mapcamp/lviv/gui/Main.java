package ua.samosfator.gmm.mapcamp.lviv.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Lviv MapCamp 2014 Utils");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
        root.resize(641, 481);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
