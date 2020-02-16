/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.controller.dao;

import com.ocp.dotation.model.Demandeur;
import com.ocp.dotation.model.Dotation;
import com.ocp.dotation.model.Materiel;
import com.ocp.dotation.util.APPException;
import com.ocp.dotation.util.AlertCell;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Jamal
 */
public class Dao {
    
    
   private String Driver = "com.mysql.cj.jdbc.Driver";
    private String databaseName = "ocpdotation";
    private String url    = "jdbc:mysql://localhost:3306/ocpdotation?useTimezone=true&serverTimezone=UTC";
    private String user   = "root";
    private String pwd    = "";
    private Connection conn;
    private Statement stm ;
    
    public Dao() throws SQLException, InstantiationException, IllegalAccessException {
                try {
                    System.out.println("Dao!!!");

                    Class.forName( Driver ).newInstance();
                    conn = DriverManager.getConnection(url, user, pwd);
                    stm = conn.createStatement();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
                }

    }
    
    public ArrayList<Materiel> getMateriels(){
        String query = " SELECT * FROM MATERIEL;";
        ArrayList<Materiel> materiels = new ArrayList<>();
        
        try(ResultSet rs= stm.executeQuery( query )){
            while( rs.next() ){
                materiels.add(new Materiel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        
        return materiels;
    }
    
    public void insertMateriel( final Materiel materiel ){
        String query = "INSERT INTO MATERIEL VALUES('"+materiel.getNumeroDeSerie()+"','"+materiel.getLibelle()+"','"+materiel.getModel()+"','"+materiel.getLieuGeographique()+"')";
        try{
            stm.executeUpdate( query );
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void updateMateriel( final Materiel materiel ){
        String query = "UPDATE MATERIEL SET "
                        + "numeroSerie='"+materiel.getNumeroDeSerie()+"',"
                        + "libelle='"+materiel.getLibelle()+"',"
                        + "modelle='"+materiel.getModel()+"',"
                        + "location='"+materiel.getLieuGeographique()+"'"
                        +" WHERE numeroSerie='"+materiel.getNumeroDeSerie()+"';";
        try{
            stm.executeUpdate( query );
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public Materiel getMateriel(final String numeroDeSerie){
        String query = " SELECT * FROM MATERIEL WHERE numeroSerie='"+numeroDeSerie+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            rs.next();
            return new Materiel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean isUniqueMateriel(final String numeroDeSerie){
        String query = " SELECT * FROM MATERIEL WHERE numeroSerie='"+numeroDeSerie+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }
    
    public ArrayList<Demandeur> getDemandeurs(){
        String query = " SELECT * FROM DEMANDEUR;";
        ArrayList<Demandeur> demandeurs = new ArrayList<>();
        
        try(ResultSet rs= stm.executeQuery( query )){
            while( rs.next() ){
                demandeurs.add(new Demandeur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        
        return demandeurs;
    }
    
    public void insertDemandeur( final Demandeur demandeur ){
        String query = "INSERT INTO DEMANDEUR VALUES('"+demandeur.getMatricule()+"','"+demandeur.getNom()+"','"+demandeur.getPrenom()+"','"+demandeur.getEntite()+"','"+demandeur.getSA()+"')";
        try{
            stm.executeUpdate( query );
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void updateDemandeur( final Demandeur demandeur ){
        String query = "UPDATE DEMANDEUR SET "
                        + "matricule='"+demandeur.getMatricule()+"',"
                        + "nom='"+demandeur.getNom()+"',"
                        + "prenom='"+demandeur.getPrenom()+"',"
                        + "entite='"+demandeur.getEntite()+"',"
                        + "sa='"+demandeur.getSA()+"'"
                        +" WHERE matricule='"+demandeur.getMatricule()+"';";
        try{
            stm.executeUpdate( query );
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public Demandeur getDemandeur(final String matricule){
        String query = " SELECT * FROM DEMANDEUR WHERE matricule='"+matricule+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            rs.next();
            return new Demandeur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean isUniqueDemandeur(final String matricule){
        String query = " SELECT * FROM DEMANDEUR WHERE matricule='"+matricule+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }
    
    public ArrayList<String> getSuggestionDataForUsers(){
        String query = " SELECT matricule FROM DEMANDEUR;";
        ArrayList<String> sugg = new ArrayList<>();
        
        try(ResultSet rs= stm.executeQuery( query )){
            while( rs.next() ){
                sugg.add( rs.getString(1) );
            }
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        return sugg;
    }
    
    public ArrayList<String> getSuggestionDataForMateriels(){
        String query = " SELECT numeroSerie FROM MATERIEL;";
        ArrayList<String> sugg = new ArrayList<>();
        
        try(ResultSet rs= stm.executeQuery( query )){
            while( rs.next() ){
                sugg.add( rs.getString(1) );
            }
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        return sugg;
    }
    
    public boolean validateUser(final String userName, final String password){
        String query = " SELECT * FROM ADMIN WHERE nomUtilisateur='"+userName+"' AND motDePasse='"+password+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }
    
    public ArrayList<Dotation> getDotations(){
        String query = " SELECT * FROM Dotation;";
        ArrayList<Dotation> dotations = new ArrayList<>();
        final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM dd yyyy");
        try(ResultSet rs= stm.executeQuery( query )){
            while( rs.next() ){
                String str = rs.getString(2);
                LocalDate date = LocalDate.of(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(5, 7)), Integer.parseInt(str.substring(8)));
                Dotation dotation = new Dotation(rs.getString(1), date, rs.getString(3),
                                           rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), 
                                           rs.getString(8), rs.getString(9));
                dotation.updateState(0, Boolean.valueOf(rs.getString(10))); 
                dotation.updateState(1, Boolean.valueOf(rs.getString(11))); 
                dotation.updateState(2, Boolean.valueOf(rs.getString(12))); 
                dotations.add(dotation);
                
            }
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        return dotations;
    }
    
    public void insertDotation( final Dotation dotation ){
        String query = "INSERT INTO DOTATION VALUES('"+dotation.getDMI()+"','"+dotation.getDate().toString()+"','"+dotation.getNature()+"','"+dotation.getMatriculeAdmin()+"','"+dotation.getMatriculeDemandeur()+"','"+dotation.getNumSerieNvMateriel()+"','"+dotation.getNumSerieMaterielRet()+"','"+dotation.getNetBios()+"','"+dotation.getNumTicketSD()+"','"+Boolean.toString(dotation.getState()[0])+"','"+Boolean.toString(dotation.getState()[1])+"','"+Boolean.toString(dotation.getState()[2])+"')";
        try{
            //verifier si le materiel (nv/ret) existe, 
            stm.executeUpdate( query );
        }catch(Exception e){
            e.printStackTrace();
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void updateDotation( final Dotation dotation ){
        String query = "UPDATE DOTATION SET "
                        + "DMI='"+dotation.getDMI()+"',"
                        + "dateDotation='"+dotation.getDate().toString()+"',"
                        + "nature='"+dotation.getNature()+"',"
                        + "matriculeAdmin='"+dotation.getMatriculeAdmin()+"',"
                        + "matriculeDemandeur='"+dotation.getMatriculeDemandeur()+"',"
                        + "numSerieNvMateriel='"+dotation.getNumSerieNvMateriel()+"',"
                        + "numSerieMaterielRet='"+dotation.getNumSerieMaterielRet()+"',"
                        + "netBios='"+dotation.getNetBios()+"',"
                        + "numeroTicketSD='"+dotation.getNumTicketSD()+"',"
                        + "DRI='"+Boolean.toString(dotation.getState()[0])+"',"
                        + "ficheExpertise='"+Boolean.toString(dotation.getState()[1])+"',"
                        + "miseEnRebut='"+Boolean.toString(dotation.getState()[2])+"'"
                        +" WHERE DMI='"+dotation.getDMI()+"';";
        try{
            stm.executeUpdate( query );
        }catch(Exception e){
            APPException.showErrorMessage(Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
        
        
    }
    
    public boolean isValidDotation(final Dotation dotation){
        if(!isUniqueDotation(dotation.getDMI()) && getDemandeur(dotation.getMatriculeDemandeur())!=null){
            switch (dotation.getNature()) {
                    
                    case "Nouvelle Dotation":
                        if(getMateriel(dotation.getNumSerieNvMateriel())!=null){
                            return true;
                        }
                        break;
                    case "Renouvellement": 
                        if(getMateriel(dotation.getNumSerieNvMateriel())!=null && getMateriel(dotation.getNumSerieMaterielRet())!=null){
                            return true;
                        }
                        break;
                    case "Retour Materiel":
                        if(getMateriel(dotation.getNumSerieMaterielRet())!=null){
                            return true;
                        }
                        break;
                }
        }
        return false;
    }
    
    
    public boolean isUniqueDotation(final String DMI){
        String query = " SELECT * FROM DOTATION WHERE DMI='"+DMI+"';";
        try(ResultSet rs= stm.executeQuery( query )){
            
            return rs.next();
        }catch(Exception e){
            System.out.println("Catch");
            return false;
        }
    }
    
    public ArrayList<AlertCell> getNotifications(){
        //AlertCell cell = new AlertCell
        ArrayList<AlertCell> notifs = new ArrayList<>();
        ArrayList<Dotation> dotations = getDotations();
        Iterator<Dotation> it = dotations.iterator();
        Dotation dota;
        while(it.hasNext()){
            dota = it.next();
            if( LocalDate.now().isAfter(dota.getDate())){
                AlertCell notif = new AlertCell("", dota.getDMI(), dota.getMatriculeDemandeur()+" ("+dota.getNature()+")", dota.getDate().toString());
                notifs.add(notif);
            }
        }
        return notifs;
    }
}
