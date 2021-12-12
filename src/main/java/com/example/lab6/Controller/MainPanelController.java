package com.example.lab6.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * JavaFX FXML Main Panel Controller
 *
 * @author Denisa Dragota
 * @version 12/12/2021
 */
public class MainPanelController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button studentProfileButton;

    @FXML
    private Button teacherProfileButton;

    @FXML
    private Text titleCenter;

    @FXML
    private Text titleLeft1;
    @FXML
    private Text titleLeft2;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    /**
     * redirects to StudentSide
     */
    @FXML
    private void studentProfile() {
        Parent parent;
        try {

            Stage stage = (Stage) studentProfileButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentLoginView.fxml"));
            parent = loader.load();

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Student Side");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * redirects to TeacherSide
     */
    @FXML
    private void teacherProfile() {
        Parent parent;
        try {

            Stage stage = (Stage) teacherProfileButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeacherLoginView.fxml"));
            parent = loader.load();

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Teacher Side");
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

