/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Demandeur;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.util.APPException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class UtilisateurController implements Initializable {
    @FXML
    private TableView<Demandeur> tv_listUsers;
    @FXML
    private JFXTextField tf_matricule;
    @FXML
    private JFXTextField tf_nom;
    @FXML
    private JFXTextField tf_prenom;
    @FXML
    private JFXTextField tf_entite;
    @FXML
    private JFXTextField tf_SA;
    @FXML
    private JFXButton sauvegarder;
    @FXML
    private JFXButton reinitialiser;
    @FXML
    private JFXButton importer;
    @FXML
    private Label path_fileExcel;
    @FXML
    private JFXTextField tf_search;
    @FXML
    private JFXButton btn_search;
    @FXML
    private TableColumn<Demandeur, String> tc_matricule;
    @FXML
    private TableColumn<Demandeur, String> tc_nom;
    @FXML
    private TableColumn<Demandeur, String> tc_prenom;
    @FXML
    private TableColumn<Demandeur, String> tc_entite;
    @FXML
    private TableColumn<Demandeur, String> tc_SA;
    @FXML
    private TableColumn tc_modifier;
    @FXML
    private Label errorMessage;
    
    /***********************************************************************/
        private Demandeur demandeur ;
        private ObservableList<Demandeur> demandeurs;
        private FilteredList filter ;
        private Demandeur itemClicked;
        private int index;
    
    /***********************************************************************/
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            // TODO
            demandeurs = FXCollections.observableArrayList();
            Callback<TableColumn<Demandeur, String>, TableCell<Demandeur, String>> cellFactoryEdit  =  (TableColumn<Demandeur, String> param)->{
                
                final TableCell<Demandeur, String> cell = new TableCell<Demandeur,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if( empty ){
                            setGraphic(null);
                            setText(null);
                        }else{
                            
                            final FontAwesomeIconView edit = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                            edit.setStyle("-fx-fill:#008fb3;");  edit.setSize("16");
                            
                            final JFXButton editBtn = new JFXButton("",edit);
                            editBtn.prefWidth( 62 );
                            editBtn.setMinWidth( 62 );
                            editBtn.setMaxWidth( 62 );
                            editBtn.setStyle("-fx-border-color: #4d4dff; -fx-background-radius:3; -fx-border-radius:3; ");
                            editBtn.contentDisplayProperty().setValue(ContentDisplay.CENTER);
                            editBtn.alignmentProperty().setValue(Pos.CENTER);
                            
                            editBtn.setOnAction((ActionEvent e)->{
                                Demandeur demandeur = getTableView().getItems().get(getIndex());
                                fillForm(demandeur);
                                itemClicked = demandeur;
                                index = getIndex();
                                System.out.println("Clicked: "+demandeur);
                            });
                            setGraphic( editBtn );
                            setText(null);
                        }
                    };
                };
                
                //return the cellule created
                return cell;
            };
            tc_modifier.setCellFactory( cellFactoryEdit );
            
            try{
                tc_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
                tc_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                tc_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                tc_entite.setCellValueFactory(new PropertyValueFactory<>("entite"));
                tc_SA.setCellValueFactory(new PropertyValueFactory<>("SA"));
                
                tv_listUsers.setItems( demandeurs );
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
            Dao dao = new Dao();
            demandeurs.setAll(dao.getDemandeurs());
            
            filter = new FilteredList(demandeurs, e->true);
            
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }    
    @FXML
    private void Sauvegarder(ActionEvent event) {
        if(checkForm()){
            try {
                demandeur =new Demandeur( 
                        tf_matricule.getText().trim(),
                        tf_nom.getText().trim(),
                        tf_prenom.getText().trim(),
                        tf_entite.getText().trim(),
                        tf_SA.getText().trim()
                );
                System.out.println("Out:: "+demandeur);
                Dao dao = new Dao();
                if(itemClicked!=null && demandeur.getMatricule().equals(itemClicked.getMatricule())){
                    //Update process
                    demandeurs.set(index, demandeur);
                    dao.updateDemandeur(demandeur);
                }else{
                    //Insert process
                    if(dao.isUniqueDemandeur(tf_matricule.getText().trim())){
                        errorMessage.setText("Demandeur existe déja!");
                        return;
                    }
                    demandeurs.add(demandeur);
                    dao.insertDemandeur(demandeur);
                }
                itemClicked = null;
                Reinitialiser( null );
            } catch (Exception e) {
                APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void Reinitialiser(ActionEvent event) {
        try{
            tf_matricule.setText("");
            tf_nom.setText("");
            tf_prenom.setText("");
            tf_entite.setText("");
            tf_SA.setText("");
            errorMessage.setText("");
        }catch(Exception e){
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }

    @FXML
    private void Importer(ActionEvent event) {
    }

    @FXML
    private void Search(KeyEvent event) {
        tf_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)->{
            String test ;
            filter.setPredicate((Predicate<? super Demandeur>)(Demandeur dem)->{
                
                if(newValue.isEmpty() || newValue==null){                    
                    return true;
                }
                else if( dem.getMatricule().contains(newValue) ){
                    return true;
                }else if( dem.getNom().contains(newValue) ){
                    return true;
                }else if( dem.getPrenom().contains(newValue) ){
                    return true;
                }else if( dem.getEntite().contains(newValue) ){
                    return true;
                }else if( dem.getSA().contains(newValue) ){
                    return true;
                }
                return false;
            });           
        });
        
        SortedList<Demandeur> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(tv_listUsers.comparatorProperty());
        tv_listUsers.setItems( sort );
        
    }

    @FXML
    private void ButtonSearch(ActionEvent event) {
    }
    
    
    //*************************************************************************//
    final String error = "Erreur de saisie";
    public boolean checkForm(){
        animateMessage();
        try{
            if( !tf_matricule.getText().isEmpty() ){//&& dao.isUnique
                if( !tf_nom.getText().isEmpty() && !tf_prenom.getText().isEmpty() && !tf_entite.getText().isEmpty() && !tf_SA.getText().isEmpty() ){
                    errorMessage.setText("");
                    return true;
                }
            }
        }catch(Exception e){
            errorMessage.setText(error);
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            return false;
        }
        errorMessage.setText(error);
        return false;
    }
    
    public void fillForm( final Demandeur demandeur){
        try{
            tf_matricule.setText(demandeur.getMatricule());
            tf_nom.setText(demandeur.getNom());
            tf_prenom.setText(demandeur.getPrenom());
            tf_entite.setText(demandeur.getEntite());
            tf_SA.setText(demandeur.getSA());
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
