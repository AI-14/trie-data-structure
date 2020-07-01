/*************************************************************************************
 * Information and Computer Science (ICS) Department                                 *
 * College of Computer Sciences and Engineering (CCSE)                               *
 * King Fahd University of Petroleum and Minerals - KFUPM                            *
 * Dhahran, Saudi Arabia                                                             *
 *                                                                                   *
 * Course - ICS 202 / Term 192                                                       *
 * Project Name - TRIE DATA STRUCTURE                                                *
 *                                                                                   *
 * @author Amaan Izhar (ID: 201781130)                                               *
 * @Instructor Mr. Faisal Alvi                                                       *
 *************************************************************************************/

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
            primaryStage.setTitle("Trie");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/CSS/StyleTrie.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().setValue(false);
            primaryStage.show();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to load the main window.");
            alert.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
