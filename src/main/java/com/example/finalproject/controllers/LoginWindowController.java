package com.example.finalproject.controllers;

import com.example.finalproject.Main;
import com.example.finalproject.utils.AESCryptoprocessor;
import com.example.finalproject.utils.MySQLConnector;
import com.example.finalproject.utils.Storing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    @FXML
    private Button loginbutton;
    @FXML
    private TextField usernameinput;
    @FXML
    private PasswordField passwordinput;
    @FXML
    private Label errortext;
    @FXML
    private CheckBox rememberCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errortext.setText("");
        loginbutton.setOnAction(event -> {
            String username = usernameinput.getText();
            String password = passwordinput.getText();
            if (rememberCheckbox.isSelected()){
                try{
                    savePassword(username, password);
                }catch (Exception e){
                    System.out.println(username + "-" + password);
                }
            }
            else {
                MySQLConnector mySQLConnector = MySQLConnector.getInstance();
            }
            MySQLConnector mySQLConnector = MySQLConnector.getInstance();
            if(mySQLConnector.Connect(username,password)){
                try{
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-window.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1280, 680);
                    stage.setTitle("Main Window!");
                    stage.setScene(scene);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            else {
                errortext.setText("Sai tài khoản hoặc mật khẩu");
            }
        } );
    }
    void savePassword(String username, String password){
        Storing.putValueToPreferences("username",username);
        AESCryptoprocessor aesCryptoprocessor = new AESCryptoprocessor();
        String encryptedPassword = aesCryptoprocessor.encrypt(password);
        Storing.putValueToPreferences("password",encryptedPassword);
    }
}
