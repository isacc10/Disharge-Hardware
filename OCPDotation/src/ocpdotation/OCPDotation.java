/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocpdotation;

import com.ocp.dotation.controller.LoginController;
import com.ocp.dotation.controller.MasterController;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Admin;
import com.ocp.dotation.util.APPException;
import com.sun.javafx.application.LauncherImpl;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jamal
 */
public class OCPDotation extends Application {
    
    private Stage stage;
    private Admin admin ;
    private boolean flag = false;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            if(flag == true){
                //APPException.showErrorMessage("ERROR can't connect to database server.", Alert.AlertType.WARNING);
                APPException.showErrorNotifiction("ERROR can't connect to database server.");
                return;
            }
                
            stage = primaryStage;
            stage.setTitle("Application de décharge de materiel informatique");
            stage.getIcons().add(new Image("/com/ocp/dotation/view/theme/ocplogo.png"));
            //stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            //stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            //Att
            gotoLogin();
            
            
            //gotoMaster();
            
            primaryStage.centerOnScreen();
            primaryStage.show();
            //primaryStage.resizableProperty().setValue(Boolean.FALSE);
        } catch (Exception ex) {
            Logger.getLogger(OCPDotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stop(){
        //Traitement 
        //GC
        this.stage.close();
    }
    
    public Stage getPrimaryStage(){
        return this.stage;
    }
    
    public Admin getLoggedAdmin() {
        return admin;
    }
    
    public void setLoggedAdmin(Admin admin){
        this.admin = admin ;
    }
    

    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("/com/ocp/dotation/view/login/Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(OCPDotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AdminLogout(){
        admin = null;
        gotoLogin();
    }
    
    public void gotoMaster() {
        try {
            MasterController master = (MasterController) replaceSceneContent("/com/ocp/dotation/view/master/Master.fxml");
            master.setApp(this);
            master.test();
        } catch (Exception ex) {
            Logger.getLogger(OCPDotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public synchronized void init(){
        float total = 1;
        increment = 100f / total;
        load(null,null);
    }
    
    private float  increment = 0;
    private float  progress  = 0;
    
    private void load( String path, String name ){
        try {
            try{
                Dao dao = new Dao();
                preloaderNotify();
            }catch(Exception e){
                System.out.println("ERROR can't connect to database server.");
                flag = true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    private synchronized void preloaderNotify() {
        progress += increment;
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(OCPDotation.class, (java.lang.String[])null);        
    }
    
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        InputStream in = OCPDotation.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(OCPDotation.class.getResource(fxml));
        StackPane page;
        try {
            page = (StackPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.centerOnScreen();
       // stage.resizableProperty().setValue(Boolean.FALSE);
       // stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
