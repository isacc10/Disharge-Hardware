/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Dotation;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.util.APPException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class DotationImportationController implements Initializable {
    @FXML
    private TableView<Dotation> tv_Disharge;
    @FXML
    private TableColumn tc_DMI;
    @FXML
    private TableColumn tc_nature;
    @FXML
    private TableColumn tc_date;
    @FXML
    private TableColumn tc_matriculeUtilisateur;
    @FXML
    private TableColumn tc_serieNvMateriel;
    @FXML
    private TableColumn tc_serieMaterielRet;
    @FXML
    private TableColumn tc_netBios;
    @FXML
    private TableColumn tc_numTicketSD;
    @FXML
    private TableColumn tc_fichiers;
    @FXML
    private TableColumn tc_valider;
    @FXML
    private TableColumn tc_inserer;
    @FXML
    private FontAwesomeIconView DRI1;
    @FXML
    private Label succes;
    @FXML
    private FontAwesomeIconView DRI;
    @FXML
    private Label failure;
    @FXML
    private FontAwesomeIconView DRI11;
    @FXML
    private JFXButton annuler;
    @FXML
    private JFXButton Injecter;

    
    private File src ;
    private ObservableList<Dotation> dotations;
    private ArrayList<String> listOfValidDMI = new ArrayList<>();
    private int f = 0; //failure pourcent "compteur"
    private int s = 0; //succes pourcent  "compteur"
    private int t = -1; //total
    private Stage stage;
    private boolean insertionProcess = false;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configTableView();
        f=0; s=0;
    }    

    @FXML
    private void Annuler(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    private void Injecter(ActionEvent event) {
        Injecter.disableProperty().setValue(Boolean.TRUE);
        annuler.setText("Terminer");
        annuler.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHECK));
        Platform.runLater(() -> {
            try{
                insertionProcess = true;
                Iterator<Dotation> it = dotations.iterator();
                Dotation dota;
                Dao dao = new Dao();
                while( it.hasNext() ){
                    dota = it.next();
                    if( listOfValidDMI.contains(dota.getDMI()) ){
                        System.out.println("Valid pour l'inserer : "+dota);
                        dao.insertDotation( dota );
                    }
                }
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
        });
        Platform.runLater(() -> {
            try{
                ArrayList<Dotation> list = new ArrayList<>(dotations);
                dotations.clear();
                dotations.addAll(list);
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
        });
        
    }
    
    public void setStage(final Stage stage){
        this.stage = stage;
    }
    
    public void config( final File src ){
        this.src = src;
    }
    
    public void readData(){
        try {
            FileInputStream fis = new FileInputStream(src);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row ;
            Dotation dotation;
            ArrayList<Dotation> list = new ArrayList<>(); 
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);
                Date d = row.getCell(1).getDateCellValue();
                LocalDate date = Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                dotation = new Dotation(row.getCell(0).getStringCellValue(),
                                        date,
                                        row.getCell(2).getStringCellValue(),
                                        row.getCell(3).getStringCellValue(),
                                        row.getCell(4).getStringCellValue(),
                                        row.getCell(5).getStringCellValue(),
                                        row.getCell(6).getStringCellValue(),
                                        row.getCell(7).getStringCellValue(),
                                        row.getCell(8).getStringCellValue());
                dotation.updateState(0, Boolean.valueOf(row.getCell(9).getStringCellValue())); 
                dotation.updateState(1, Boolean.valueOf(row.getCell(10).getStringCellValue())); 
                dotation.updateState(2, Boolean.valueOf(row.getCell(11).getStringCellValue()));
                System.out.println(""+dotation);
                list.add(dotation);
            }
            dotations.addAll(list);
            System.out.println("Taille: "+tv_Disharge.getItems().size());
        } catch (FileNotFoundException ex) {
            APPException.showErrorMessage( Arrays.toString(ex.getStackTrace()), Alert.AlertType.NONE);
        } catch (IOException ex) {
            APPException.showErrorMessage( Arrays.toString(ex.getStackTrace()), Alert.AlertType.NONE);
        }
            
    }
    
    private void configTableView(){
        dotations = FXCollections.observableArrayList();
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryValider  =  (TableColumn<Dotation, String> param)->{
                
                final TableCell<Dotation, String> cell = new TableCell<Dotation,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if( empty ){
                            setGraphic(null);
                            setText(null);
                        }else{
                            
                            try {
                                final FontAwesomeIconView valid = new FontAwesomeIconView(FontAwesomeIcon.UPLOAD);
                                valid.setStyle("-fx-fill:#0b7dda;");  valid.setSize("16");
                                
                                final FontAwesomeIconView invalid = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                                invalid.setStyle("-fx-fill:red;");  invalid.setSize("16");
                                Dotation mDotation = getTableView().getItems().get(getIndex());
                                
                                if(t>-1 && insertionProcess==false){
                                    Dao dao = new Dao();
                                    if( !listOfValidDMI.contains(mDotation.getDMI()) && dao.isValidDotation(mDotation)){
                                        s++;
                                        listOfValidDMI.add(mDotation.getDMI());
                                        setGraphic(valid);
                                    }else {
                                        f++;
                                        setGraphic(invalid);
                                    }
                                    System.out.println("s:"+s+" f:"+f);
                                    failure.setText((float)(f*100)/dotations.size()+"%");
                                    succes.setText((float)(s*100)/dotations.size()+"%");
                                    setText(null);
                                }
                                if(insertionProcess==true){
                                    if( listOfValidDMI.contains(mDotation.getDMI())){
                                        setGraphic(valid);
                                    }else {
                                        setGraphic(invalid);
                                    }
                                    setText(null);
                                }
                                t++;
                            } catch (Exception ex) {
                                Logger.getLogger(DotationImportationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                };
                
                //return the cellule created
                return cell;
            };
            tc_valider.setCellFactory( cellFactoryValider );
            
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryInserer  =  (TableColumn<Dotation, String> param)->{
                
                final TableCell<Dotation, String> cell = new TableCell<Dotation,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        super.updateItem(item, empty);
                        if( empty ){
                            setGraphic(null);
                            setText(null);
                        }else{
                            
                            try {
                                if( insertionProcess==true ){
                                    //Dao dao = new Dao();
                                    Dotation mDotation = getTableView().getItems().get(getIndex());
                                    if(listOfValidDMI.contains(mDotation.getDMI())){
                                        final FontAwesomeIconView valid = new FontAwesomeIconView(FontAwesomeIcon.SPINNER);
                                        valid.setStyle("-fx-fill:#0b7dda;");  valid.setSize("16");
                                        rotate(valid);
                                        Label progress = new Label("0%", valid);
                                        progress.setMaxHeight(20);
                                        progress.setContentDisplay(ContentDisplay.RIGHT);
                                        setGraphic(progress);
                                        //dao.insertDotation( mDotation );
                                        new Thread(){
                                            @SuppressWarnings("SleepWhileInLoop")
                                            public void run(){

                                                for(int i=0; i<=100; i++){
                                                    try{
                                                        Thread.sleep( 50 );
                                                    }catch(Exception e){
                                                        APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
                                                    }
                                                    int counter = i;
                                                    Platform.runLater(() -> {
                                                        progress.setText(counter+"%");
                                                        if(counter==100)
                                                            progress.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHECK));
                                                    }); 
                                                }    
                                                
                                            }
                                        }.start();
                                        
                                    }else{
                                        final FontAwesomeIconView invalid = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                                        invalid.setStyle("-fx-fill:red;");  invalid.setSize("16");
                                        setGraphic(invalid);
                                    }
                                    
                                    //setGraphic(null);    
                                    setText(null);
                                    
                                }
                                
                            } catch (Exception ex) {
                                Logger.getLogger(DotationImportationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                };
                
                //return the cellule created
                return cell;
            };
            tc_inserer.setCellFactory( cellFactoryInserer );
            
            Callback<TableColumn<Dotation, String>, TableCell<Dotation, String>> cellFactoryFichiers  =  (TableColumn<Dotation, String> param)->{
                
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
            tc_fichiers.setCellFactory( cellFactoryFichiers );
            
            try{
                tc_DMI.setCellValueFactory(new PropertyValueFactory<>("DMI"));
                tc_date.setCellValueFactory(new PropertyValueFactory<>("date"));
                tc_nature.setCellValueFactory(new PropertyValueFactory<>("nature"));
                tc_matriculeUtilisateur.setCellValueFactory(new PropertyValueFactory<>("matriculeDemandeur"));
                tc_serieNvMateriel.setCellValueFactory(new PropertyValueFactory<>("numSerieNvMateriel"));
                tc_serieMaterielRet.setCellValueFactory(new PropertyValueFactory<>("numSerieMaterielRet"));
                tc_netBios.setCellValueFactory(new PropertyValueFactory<>("netBios"));
                tc_numTicketSD.setCellValueFactory(new PropertyValueFactory<>("numTicketSD"));
                
                tv_Disharge.setItems( dotations );
            }catch(Exception e){
                APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
            }
    }
    
    public void rotate(final Node node){
        //Creating a rotate transition    
        RotateTransition rotateTransition = new RotateTransition(); 
      
        //Setting the duration for the transition 
        rotateTransition.setDuration( Duration.millis(2000) ); 
      
        //Setting the node for the transition 
        rotateTransition.setNode(node);       
      
        //Setting the angle of the rotation 
        rotateTransition.setByAngle(360); 
      
        //Setting the cycle count for the transition 
        rotateTransition.setCycleCount( 100000 ); 
      
        //Setting auto reverse value to false 
        rotateTransition.setAutoReverse(false); 
      
        //Playing the animation 
        rotateTransition.play();
    }
    
}
