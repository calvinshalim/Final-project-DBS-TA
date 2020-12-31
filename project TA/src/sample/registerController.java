package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class registerController {
    @FXML
    private Button closeButton;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private PasswordField registerPasswordTextField;
    @FXML
    private PasswordField confirmPasswordTextField;
    @FXML
    private Label confirmPasswordMessageLabel;
    @FXML
    private TextField registerFirstnameTextField;
    @FXML
    private TextField registerLastnameTextField;
    @FXML
    private TextField registerUsernameTextField;



    public void closeButtonOnAction (ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void registerButtonOnAction(ActionEvent event) {
        if (registerPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
            registerUser();
            confirmPasswordMessageLabel.setText("");

        } else {
            confirmPasswordMessageLabel.setText("Password does not match!");
        }
    }

    public void registerUser() {
        Databaseconnection connectNow = new Databaseconnection();
        Connection connectDB = connectNow.getConnection();

        String username = registerUsernameTextField.getText();
        String pass = registerPasswordTextField.getText();
        String first_name = registerFirstnameTextField.getText();
        String last_name = registerLastnameTextField.getText();

        String insertFields = "INSERT INTO users(username, pass, first_name, last_name) VALUES ('";
        String insertValues = username + "', '" +  pass + "', '" + first_name + "', '" + last_name + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registerMessageLabel.setText("User register success!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}

