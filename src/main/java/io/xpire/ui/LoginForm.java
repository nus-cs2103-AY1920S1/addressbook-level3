package io.xpire.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import io.xpire.MainApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoginForm implements Initializable {

    @FXML
    private JFXButton signUp;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXButton login;

    @FXML
    private JFXPasswordField password;

    @FXML
    void handleLogin(ActionEvent event) {
        final String username = this.username.getText();
        final String password = this.password.getText();
        if (username.equals("test") && password.equals("password")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Wrong credentials");
        }
    }

    @FXML
    void handleSignUp(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
