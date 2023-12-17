package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.example.model.JsonDatabase;
import com.example.model.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class ContinuePartyController implements Initializable {

    public class SessionListViewCell extends ListCell<Session> {

        private SessionHBox sessionHBox;
        
        @Override
        public void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);
        
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (sessionHBox == null) {
                    sessionHBox = new SessionHBox(session);
                } 
                setGraphic(sessionHBox);
            }
        }
    }

    public class SessionHBox extends HBox {

        Label nameLabel;
        ImageView imageView;
        Button continuePartyButton;

        public SessionHBox(Session session) {
            
            imageView = new ImageView();
            imageView.setImage(new Image(session.getTamagotchi_img_path()));
            imageView.setFitWidth(400); // Set maximum width to 100 pixels
            imageView.setFitHeight(400);

            nameLabel = new Label(session.getNom_donner_tamagotchi());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-family: Arial;"); // Change font size and family
            
            continuePartyButton = new Button("Continue Party");
            continuePartyButton.setOnAction(e ->{continuePartyClicked(session);});
            
            this.getChildren().addAll(imageView, nameLabel,continuePartyButton);
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER_LEFT);
        }
    }

    ArrayList<Session> allSessions;
    ListView<Session> sessionListView;
    ActionEvent event;

    @FXML
    AnchorPane rootLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       
        allSessions = JsonDatabase.getAllSession();
            // Set up the ListView to display sessions
        sessionListView = new ListView<>();
        
        sessionListView.setCellFactory(listView -> new SessionListViewCell());

        for (Session session : allSessions) {
            // Create a custom cell to display the session image and name
            sessionListView.getItems().add(session);
        }

        sessionListView.prefHeightProperty().bind(rootLayout.prefHeightProperty());
        sessionListView.prefWidthProperty().bind(rootLayout.prefWidthProperty());
        rootLayout.getChildren().add(sessionListView);
       
    }    


    private void continuePartyClicked(Session session){
        if (session.getCodePin() != -1){ 
            showAlertCodePin(session);
        } else {
            goToHomeController(session);
        }

    }

    private void showAlertCodePin(Session session){
        Alert codePinAlert = new Alert(Alert.AlertType.NONE);
        codePinAlert.setTitle("Write the codePin");

            // Create a GridPane to hold the input controls
        GridPane gridPane = new GridPane();
        TextField codePinField = new TextField();
        codePinField.setTextFormatter(pinCode_helper());
        codePinField.setPromptText("Enter codePin");
        gridPane.add(codePinField, 0, 0);

        codePinAlert.getDialogPane().setContent(gridPane);

        ButtonType okButtonType = new ButtonType("OK");
        codePinAlert.getButtonTypes().setAll(okButtonType);

        codePinAlert.showAndWait().ifPresent(result -> {
            if (result == okButtonType) {
                Integer enteredCodePin = Integer.parseInt(codePinField.getText());
                if (enteredCodePin == session.getCodePin()){
                    goToHomeController(session);
                } else {
                   showErrorAlert();
                }
            }
        });


    }


    private TextFormatter<Integer> pinCode_helper(){

         TextFormatter<Integer> textFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                null,
                change -> {
                    String newText = change.getControlNewText();

                    // Use a regular expression to allow only numeric input
                    if (newText.matches("\\d*")) {
                        return change;
                    } else {
                        return null;
                    }
                }
        );
        return textFormatter;
    }

    private void showErrorAlert(){
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Le code pin entré est mauvais");
        errorAlert.showAndWait();


    }

    private void goToHomeController(Session session){
        JsonDatabase.setCurrentTamaFromSession(session);
        JsonDatabase.currentTamagotchi.setSession(session);
        
           // change screen
        Stage currentStage = (Stage) rootLayout.getScene().getWindow();
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}