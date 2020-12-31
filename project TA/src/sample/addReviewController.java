package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Scanner;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class addReviewController {
    @FXML
    private TextField reviewField;
    @FXML
    private TextField ratingReviewField;
    @FXML
    private Button saveReviewButton;
    @FXML
    private Button descButtonClose;
    @FXML
    private Label successAddLabel;
    @FXML
    private Label failAddLabel;

    public void addReview() throws IOException {

        File readfile = new File("loginSession.txt");
        File readfile2 = new File("movieSession.txt");
        Scanner myReader = new Scanner(readfile);
        Scanner myReader2 = new Scanner(readfile2);

        Databaseconnection connectNow = new Databaseconnection();
        Connection connectDB = connectNow.getConnection();

        String username = myReader.nextLine();
        String movieid = myReader2.nextLine();

        String description = reviewField.getText();
        int rating = Integer.parseInt(ratingReviewField.getText());

        String insertFields = "INSERT INTO description(movieid, username, description, rating) VALUES (";
        String insertValues = movieid + ", '" + username + "', '" + description + "', " + rating + ")";
        String insertToAddReview = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToAddReview);
            statement.executeUpdate("UPDATE movies m join (SELECT movieid, AVG(rating) AS avgrating FROM description r GROUP BY movieid) r ON m.id = r.movieid SET m.rating = r.avgrating");
            successAddLabel.setText("Review Added");
            myReader.close();
            myReader2.close();

        } catch (Exception e) {
            failAddLabel.setText("Failed!");
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addReviewButton(ActionEvent event) throws IOException {
        addReview();
    }

    public void setaddReviewCloseButton(ActionEvent event){
        Stage stage = (Stage) descButtonClose.getScene().getWindow();
        stage.close();
    }
}
