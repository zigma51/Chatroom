package Controllers;

import Database.DBConnect;
import Database.Main;
import client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;

public class LoginController  {

    @FXML
    private GridPane root;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private Label status;
    
    public static String user;

    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws SQLException {
        
        user = username.getText();
        
        String query = "SELECT * FROM `database2` WHERE `username` = '%s' && `password` = '%s'";
        query = String.format(query,
                username.getText(),
                password.getText());
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            status.setText("Username or password can't be empty.");
        } else {
            try {
                ResultSet set = DBConnect.getStatement().executeQuery(query); //press control + Q on "Execute Query"
                if (set.next()) {
                    //status.setText("Logged in Successfully.");
                    login.getScene().getWindow().hide();
                    new client();
                    
                } else {
                    status.setText("Incorrect username or password");
                }
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }
    }

    @FXML
    void onRegister(ActionEvent event) throws IOException {
        Stage registerStage = Main.stage;
        registerStage.setTitle("Register");
        root = FXMLLoader.load(getClass().getResource("/FXMLs/Register.fxml"));
        registerStage.setScene(new Scene(root));
        registerStage.show();
    }

}
