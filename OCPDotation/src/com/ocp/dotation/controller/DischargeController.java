/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Dotation;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.util.APPException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ocpdotation.OCPDotation;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class DischargeController implements Initializable {
    @FXML
    private JFXComboBox<String> nature_affectation;
    @FXML
    private JFXTextField matricule_utilisateur;
    @FXML
    private JFXTextField nserie_nvMateriel;
    @FXML
    private JFXTextField ns_materielRetourner;
    @FXML
    private JFXCheckBox cb_DRI;
    @FXML
    private JFXCheckBox cb_ficheExpertise;
    @FXML
    private JFXCheckBox cb_miseEnRebut;
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
    private TableView<Dotation> tv_Disharge;
    @FXML
    private TableColumn tc_DMI;
    @FXML
    private TableColumn tc_matriculeUtilisateur;
    @FXML
    private TableColumn tc_serieNvMateriel;
    @FXML
    private TableColumn tc_serieMaterielRet;
    @FXML
    private TableColumn tc_modifier;
    @FXML
    private TableColumn tc_details;
    @FXML
    private JFXTextField DMI;
    @FXML
    private Label year;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTextField netBios;
    @FXML
    private JFXTextField numTicketSD;
    

    
    
    /***********************************************************************/
        private Dotation dotation ;
        private ObservableList<Dotation> dotations;
        private FilteredList filter ;
        private Dotation itemClicked;
        private int index;
    /***********************************************************************/
       
    private OCPDotation application;
    @FXML
    private TableColumn tc_state;
    
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
        try {
            configTableView();
            // TODO
            Dao dao = new Dao();
            
            
            //1
            ArrayList<String> natures = new ArrayList<>();
            natures.add("Nouvelle Dotation");
            natures.add("Renouvellement");
            natures.add("Retour Materiel");
            nature_affectation.getItems().addAll(natures);
            //2
            TextFields.bindAutoCompletion(matricule_utilisateur, dao.getSuggestionDataForUsers());
            
            //3
            TextFields.bindAutoCompletion(nserie_nvMateriel, dao.getSuggestionDataForMateriels());
            
            //4
            TextFields.bindAutoCompletion(ns_materielRetourner, dao.getSuggestionDataForMateriels());
            
            //5
            nature_affectation.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)->{
                switch (newValue) {
                    case "Nouvelle Dotation":
                            nserie_nvMateriel.disableProperty().setValue(Boolean.FALSE);
                            ns_materielRetourner.disableProperty().setValue(Boolean.TRUE); ns_materielRetourner.setText("");
                            netBios.disableProperty().setValue(Boolean.TRUE); netBios.setText("");
                            numTicketSD.disableProperty().setValue(Boolean.TRUE); numTicketSD.setText("");
                        break;
                    case "Renouvellement":
                            nserie_nvMateriel.disableProperty().setValue(Boolean.FALSE);
                            ns_materielRetourner.disableProperty().setValue(Boolean.FALSE);
                            netBios.disableProperty().setValue(Boolean.FALSE);
                            numTicketSD.disableProperty().setValue(Boolean.FALSE);                        
                        break;
                    case "Retour Materiel":
                            nserie_nvMateriel.disableProperty().setValue(Boolean.TRUE);
                            nserie_nvMateriel.setText("");
                            ns_materielRetourner.disableProperty().setValue(Boolean.FALSE);
                            netBios.disableProperty().setValue(Boolean.FALSE);
                            numTicketSD.disableProperty().setValue(Boolean.FALSE);                        
                        break;
                }
            });
            
            
        } catch (Exception ex) {
            Logger.getLogger(DischargeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    @FXML
    private void Sauvegarder(ActionEvent event) {
        
        if(  nature_affectation.getValue()!=null ){
            try {
                Dotation mDotation ;
                Dao dao = new Dao();
                switch ( nature_affectation.getValue() ) {
                    case "Nouvelle Dotation":
                        if(checkFormNvDotation()){
                            mDotation = new Dotation(DMI.getText().trim(),
                                    date.getValue(),
                                    "Nouvelle Dotation",
                                    "ADMIN",
                                    matricule_utilisateur.getText().trim(),
                                    nserie_nvMateriel.getText().trim());
                            mDotation.updateState(0, cb_DRI.selectedProperty().get());
                            mDotation.updateState(1, cb_ficheExpertise.selectedProperty().get());
                            mDotation.updateState(2, cb_miseEnRebut.selectedProperty().get());
                            //dotations.add(mDotation);
                            if(itemClicked!=null && mDotation.getDMI().equals(itemClicked.getDMI())){
                                //update process
                                dotations.set( index, mDotation );
                                dao.updateDotation(mDotation);
                            }else{
                                //insert process
                                if(dao.isUniqueDotation( DMI.getText().trim() )){
                                    APPException.showErrorNotifiction("DMI existe déja!");
                                    break;
                                }
                                dotations.add(mDotation);
                                dao.insertDotation(mDotation);
                            }
                        }else{
                            //DAO
                            APPException.showErrorNotifiction("Veuillez remplir les champs requis!");
                        }
                        break;
                        
                    case "Renouvellement":
                        if(checkFormRenouvellement()){
                            mDotation = new Dotation(DMI.getText().trim(),
                                    date.getValue(),
                                    "Renouvellement",
                                    "ADMIN",
                                    matricule_utilisateur.getText().trim(),
                                    nserie_nvMateriel.getText().trim(),
                                    ns_materielRetourner.getText().trim(),
                                    netBios.getText().trim(),
                                    numTicketSD.getText().trim());
                            mDotation.updateState(0, cb_DRI.selectedProperty().get());
                            mDotation.updateState(1, cb_ficheExpertise.selectedProperty().get());
                            mDotation.updateState(2, cb_miseEnRebut.selectedProperty().get());
                            //dotations.add(mDotation);
                            if(itemClicked!=null && mDotation.getDMI().equals(itemClicked.getDMI())){
                                //update process
                                System.out.println("INDEXES"+index);
                                dotations.set( index, mDotation );
                                dao.updateDotation(mDotation);
                            }else{
                                //insert process
                                if(dao.isUniqueDotation( DMI.getText().trim() )){
                                    APPException.showErrorNotifiction("DMI existe déja!");
                                    break;
                                }
                                dotations.add(mDotation);
                                dao.insertDotation(mDotation);
                            }
                        }else{
                            //DAO
                            APPException.showErrorNotifiction("Veuillez remplir les champs requis!");
                        }
                        break;
                    case "Retour Materiel":
                        if(checkFormRetourMateriel()){
                            mDotation = new Dotation(DMI.getText().trim(),
                                    date.getValue(),
                                    "Retour Materiel",
                                    "ADMIN",
                                    matricule_utilisateur.getText().trim(),
                                    ns_materielRetourner.getText().trim(),
                                    netBios.getText().trim(),
                                    numTicketSD.getText().trim());
                            mDotation.updateState(0, cb_DRI.selectedProperty().get());
                            mDotation.updateState(1, cb_ficheExpertise.selectedProperty().get());
                            mDotation.updateState(2, cb_miseEnRebut.selectedProperty().get());
                            //dotations.add(mDotation);
                            if(itemClicked!=null && mDotation.getDMI().equals(itemClicked.getDMI())){
                                //update process
                                dotations.set( index, mDotation );
                                dao.updateDotation(mDotation);
                            }else{
                                //insert process
                                if(dao.isUniqueDotation( DMI.getText().trim() )){
                                    APPException.showErrorNotifiction("DMI existe déja!");
                                    break;
                                }
                                dotations.add(mDotation);
                                dao.insertDotation(mDotation);
                            }
                        }else{
                            //DAO
                            APPException.showErrorNotifiction("Veuillez remplir les champs requis!");
                        }
                        break;
                        
                } 
            } catch (Exception ex) {
                APPException.showErrorNotifiction("ERROR FATALE !!! ");
            }
        }
        
    }

    @FXML
    private void Reinitialiser(ActionEvent event) {
        try{
            DMI.setText("");
            date.setValue(null);
            nature_affectation.setValue("");
            matricule_utilisateur.setText("");
            nserie_nvMateriel.setText("");
            ns_materielRetourner.setText("");
            netBios.setText("");
            numTicketSD.setText("");
            cb_DRI.setSelected(false);
            cb_ficheExpertise.setSelected(false);
            cb_miseEnRebut.setSelected(false);
            itemClicked = null;
            nserie_nvMateriel.disableProperty().setValue(Boolean.FALSE);
            ns_materielRetourner.disableProperty().setValue(Boolean.FALSE);
            netBios.disableProperty().setValue(Boolean.FALSE);
            numTicketSD.disableProperty().setValue(Boolean.FALSE);  
        }catch(Exception e){
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }

    @FXML
    private void Importer(ActionEvent event) {
        Stage stage = new Stage();
        File file ;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(stage);
        if ( file != null ) {
            gotoImportationProcess(file);
        }
    }

    @FXML
    private void Search(KeyEvent event) {
        tf_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)->{
            String test ;
            filter.setPredicate((Predicate<? super Dotation>)(Dotation mDotation)->{
                
                if(newValue.isEmpty() || newValue==null){                    
                    return true;
                }
                else if( mDotation.getDMI().contains(newValue) ){
                    return true;
                }else if( mDotation.getMatriculeDemandeur().contains(newValue) ){
                    return true;
                }else if( mDotation.getNumSerieNvMateriel().contains(newValue) ){
                    return true;
                }else if( mDotation.getNumSerieMaterielRet().contains(newValue) ){
                    return true;
                }
                return false;
            });           
        });
        
        SortedList<Dotation> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(tv_Disharge.comparatorProperty());
        tv_Disharge.setItems( sort );
    }

    @FXML
    private void ButtonSearch(ActionEvent event) {
    }
    
    
    //*****//
    /*
     * Cette methode est utile pour vérifier si l'utilisateur a rempli tous les champs requis.
     * Les  champs requis = Les champs nécessaire dans le cas d'une nouvelle dotation
     */
    public boolean checkFormNvDotation(){
        if( !DMI.getText().trim().isEmpty() && date.getValue()!=null 
                && !matricule_utilisateur.getText().trim().isEmpty() && !nserie_nvMateriel.getText().trim().isEmpty())
        {
            return true;
        }
        return false;
    }
    
    /*
     * Cette methode est utile pour vérifier si l'utilisateur a rempli tous les champs requis.
     * Les  champs requis = Les champs nécessaire dans le cas d'un renouvellement
     */
    public boolean checkFormRenouvellement(){
        if( checkFormNvDotation() && checkFormRetourMateriel())
        {
            return true;
        }
        return false;
    }
    
    /*
     * Cette methode est utile pour vérifier si l'utilisateur a rempli tous les champs requis.
     * Les  champs requis = Les champs nécessaire dans le cas d'un retour de materiel
     */
    public boolean checkFormRetourMateriel(){
        if( !DMI.getText().trim().isEmpty() && date.getValue()!=null 
                && !matricule_utilisateur.getText().trim().isEmpty() && !ns_materielRetourner.getText().trim().isEmpty()
                && !netBios.getText().trim().isEmpty() && !numTicketSD.getText().trim().isEmpty())
        {
            return true;
        }
        return false;
    }
    
    private void configTableView(){
        dotations = FXCollections.observableArrayList();
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryState  =  (TableColumn<Dotation, String> param)->{
                
                final TableCell<Dotation, String> cell = new TableCell<Dotation,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if( empty ){
                            setGraphic(null);
                            setText(null);
                        }else{
                            
                            final FontAwesomeIconView valid = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                            valid.setStyle("-fx-fill:green;");  valid.setSize("16");
                            
                            final FontAwesomeIconView invalid = new FontAwesomeIconView(FontAwesomeIcon.WARNING);
                            invalid.setStyle("-fx-fill:red;");  invalid.setSize("16");
                            
                            Dotation mDotation = getTableView().getItems().get(getIndex());
                            
                            if(mDotation.getState()[0]==true && mDotation.getState()[1]==true && mDotation.getState()[2]==true){
                                setGraphic(valid);
                            }else setGraphic(invalid);
                            
                            setText(null);
                        }
                    };
                };
                
                //return the cellule created
                return cell;
            };
            tc_state.setCellFactory( cellFactoryState );
            
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryShow  =  (TableColumn<Dotation, String> param)->{
                
                final TableCell<Dotation, String> cell = new TableCell<Dotation,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if( empty ){
                            setGraphic(null);
                            setText(null);
                        }else{
                            
                            final FontAwesomeIconView edit = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
                            edit.setStyle("-fx-fill:#008fb3;");  edit.setSize("16");
                            
                            final JFXButton showBtn = new JFXButton("",edit);
                            showBtn.prefWidth( 62 );
                            showBtn.setMinWidth( 62 );
                            showBtn.setMaxWidth( 62 );
                            showBtn.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
                            showBtn.setStyle("-fx-background-color: white; -fx-background-radius:3; -fx-border-radius:3; ");
                            showBtn.contentDisplayProperty().setValue(ContentDisplay.CENTER);
                            showBtn.alignmentProperty().setValue(Pos.CENTER);
                            
                            showBtn.setOnAction((ActionEvent e)->{
                                Dotation mDotation = getTableView().getItems().get(getIndex());
                                details( mDotation );
                            });
                            setGraphic( showBtn );
                            setText(null);
                        }
                    };
                };
                
                //return the cellule created
                return cell;
            };
            tc_details.setCellFactory( cellFactoryShow );
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryEdit  =  (TableColumn<Dotation, String> param)->{
                
                final TableCell<Dotation, String> cell = new TableCell<Dotation,String>(){
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
                            editBtn.buttonTypeProperty().setValue(JFXButton.ButtonType.RAISED);
                            editBtn.setStyle("-fx-background-color: white; -fx-background-radius:3; -fx-border-radius:3; ");
                            editBtn.contentDisplayProperty().setValue(ContentDisplay.CENTER);
                            editBtn.alignmentProperty().setValue(Pos.CENTER);
                            
                            editBtn.setOnAction((ActionEvent e)->{
                                Dotation mDotation = getTableView().getItems().get(getIndex());
                                fillForm( mDotation );
                                itemClicked = mDotation;
                                index = getIndex();
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
                tc_DMI.setCellValueFactory(new PropertyValueFactory<>("DMI"));
                tc_matriculeUtilisateur.setCellValueFactory(new PropertyValueFactory<>("matriculeDemandeur"));
                tc_serieNvMateriel.setCellValueFactory(new PropertyValueFactory<>("numSerieNvMateriel"));
                tc_serieMaterielRet.setCellValueFactory(new PropertyValueFactory<>("numSerieMaterielRet"));
                
                tv_Disharge.setItems( dotations );
                dotations.addAll(new Dao().getDotations());
                
                filter = new FilteredList(dotations, e->true);
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
    }
    
    public void fillForm(final Dotation mDotation){
        try{
            DMI.setText(mDotation.getDMI());
            date.setValue(mDotation.getDate());
            nature_affectation.setValue(mDotation.getNature());
            matricule_utilisateur.setText(mDotation.getMatriculeDemandeur());
            nserie_nvMateriel.setText(mDotation.getNumSerieNvMateriel());
            ns_materielRetourner.setText(mDotation.getNumSerieMaterielRet());
            netBios.setText(mDotation.getNetBios());
            numTicketSD.setText(mDotation.getNumTicketSD());
            cb_DRI.setSelected(mDotation.getState()[0]);
            cb_ficheExpertise.setSelected(mDotation.getState()[1]);
            cb_miseEnRebut.setSelected(mDotation.getState()[2]);
        }catch(Exception e){
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }
    
    private void gotoImportationProcess(final File src){
        try{
            String fxml = "/com/ocp/dotation/view/importer/DotationImportation.fxml";
            FXMLLoader loader = new FXMLLoader();
            InputStream in = DischargeController.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(DischargeController.class.getResource(fxml));
            Parent root;
            Stage stage = new Stage();
            try {
                root = (Parent) loader.load(in);
            } finally {
                in.close();
            } 
            Scene scene = new Scene( root );
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.sizeToScene();
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.sizeToScene();

            /**/
            //M1
            DotationImportationController dic = (DotationImportationController) loader.getController();
            dic.config(src);
            
            dic.setStage(stage);
            stage.initModality( Modality.WINDOW_MODAL );
            stage.initOwner( this.application.getPrimaryStage() );
            stage.show();
            dic.readData();
        }catch(Exception e){
            e.getStackTrace();
            APPException.showErrorNotifiction( "Error DotationImportationController");
        }
    }
    
    private void details(final Dotation mDotation){
        try{
            String fxml = "/com/ocp/dotation/view/discharge/DotationData.fxml";
            FXMLLoader loader = new FXMLLoader();
            InputStream in = DischargeController.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(DischargeController.class.getResource(fxml));
            Parent root;
            Stage stage = new Stage();
            try {
                root = (Parent) loader.load(in);
            } finally {
                in.close();
            } 
            Scene scene = new Scene( root );
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.sizeToScene();
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.sizeToScene();

            /**/
            //M1
            DotationDataController ddc = (DotationDataController) loader.getController();
            ddc.config(mDotation);
            stage.initModality( Modality.WINDOW_MODAL );
            stage.initOwner( this.application.getPrimaryStage() );
            stage.show();
        }catch(Exception e){
            e.getStackTrace();
            APPException.showErrorNotifiction( "Error DotationDataController");
        }
    }
    
}
