/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.util.APPException;
import com.ocp.dotation.util.AlertCell;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import ocpdotation.OCPDotation;
import org.controlsfx.control.PopOver;


    
/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class MasterController implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private JFXButton btn_user;
    @FXML
    private JFXButton btn_materiel;
    @FXML
    private JFXButton btn_dmi;
    @FXML
    private Label dateTime;
    @FXML
    private JFXButton logout;
    @FXML
    private Label titleApp;
    @FXML
    private FontAwesomeIconView icon;
    @FXML
    private HBox options_zone;
    @FXML
    private JFXButton btnNotifs;
    @FXML
    private FontAwesomeIconView notifs_icon;
    @FXML
    private StackPane body;
    @FXML
    private JFXButton btn_accueil;

    private OCPDotation application;
    @FXML
    private JFXButton btnShutDown;
    @FXML
    private FontAwesomeIconView shutDown_icon;
    
    public void setApp(OCPDotation application){
        this.application = application;
    }
    
    final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, (ActionEvent event) -> {
        LocalDateTime dt = LocalDateTime.now();
        dateTime.setText(dt.getDayOfWeek()+", "+dt.getMonth()+ " "+dt.getDayOfMonth()+", "+dt.getYear()+" - "+
                dt.getHour()+":"+dt.getMinute()+":"+dt.getSecond());
    }),
            new KeyFrame(Duration.seconds(1))
    );
    
    final Timeline notifThread = new Timeline(new KeyFrame(Duration.seconds(15), (ActionEvent event) -> {
            try{
                int size = listOfNotifications.size();
                listOfNotifications.setAll(new Dao().getNotifications());
                //listOfNotifications.addAll(new Dao().getNotifications());
                if(size!=listOfNotifications.size()){
                    notificationSound();
                    notifs_icon.setStyle("-fx-fill: red;");
                }
            } catch(Exception e){
               APPException.showErrorNotifiction("Error Fatale 'Notification Th'");
            }
        }),
            new KeyFrame(Duration.seconds(30))
    );
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            notifs_icon.setStyle("-fx-fill: red;");
            listOfNotifications.addAll(new Dao().getNotifications());
            
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            
            notifThread.setCycleCount(Animation.INDEFINITE);
            notifThread.play();
            try {
                accueil(null);
            } catch (IOException ex) {
                Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    
    
    public void test(){
        System.out.println("Method invoked by gotoMaster();");
    }
    
    @FXML
    private void accueil(ActionEvent event) throws IOException {
        if(body.getChildren().size()>0) body.getChildren().remove(0);
        body.getChildren().add( FXMLLoader.load(getClass().getResource("/com/ocp/dotation/view/accueil/Accueil.fxml")));
        titleApp.setText("Accueil");
    }
    
    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        if(body.getChildren().size()>0) body.getChildren().remove(0);
        body.getChildren().add( FXMLLoader.load(getClass().getResource("/com/ocp/dotation/view/users/Utilisateur.fxml")));
        titleApp.setText("Utilisateur");
        /*
        Node node = ViewManager.getInstance().get("Sale");
        currentScene = "Sale";
        SaleController.controller.prepare();
        //body.setContent( node );
        try{
            body.getChildren().remove(0);
            body.getChildren().add(node);
        }catch(Exception e){
            body.getChildren().add(node);
        }
        SaleController.controller.setApp( application );
        SaleController.controller.setRoot( root );
        titleApp.setText( sale.getText() );
        icon.setIcon(FontAwesomeIcon.MONEY);
                */
    }

    @FXML
    private void materiel(ActionEvent event) throws IOException {
        if(body.getChildren().size()>0) body.getChildren().remove(0);
        body.getChildren().add( FXMLLoader.load(getClass().getResource("/com/ocp/dotation/view/materiel/Materiel.fxml")));
        titleApp.setText("Materiel");
    }

    @FXML
    private void DMI(ActionEvent event) throws IOException {
        if(body.getChildren().size()>0) body.getChildren().remove(0);
        FXMLLoader loader = new FXMLLoader();
        InputStream in = OCPDotation.class.getResourceAsStream("/com/ocp/dotation/view/discharge/Discharge.fxml");
        body.getChildren().add(loader.load(in));
        DischargeController ctrl  = (DischargeController) loader.getController();
        ctrl.setApp(application);
        titleApp.setText("Décharge");
        /*
        body.getChildren().add( FXMLLoader.load(getClass().getResource("/com/ocp/dotation/view/discharge/Discharge.fxml")));
        titleApp.setText("Décharge");
                */
    }

    @FXML
    private void logout(ActionEvent event) {
        this.application.AdminLogout();
    }
    
    private static ObservableList<AlertCell> listOfNotifications = FXCollections.observableArrayList();
    PopOver pop = new PopOver();
    @FXML
    private void notifs(ActionEvent event) {
        notifs_icon.setStyle("-fx-fill: #6666d6;");
        
        rotate(notifs_icon);
        
           if(!pop.isShowing()){
            
            Separator top = new Separator();
            Separator bottom = new Separator();

            Label message = new Label("Notifications");
            Label count = new Label(listOfNotifications.size()+" Nouvelles"); //dao.getSumOfNotifs();
            count.setStyle("-fx-text-fill : #ff0000 ;");
            //count.getStyleClass().add("text-success");
            GridPane title = new GridPane();
            title.setMinHeight(40D);

            title.setAlignment(Pos.CENTER);
            title.add(message, 0, 0);
            title.add(count, 1,0);
            GridPane.setHalignment(count, HPos.RIGHT);

            ListView<AlertCell> listView = new ListView<>();
            
            listView.getItems().addAll(listOfNotifications);
            listView.setPadding(new Insets(12, 0, 12, 0));
            
            JFXButton btn = new JFXButton("Voir Tout"); //interntionalization !!!!!!!!!!!
            btn.setStyle("-fx-text-fill : #0b95ea ; -fx-font-size : 13");
            
            VBox vbox = new VBox(title, top, listView, bottom, btn);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefSize(300, 300);
            title.setPrefWidth(vbox.getPrefWidth());
            count.setPrefWidth(vbox.getPrefWidth());
            message.setPrefWidth(vbox.getPrefWidth());
            count.setAlignment(Pos.CENTER_RIGHT);
            title.setPadding(new Insets(0, 25, 0, 25));
            btn.setPrefWidth(vbox.getPrefWidth());


            JFXScrollPane sp = new JFXScrollPane();
            sp.getChildren().add(vbox);
            pop.setContentNode(vbox);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
            pop.setArrowIndent(0);
            pop.setArrowSize(0);
            pop.setCloseButtonEnabled(false);
            pop.setHeaderAlwaysVisible(false);
            pop.setCornerRadius(0);
            pop.setAutoFix(true);
            pop.show(options_zone);
            notifs_icon.setStyle("-fx-fill:#0072ff;"); //#5200eb
        } else {
            pop.hide();
        }
    }

    @FXML
    private void shutDown(ActionEvent event) {
        this.application.stop();
    }
    
    public void notificationSound(){
        try{
            final AudioClip Note = new AudioClip(MasterController.class.getResource("/com/ocp/dotation/util/notif.mp3").toString());      
            Note.play();
        }catch(Exception e){
            
        }
    }
    
    public void rotate(Node node){
        //Creating a rotate transition    
        RotateTransition rotateTransition = new RotateTransition(); 
      
        //Setting the duration for the transition 
        rotateTransition.setDuration(Duration.millis(800)); 
      
        //Setting the node for the transition 
        rotateTransition.setNode(node);       
      
        //Setting the angle of the rotation 
        //rotateTransition.axisProperty().set(Point3D.ZERO);
        //rotateTransition.setByAngle(45); 
        rotateTransition.setFromAngle(30);
        rotateTransition.setToAngle(-30);
        rotateTransition.setOnFinished((ActionEvent e)->{
            node.setRotate(0);
        });
        //Setting the cycle count for the transition 
        rotateTransition.setCycleCount( 1 ); 
      
        //Setting auto reverse value to false 
        rotateTransition.setAutoReverse(true); 
      
        //Playing the animation 
        rotateTransition.play();
    }
}
