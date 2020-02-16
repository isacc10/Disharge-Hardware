/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.util.APPException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ocpdotation.OCPDotation;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class LoginController implements Initializable {
    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label errorMessage;
    @FXML
    private JFXButton btn_connect;

    
    private OCPDotation application;
    
    public void setApp(OCPDotation application){
        this.application = application;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Identifier(ActionEvent event) {
        try{
            Dao dao  = new Dao();
            if( !userName.getText().trim().isEmpty() && !password.getText().trim().isEmpty()  ){
                if(dao.validateUser(userName.getText().trim(), password.getText().trim())){
                    this.application.gotoMaster();
                }else{
                    errorMessage.setText("Nom d'utilisateur ou Mot de passe\nest incorrect");
                }
            }else{
                errorMessage.setText("SVP, completer les champs requis");
            }
        }catch(Exception e){
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), errorMessage);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
    
}
