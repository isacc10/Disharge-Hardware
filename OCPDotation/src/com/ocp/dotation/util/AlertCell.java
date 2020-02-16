/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.util;

import com.gn.AvatarType;
import com.gn.GNAvatarView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;



public class AlertCell extends HBox {

    private FontAwesomeIconView fontIcon;
    private ImageView           imageView;
    private SVGPath             path;
    private Label               label;
    
    private VBox content = new VBox();

    private TextFlow textFlow = new TextFlow();
    private Text text = new Text();
    private Label title = new Label("tile");
    private Label quantite  = new Label("n minutes ago");

    
    public AlertCell(String avatarPath, String title, String text, String quantite) {
        GNAvatarView avatar = new GNAvatarView();
        try{
            avatar.setImage(new Image( getClass().getResource( "/com/ocp/dotation/view/theme/ocplogo.png" ).toExternalForm() ) );
        }catch(Exception ee){
            avatar.setImage(new Image( getClass().getResource( "/com/ocp/dotation/view/theme/ocplogo.png" ).toExternalForm() ) );
        }
        setIcon(avatar);
        this.title.setText(title);
        this.quantite.setText(quantite);
        this.text.setText(text);
        config();
    }
    
    public AlertCell(char c, String title, String text, String time) {
        setLabel(c);
        this.title.setText(title);
        this.quantite.setText(time);
        this.text.setText(text);
        config();
    }
    
    
    private void config(){
        this.getStyleClass().add("alert-cell");
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefHeight(50D);
        
        this.title.setStyle("-fx-font-size : 14;");
        this.title.setStyle("-fx-font-weight : bolder");
        
        //this.text.getStyleClass().addAll("h6");
        this.text.setStyle("-fx-fill : black;");
        this.quantite.setStyle("-fx-text-fill : #b2babf ; -fx-font-style : italic; ");
        textFlow.getChildren().addAll(text);
        this.content.getChildren().addAll(this.title, textFlow, this.quantite);
        this.content.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(content);
        this.setAlignment(Pos.CENTER);
        HBox.setHgrow(content, Priority.ALWAYS);
        GridPane.setHalignment(this.quantite, HPos.RIGHT);
        GridPane.setValignment(this.quantite, VPos.CENTER);
        GridPane.setHgrow(this.quantite, Priority.ALWAYS);
        HBox.setMargin(this.content, new Insets(0,0,0,10));
    }

    public void setTitle(Label title) {
        title.setStyle("-fx-text-fill : -text-color;");
        this.title = title;
    }


    public void setQuantite(Label quantite) {
        quantite.setStyle("-fx-text-fill : -text-color;");
        this.quantite = quantite;
    }

    public void setIcon(FontAwesomeIconView icon){
        //icon.setStyle("-fx-fill : -text-color;");
        this.getChildren().add(icon);
        icon.toBack();
    }
    
    public void setLabel(char icon){
        Label l = new Label(icon+"");
        l.setTextAlignment(TextAlignment.CENTER);
        l.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-text-fill: #ff0000; -fx-background-color:white; -fx-font-size:30; -fx-border-color: #99ccff; "
                   + "-fx-pref-width: 45; -fx-pref-height:45; -fx-alignment:center; -fx-border-width:2; -fx-effect:dropshadow(three-pass-box, purple, 10, 0, 0, 0); -fx-font-family:'Baskerville Old Face';");
        this.getChildren().add(l);
    }
    
    public void setIcon(ImageView icon){
        this.getChildren().add(icon);
        icon.toBack();
    }

    public void setIcon(GNAvatarView icon){
        icon.setType(AvatarType.CIRCLE);
        icon.setStrokeWidth(0);
        icon.setStroke(Color.WHITE);
        icon.setPrefWidth(35);
        this.getChildren().add(icon);
        icon.toBack();
    }

    public void setIcon(SVGPath icon){
        this.getChildren().add(icon);
        icon.toBack();
    }

    public FontAwesomeIconView getIcon() {
        return fontIcon;
    }

    public Label getTitle() {
        return title;
    }
}
