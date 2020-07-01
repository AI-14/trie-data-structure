// @author Amaan Izhar

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {

    @FXML
    private Separator horizontalSeparator, verticalSeparator;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField textField;

    @FXML
    private Button createEmptyTrie, createWithLetters, insertWord, deleteWord, listWithPrefix, printWords, searchWord, sizeTrie, reset;

    @FXML
    private ListView<String> listView;

    private Trie trie;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLabel.setText("WELCOME TO THE PROJECT - TRIE!");
        listView.getItems().add("Instructions: ");
        listView.getItems().add("1. This application uses TRIE data structure to store the words.");
        listView.getItems().add("2. Make sure to type the word in the text area before creating a trie with initial letters,");
        listView.getItems().add("     inserting, deleting, searching a word or listing the words that start with a prefix.");
        listView.getItems().add("3. When you click on [RESET EVERYTHING], the application goes to the initial state");
        listView.getItems().add("     where you will be able to create another trie.");
        listView.getItems().add("GOOD LUCK! I hope you like it.");
        listView.getItems().add("\n\n\n\n\n\n\n\n");
        listView.getItems().add("@developer Amaan Izhar");
        listView.getItems().add("@github AI-14");
        textField.clear();
        createWithLetters.setDisable(true);
        insertWord.setDisable(true);
        deleteWord.setDisable(true);
        listWithPrefix.setDisable(true);
        printWords.setDisable(true);
        searchWord.setDisable(true);
        sizeTrie.setDisable(true);
        reset.setDisable(true);
    }

    @FXML
    void actionCreateEmptyTrie(ActionEvent event) {
        trie = new Trie();
        messageLabel.setText("An empty trie is created.");
        createWithLetters.setDisable(false);
        insertWord.setDisable(false);
        deleteWord.setDisable(false);
        listWithPrefix.setDisable(false);
        printWords.setDisable(false);
        searchWord.setDisable(false);
        sizeTrie.setDisable(false);
        createEmptyTrie.setDisable(true);
        reset.setDisable(false);
    }

    @FXML
    void actionCreateWithLetters(ActionEvent event) {
        String str = textField.getText().toUpperCase();
        textField.clear();
        if(str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Text Field is empty!");
            alert.show();
        }
        else {
            Permutations permutations = new Permutations(str);
            permutations.permute(); //generating all permutations of str.
            try {
                listView.getItems().clear();
                Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
                ArrayList<String> dictionary = new ArrayList<>();
                while (fileScan.hasNext()) {
                    dictionary.add(fileScan.next());
                }
                for(String el : permutations.getPermutationList()) {
                    int index = Collections.binarySearch(dictionary, el);
                    if(index > 0)
                        trie.insert(el);
                }
                if(trie.size() == -1)
                    messageLabel.setText("No valid words are present in the dictionary");
                else
                    messageLabel.setText("Valid permuted words are inserted.");
            }
            catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error in opening the text file! Try again or recheck the file path.");
                alert.show();
            }
        }
    }

    @FXML
    void actionInsertWord(ActionEvent event) {
        String str = textField.getText().toUpperCase();
        textField.clear();
        if(str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Text Field is empty!");
            alert.show();
        }
        else {
            trie.insert(str);
            textField.clear();
            messageLabel.setText("The word " + str + " is inserted.");
        }
    }

    @FXML
    void actionDeleteWord(ActionEvent event) {
        String str = textField.getText().toUpperCase();
        textField.clear();
        if(str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Text Field is empty!");
            alert.show();
        }
        else if(!trie.contains(str)) {
            messageLabel.setText("Cannot delete as " + str + " is NOT present in the trie.");
        }
        else {
            trie.delete(str);
            textField.clear();
            messageLabel.setText("The word " + str + " is deleted.");
        }
    }

    @FXML
    void actionListWithPrefix(ActionEvent event) {
        listView.getItems().clear();
        String str = textField.getText().toUpperCase();
        textField.clear();
        if(str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Text Field is empty!");
            alert.show();
        }
        else if(trie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trie is empty!");
            alert.show();
        }
        else if(!trie.isPrefix(str)) {
            messageLabel.setText(str + " is NOT a valid prefix in the trie.");
        }
        else {
            String[] prefixWords = trie.allWordsPrefix(str);
            for(String el : prefixWords)
                listView.getItems().add(el);
        }
    }

    @FXML
    void actionPrintWords(ActionEvent event) {
        if(trie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trie is empty!");
            alert.show();
        }
        else {
            listView.getItems().clear();
            ArrayList<String> allWords = trie.print();
            listView.getItems().addAll(allWords);
        }
    }

    @FXML
    void actionSearchWord(ActionEvent event) {
        String str = textField.getText().toUpperCase();
        textField.clear();
        if(str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Text Field is empty!");
            alert.show();
        }
        else if(trie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trie is empty!");
            alert.show();
        }
        else if(!trie.contains(str))
            messageLabel.setText("The word " + str + " is NOT present in the trie.");
        else if(trie.contains(str))
            messageLabel.setText("This word " + str + " is present in the trie.");
    }

    @FXML
    void actionSizeTrie(ActionEvent event) {
        messageLabel.setText("The size of the trie = " + trie.size() + ".");
    }

    @FXML
    void actionReset(ActionEvent event) {
        trie.clear();
        listView.getItems().clear();
        createEmptyTrie.setDisable(false);
        createWithLetters.setDisable(true);
        insertWord.setDisable(true);
        deleteWord.setDisable(true);
        listWithPrefix.setDisable(true);
        printWords.setDisable(true);
        searchWord.setDisable(true);
        sizeTrie.setDisable(true);
        messageLabel.setText("WELCOME TO THE PROJECT - TRIE!");
        listView.getItems().add("Instructions: ");
        listView.getItems().add("1. This application uses TRIE data structure to store the words.");
        listView.getItems().add("2. Make sure to type the word in the text area before creating a trie with initial letters,");
        listView.getItems().add("     inserting, deleting, searching a word or listing the words that start with a prefix.");
        listView.getItems().add("3. When you click on [RESET EVERYTHING], the application goes to the initial state");
        listView.getItems().add("   where you will be able to create another trie.");
        listView.getItems().add("GOOD LUCK! I hope you like it.");
        listView.getItems().add("\n\n\n\n\n\n\n\n");
        listView.getItems().add("@developer Amaan Izhar");
        listView.getItems().add("@github AI-14");
    }
}
