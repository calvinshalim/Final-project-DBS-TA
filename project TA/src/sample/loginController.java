package sample;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent ;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.*;

import java.io.IOException;
import java.sql.Connection;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.net.URL;

public class loginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private PasswordField loginPasswordField;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {
        if (loginUsernameField.getText().isBlank() ==  false && loginPasswordField.getText().isBlank() == false)  {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please Enter username and password ");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        deleteFileSession();
        stage.close();
    }

    public void validateLogin() throws IOException {
        Databaseconnection connectNow = new Databaseconnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + loginUsernameField.getText() + "' AND pass = '" + loginPasswordField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    createFileSession();
                    writeToSessionFile(loginUsernameField.getText());
                    mainPage();
                } else {
                    loginMessageLabel.setText("Invalid username or password!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public String getusername(){
        String username = loginUsernameField.getText();
        return username;
    }

    public void registerLoginButtonOnAction (ActionEvent event) {
        createUser();
    }

//go to register page
    public void createUser() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createFileSession() throws IOException {
        try {
            File myObj = new File("./loginSession.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void writeToSessionFile(String sessionOwner) {
        try {
            FileWriter myWriter = new FileWriter("./loginSession.txt");
            myWriter.write(sessionOwner);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void deleteFileSession() throws IOException {
        File delfile = new File("loginSession.txt");
        delfile.delete();
    }

    //go to main page after successful login
    public void mainPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 650, 400));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


}
