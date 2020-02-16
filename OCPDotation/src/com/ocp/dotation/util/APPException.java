/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Jamal
 */
public class APPException {
    
    public static void showNotification(final String message, final String title ){
        Notifications notify = Notifications.create().title(title)
                                            .text(message)
                                            .hideAfter(javafx.util.Duration.seconds(7))
                                            .position(Pos.BOTTOM_RIGHT);
        notify.showWarning();
    }
    
    public static void showErrorMessage(final String message, Alert.AlertType type){
        Alert alert = new Alert( type );
            ScrollPane sc = new ScrollPane();
            JFXTextArea ta = new JFXTextArea(message);
            ta.editableProperty().setValue(Boolean.FALSE);
            alert.getDialogPane().setContent( ta );
            alert.show();
    }
    
    public static void showErrorNotifiction(final String message){
        Stage stage = new Stage();
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog( pane, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialogLayout.setHeading(new Label( message ));
        HBox pan = new HBox();
        JFXButton annuler   = new JFXButton("Annuler");
        annuler.setStyle("-fx-background-color: white; -fx-text-fill:#4d4dff;");
            annuler.setOnAction((ActionEvent ev)->{
                dialog.close();
                stage.close();
            });
        pan.spacingProperty().set( 10 );
        pan.getChildren().addAll( annuler );
        dialogLayout.setActions(pan);
        dialog.show(); 
        
        stage.show();
    }
    
}
