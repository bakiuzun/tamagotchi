package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.util.Date;


/*
 * This class is the controller for the ContinueParty.fxml file
 * 
 * It displays all the sessions that are saved in the database
 * 
 * The user can click on a session to continue the party
 * 
 * If the session has a codePin, the user will have to enter it to continue the party
 * 
 * If the session doesn't have a codePin, the user will be redirected to the home screen
 * 
 * If the user clicks on the "Delete Party" button, the session will be deleted from the database
 * 
 */
public class ContinuePartyController implements Initializable {

    /*
     * This class is the cell of the ListView
     * 
     * It displays the name of the tamagotchi, the date of the last connection and two buttons
     * 
     * The first button is "Delete Party" and the second one is "Continue Party"
     * 
     * If the user clicks on "Delete Party", the session will be deleted from the database
     * 
     * If the user clicks on "Continue Party", the user will be redirected to the home screen
     * 
     * If the session has a codePin, the user will have to enter it to continue the party
     * 
     */
    public class SessionListViewCell extends ListCell<Session> {

        private SessionHBox sessionHBox;

        /* 
         * This method is called when the ListView needs to update the cell
         * 
         * It creates a new SessionHBox and sets it as the graphic of the cell
         * 
         * If the cell is empty, it sets the graphic to null
         * 
         * @param session the session that is displayed in the cell
         * @param empty a boolean that is true if the cell is empty
         * 
         */
        @Override
        public void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);
        
            if (empty) {
                //setText(null);
                setGraphic(null);
            } else {
                if (sessionHBox == null) {
                    sessionHBox = new SessionHBox(session,this);
                } 
                setGraphic(sessionHBox);
            }
        }

        /*
         * This method is called when the user clicks on the "Delete Party" button
         * 
         * It deletes the session from the database
         * 
         * If the ListView is empty, it calls the ifEmptySession() method
         * 
         * It sets the graphic of the cell to null
         * 
         */

        public void onDeleteSession(){
             if(allSessions.isEmpty()){
                    ifEmptySession();
                }

            
            setGraphic(null);
        }
    }

    /*
     * This class is the HBox of the ListViewCell
     */
    public class SessionHBox extends HBox {

        Label nameLabel;
        Label lastConLabel;
        ImageView imageView;
        Button continuePartyButton;
        Button deletePartyButton;

        /*
         * This constructor creates a new HBox
         * 
         * It displays the name of the tamagotchi, the date of the last connection and two buttons
         */
        public SessionHBox(Session session,SessionListViewCell listView) {
            
            imageView = new ImageView();
            imageView.setImage(new Image(session.getTamagotchiImgPath()));
            imageView.setFitWidth(400); // Set maximum width to 100 pixels
            imageView.setFitHeight(400);

            nameLabel = new Label(session.getNameGivenToTamagotchi());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-family: Arial;"); // Change font size and family


            Instant instant = Instant.ofEpochSecond(session.getLastConnectionDate());
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = zonedDateTime.format(formatter);
            lastConLabel = new Label(formattedDateTime);
            lastConLabel.setStyle("-fx-font-size: 14px; -fx-font-family: Arial;"); // Change font size and family

            
            continuePartyButton = new Button("Continue Party");
            continuePartyButton.getStyleClass().add("continue-party-button");
            continuePartyButton.setOnAction(e->{
                JsonDatabase.deleteOneSession(session);
            });

            continuePartyButton.setOnAction(e ->{continuePartyClicked(session);});
            allSessions = JsonDatabase.getAllSession();

            deletePartyButton = new Button("Delete Party"); 
            deletePartyButton.getStyleClass().add("delete-party-button");
            deletePartyButton.setOnAction(e ->{
                
                JsonDatabase.deleteOneSession(session);
               
                listView.onDeleteSession();
            });
            
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS); // This will make the spacer grow and push the button to the right
    
            this.getChildren().addAll(imageView, nameLabel, lastConLabel, spacer,deletePartyButton, continuePartyButton); // Add spacer before the button
            this.getStyleClass().add("session-hbox");
            this.setSpacing(20);
            this.setAlignment(Pos.CENTER_LEFT);

        }
    }

    ArrayList<Session> allSessions;
    ListView<Session> sessionListView;


    @FXML
    AnchorPane rootLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       
        allSessions = JsonDatabase.getAllSession();

        if(allSessions.isEmpty()){
            ifEmptySession();
        } else {

         // Set up the ListView to display sessions
        sessionListView = new ListView<>();
        sessionListView.getStyleClass().add("session-listview");
        sessionListView.setCellFactory(listView -> new SessionListViewCell());

        fillSessionListView();

        }





       
    }    

    private void ifEmptySession(){
            /* big button in the middle of the screen to go to the "New party scene" */
            Button newPartyButton = new Button("Create a New Party");
            // the button needs to be in the middle of the screen (centered)

            AnchorPane.setTopAnchor(newPartyButton, 0.0);
            AnchorPane.setBottomAnchor(newPartyButton, 0.0);
            AnchorPane.setLeftAnchor(newPartyButton, 0.0);
            AnchorPane.setRightAnchor(newPartyButton, 0.0);
            newPartyButton.setAlignment(Pos.CENTER);
            // the button needs to be smaller
            newPartyButton.setPrefWidth(40);
            newPartyButton.getStyleClass().add("new-party-button");
            newPartyButton.setOnAction(e ->{
                goToNewPartyController();
            });
            rootLayout.getChildren().add(newPartyButton);
        
    }

    private void fillSessionListView(){
        for (Session session : allSessions) {
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

    private void goToNewPartyController(){
        // change screen
        Stage currentStage = (Stage) rootLayout.getScene().getWindow();
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/new_party.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToHomeController(Session session){
        JsonDatabase.setCurrentTamaFromSession(session);
        //JsonDatabase.currentTamagotchi.setSession(session);
        
           // change screen
        Stage currentStage = (Stage) rootLayout.getScene().getWindow();
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home2.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnCloseRequest(event ->{JsonDatabase.saveExistingSession();});
            stage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}