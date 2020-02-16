/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.ocp.dotation.controller.dao.Dao;
import com.ocp.dotation.model.Dotation;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.model.Person;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class DotationDataController implements Initializable {
    @FXML
    private Label DMI;
    @FXML
    private Label date;
    @FXML
    private Label matriculeUtilisateur;
    @FXML
    private Label nomUtilisateur;
    @FXML
    private Label prenomUtilisateur;
    @FXML
    private Label entiteUtilisateur;
    @FXML
    private Label SAUtilisateur;
    @FXML
    private Label nvLibelle;
    @FXML
    private Label nvModele;
    @FXML
    private Label nvNumSerie;
    @FXML
    private Label nvDateAffectation;
    @FXML
    private Label nvLieuGeo;
    @FXML
    private Label retLibelle;
    @FXML
    private Label retModele;
    @FXML
    private Label retNumSerie;
    @FXML
    private Label retDateAffectation;
    @FXML
    private Label retNetBios;
    @FXML
    private Label retNumTicketSD;
    @FXML
    private Label nature;
    @FXML
    private FontAwesomeIconView DRI;
    @FXML
    private FontAwesomeIconView ficheExpertise;
    @FXML
    private FontAwesomeIconView miseEnRebut;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void config(final Dotation dotation){
        try {
            Dao dao = new Dao();
            DMI.setText("DMI-"+dotation.getDMI()+"/19");
            date.setText("Khouribga le "+dotation.getDate().getDayOfMonth()+"/"+dotation.getDate().getMonthValue()+"/"+dotation.getDate().getYear());
            nature.setText(dotation.getNature());
            Person demandeur = dao.getDemandeur(dotation.getMatriculeDemandeur());
            System.out.println(demandeur);
            matriculeUtilisateur.setText(demandeur.getMatricule());
            nomUtilisateur.setText(demandeur.getNom());
            prenomUtilisateur.setText(demandeur.getPrenom());
            entiteUtilisateur.setText(demandeur.getEntite());
            SAUtilisateur.setText(demandeur.getSA());
            
            Materiel nvMateriel ;
            Materiel materielRe ;
            
            if(dotation.getNature().equals("Nouvelle Dotation")){
                nvMateriel = dao.getMateriel(dotation.getNumSerieNvMateriel());
                fillNvDotation(dotation, nvMateriel);
                emptyMaterielRet(); 
            }
            
            if(dotation.getNature().equals("Renouvellement")){
                nvMateriel = dao.getMateriel(dotation.getNumSerieNvMateriel());
                materielRe = dao.getMateriel(dotation.getNumSerieMaterielRet());
                fillNvDotation(dotation, nvMateriel);
                fillMaterielRet(dotation, materielRe);
            }
            
            if(dotation.getNature().equals("Retour Materiel")){
                materielRe = dao.getMateriel(dotation.getNumSerieMaterielRet());
                fillMaterielRet(dotation, materielRe);
                emptyNvDotation();
            }
            
            if( dotation.getState()[0]==false ){
                DRI.setIcon(FontAwesomeIcon.WARNING);
                DRI.setStyle("-fx-fill:red;");
            }
            if( dotation.getState()[1]==false ){
                ficheExpertise.setIcon(FontAwesomeIcon.WARNING);
                ficheExpertise.setStyle("-fx-fill:red;");
            }
            if( dotation.getState()[2]==false ){
                miseEnRebut.setIcon(FontAwesomeIcon.WARNING);
                miseEnRebut.setStyle("-fx-fill:red;");
            }
        } catch (Exception ex) {
            Logger.getLogger(DotationDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void fillNvDotation(Dotation dotation, Materiel nvMateriel){
        nvLibelle.setText(nvMateriel.getLibelle());
        nvModele.setText(nvMateriel.getModel());
        nvNumSerie.setText(nvMateriel.getNumeroDeSerie());
        nvDateAffectation.setText(dotation.getDate().toString());
        nvLieuGeo.setText(nvMateriel.getLieuGeographique());
    }
    
    private void emptyNvDotation(){
        String empty = "-----";
        nvLibelle.setText(empty);
        nvModele.setText(empty);
        nvNumSerie.setText(empty);
        nvDateAffectation.setText(empty);
        nvLieuGeo.setText(empty);
    }
    
    private void fillMaterielRet(Dotation dotation, Materiel materielRet){
        retLibelle.setText(materielRet.getLibelle());
        retModele.setText(materielRet.getModel());
        retNumSerie.setText(materielRet.getNumeroDeSerie());
        retDateAffectation.setText(dotation.getDate().toString());
        retNetBios.setText(dotation.getNetBios());
        retNumTicketSD.setText(dotation.getNumTicketSD());
    }
    
    private void emptyMaterielRet(){
        String empty = "-----";
        retLibelle.setText(empty);
        retModele.setText(empty);
        retNumSerie.setText(empty);
        retDateAffectation.setText(empty);
        retNetBios.setText(empty);
        retNumTicketSD.setText(empty);
    }
}
