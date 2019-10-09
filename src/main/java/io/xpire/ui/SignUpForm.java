package io.xpire.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SignUpForm implements Initializable {

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXButton signUp;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmPassword;

    @FXML
    void handleBack(ActionEvent event) {

    }

    @FXML
    void handleSignUp(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
