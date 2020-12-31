package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class mainPageController implements Initializable {

    @FXML
    private TableView<mainPageModelTable> movieTable;
    @FXML
    private TableColumn<mainPageModelTable, Integer> movieTableId;
    @FXML
    private TableColumn<mainPageModelTable, String> movieTableName;
    @FXML
    private TableColumn<mainPageModelTable, Double> movieTableRating;
    @FXML
    private Button mainPageCloseButton;
    @FXML
    private TableView<mainPageModelTable2> descTable;
    @FXML
    private TableColumn<mainPageModelTable2, String> descTableUsername;
    @FXML
    private TableColumn<mainPageModelTable2, String> descTableDesc;
    @FXML
    private TableColumn<mainPageModelTable2, Double> descTableRating;
    @FXML
    private Button addReviewButton;


    ObservableList<mainPageModelTable> oblist = FXCollections.observableArrayList();
    ObservableList<mainPageModelTable2> oblist2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //table 1
        movieTableId.setCellValueFactory(new PropertyValueFactory<>("movieid"));
        movieTableName.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        movieTableRating.setCellValueFactory(new PropertyValueFactory<>("movierating"));

        //table 2
        descTableUsername.setCellValueFactory(new PropertyValueFactory<>("descUsername"));
        descTableDesc.setCellValueFactory(new PropertyValueFactory<>("descDescription"));
        descTableRating.setCellValueFactory(new PropertyValueFactory<>("descRating"));


        try {
            Databaseconnection connectNow = new Databaseconnection();
            Connection connectDB = connectNow.getConnection();
            String seemovielist = "SELECT * FROM movies";
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(seemovielist);

            while (queryResult.next()) {
                oblist.add(new mainPageModelTable(queryResult.getInt("id"), queryResult.getString("name"), queryResult.getDouble("rating")));
                movieTable.setItems(oblist);
                movieTable.setOnMouseClicked(e -> {
                    try {
                        createmovieID();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    oblist2.clear();
                    events();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void createmovieID() throws IOException {
        int movieID = movieTable.getSelectionModel().getSelectedItem().getMovieid();
        try {
            File myObj = new File("./movieSession.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("./movieSession.txt");
            myWriter.write(Integer.toString(movieID));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void deleteFileSession() throws IOException {
        File delfile = new File("movieSession.txt");
        delfile.delete();
    }


    private void events() {
        try {
            Databaseconnection connectNow2 = new Databaseconnection();
            Connection connectDB2 = connectNow2.getConnection();
            Statement statement2 = connectDB2.createStatement();
            String seedesclist = "SELECT username, description,rating FROM description WHERE movieid = " + movieTable.getSelectionModel().getSelectedItem().getMovieid() + "";
            ResultSet queryResult2 = statement2.executeQuery(seedesclist);

            while(queryResult2.next()) {
                oblist2.add(new mainPageModelTable2(queryResult2.getString("username"), queryResult2.getString("description"), queryResult2.getDouble("rating")));
                descTable.setItems(oblist2);
            }

        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    public void setMainPageCloseButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) mainPageCloseButton.getScene().getWindow();
        deleteFileSession();
        stage.close();
    }

    public void reviewPageLoader() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addReview.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    public void setAddReviewButton(ActionEvent event) {
        reviewPageLoader();
    }

    public void updateMovieRating() {
        try {
            Databaseconnection connectNow = new Databaseconnection();
            Connection connectDB = connectNow.getConnection();
            ResultSet rs = connectDB.createStatement().executeQuery("UPDATE movies m join (SELECT movieid, AVG(rating) AS avgrating FROM description r GROUP BY movieid) r ON m.id = r.movieid SET m.rating = r.avgrating");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}

