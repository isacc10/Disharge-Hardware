/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.util.APPException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class MaterielController implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private JFXTextField tf_numeroSerie;
    @FXML
    private JFXTextField tf_libelle;
    @FXML
    private JFXTextField tf_model;
    @FXML
    private JFXTextField tf_lieuGeo;
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
    private TableView<Materiel> tv_materials;
    @FXML
    private TableColumn<Materiel, String> tc_numeroDeSerie;
    @FXML
    private TableColumn<Materiel, String> tc_libelle;
    @FXML
    private TableColumn<Materiel, String> tc_model;
    @FXML
    private TableColumn<Materiel, String> tc_lieuGeographique;
    @FXML
    private TableColumn tc_modifier;
    @FXML
    private Label errorMessage;

    
    
    
    /***********************************************************************/
        private Materiel materiel ;
        private ObservableList<Materiel> materiels;
        private FilteredList filter ;
        private Materiel itemClicked;
        private int index;
    /***********************************************************************/
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            // TODO
            materiels = FXCollections.observableArrayList();
            Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellFactoryEdit  =  (TableColumn<Materiel, String> param)->{
                
                final TableCell<Materiel, String> cell = new TableCell<Materiel,String>(){
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
                                Materiel materiel = getTableView().getItems().get(getIndex());
                                fillForm(materiel);
                                itemClicked = materiel;
                                index = getIndex();
                                System.out.println("Clicked: "+materiel);
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
                tc_numeroDeSerie.setCellValueFactory(new PropertyValueFactory<>("numeroDeSerie"));
                tc_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
                tc_model.setCellValueFactory(new PropertyValueFactory<>("model"));
                tc_lieuGeographique.setCellValueFactory(new PropertyValueFactory<>("lieuGeographique"));
                
                tv_materials.setItems( materiels );
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
            Dao dao = new Dao();
            materiels.setAll(dao.getMateriels());
            
            filter = new FilteredList(materiels, e->true);
            
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        
    } 
    
    
    
    public void init(){
        
    }
    
    
    @FXML
    private void Sauvegarder(ActionEvent event) {
        if(checkForm()){
            try {
                materiel =new Materiel( tf_numeroSerie.getText().trim(),
                        tf_libelle.getText().trim(),
                        tf_model.getText().trim(),
                        tf_lieuGeo.getText().trim()
                );
                System.out.println("Out:: "+materiel);
                Dao dao = new Dao();
                if(itemClicked!=null && materiel.getNumeroDeSerie().equals(itemClicked.getNumeroDeSerie())){
                    //Update process
                    //materiels.remove(index);
                    materiels.set(index, materiel);
                    dao.updateMateriel(materiel);
                }else{
                    //Insert process
                    if(dao.isUniqueMateriel(tf_numeroSerie.getText().trim())){
                        errorMessage.setText("Article existe déja!");
                        return;
                    }
                    materiels.add(materiel);
                    dao.insertMateriel(materiel);
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
            tf_numeroSerie.setText("");
            tf_libelle.setText("");
            tf_lieuGeo.setText("");
            tf_model.setText("");
            itemClicked = null;
        }catch(Exception e){
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }

    
    /**************************************************************************************************************************/
    
    @FXML
    private void Importer(ActionEvent event) {
        //checkConnectionToDB();
        Stage stage = new Stage();
        File file ;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(stage);
        if ( file != null ) {
            
        
        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row ;
            Materiel mat;
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);
                //System.out.println("Row number("+i+") = {"+row.getCell(0).getStringCellValue()+","+row.getCell(1).getStringCellValue()+","+row.getCell(2).getStringCellValue()+"}");
                mat = new Materiel(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue());
                materiels.add(mat);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MaterielController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MaterielController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
        
    }

    
    /**************************************************************************************************************************/    
    private String Driver = "com.mysql.cj.jdbc.Driver";
    @FXML
    private void checkConnectionToDB(){
        try {
            Dao dao = new Dao();
            showErrorNotifiction("Driver ["+Driver+"] is connected with succes.");
        }catch(Exception e) {
            showErrorNotifiction("Driver ["+Driver+"] Not found.");
        }
    }
    public void showErrorNotifiction(final String message){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog( root, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialogLayout.setHeading(new Label( message ));
        HBox pan = new HBox();
        JFXButton annuler   = new JFXButton("Annuler");
        annuler.setStyle("-fx-background-color: white; -fx-text-fill:#4d4dff;");
            annuler.setOnAction((ActionEvent ev)->{
                dialog.close();
            });
        pan.spacingProperty().set( 10 );
        pan.getChildren().addAll( annuler );
        dialogLayout.setActions(pan);
        dialog.show();                    
    }
    
    @FXML
    private void Search(KeyEvent event) {
        tf_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)->{
            String test ;
            filter.setPredicate((Predicate<? super Materiel>)(Materiel mat)->{
                
                if(newValue.isEmpty() || newValue==null){                    
                    return true;
                }
                else if( mat.getNumeroDeSerie().contains(newValue) ){
                    return true;
                }else if( mat.getLibelle().contains(newValue) ){
                    return true;
                }else if( mat.getModel().contains(newValue) ){
                    return true;
                }else if( mat.getLieuGeographique().contains(newValue) ){
                    return true;
                }
                return false;
            });           
        });
        
        SortedList<Materiel> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(tv_materials.comparatorProperty());
        tv_materials.setItems( sort );
    }

    @FXML
    private void ButtonSearch(ActionEvent event) {
    }
    
    
    //*************************************************************************//
    final String error = "Erreur de saisie";
    public boolean checkForm(){
        
        animateMessage();
        try{
            if( !tf_numeroSerie.getText().isEmpty() ){//&& dao.isUniqueNumero
                if( !tf_libelle.getText().isEmpty() && !tf_lieuGeo.getText().isEmpty() && !tf_model.getText().isEmpty() ){
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
    
    public void fillForm( final Materiel materiel){
        try{
            tf_numeroSerie.setText(materiel.getNumeroDeSerie());
            tf_libelle.setText(materiel.getLibelle());
            tf_model.setText(materiel.getModel());
            tf_lieuGeo.setText(materiel.getLieuGeographique());
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
