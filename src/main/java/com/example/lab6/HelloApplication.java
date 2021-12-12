package com.example.lab6;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application
 * @author Denisa Dragota
 * @version 12/12/2021
 */
public class HelloApplication extends Application {

    public void start(Stage stage) throws Exception {

        //In order to demonstrate the updates in the Teacher Side application while modifying the Student Side application
        //we display two JavaFX application to run

        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("mainPanelView.fxml"));

        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("mainPanelView.fxml"));

        Scene scene = new Scene(root1);
        Scene scene2=new Scene(root2);

        Stage newStage=new Stage();
        newStage.setScene(scene2);
        newStage.setTitle("secondary");

        stage.setScene(scene);
        stage.setTitle("principal");
        stage.show();
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}