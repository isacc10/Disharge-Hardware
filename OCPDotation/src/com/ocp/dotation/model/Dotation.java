/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.model;

import java.time.LocalDate;
import java.util.Arrays;

/**
 *
 * @author Jamal SAHRI
 */
public class Dotation {
    private String DMI;
    private LocalDate date;
    private String nature;
    private String matriculeAdmin;
    private String matriculeDemandeur;
    private String numSerieNvMateriel = "------";
    private String numSerieMaterielRet = "------";
    private String netBios = "------";
    private String numTicketSD = "------";
    private boolean state[] = new boolean[3];
    
    //Constructeur renouvellement
    public Dotation(String DMI, LocalDate date, String nature, String matriculeAdmin, String matriculeDemandeur, String numSerieNvMateriel, String numSerieMaterielRet, String netBios, String numTicketSD) {
        this.DMI = DMI;
        this.date = date;
        this.nature = nature;
        this.matriculeAdmin = matriculeAdmin;
        this.matriculeDemandeur = matriculeDemandeur;
        this.numSerieNvMateriel = numSerieNvMateriel;
        this.numSerieMaterielRet = numSerieMaterielRet;
        this.netBios = netBios;
        this.numTicketSD = numTicketSD;
    }
    
    //Constructeur Nouvelle dotation
    public Dotation(String DMI, LocalDate date, String nature, String matriculeAdmin, String matriculeDemandeur, String numSerieNvMateriel) {
        this.DMI = DMI;
        this.date = date;
        this.nature = nature;
        this.matriculeAdmin = matriculeAdmin;
        this.matriculeDemandeur = matriculeDemandeur;
        this.numSerieNvMateriel = numSerieNvMateriel;
    }
    
    //Constructeur Retour Materiel
    public Dotation(String DMI, LocalDate date, String nature, String matriculeAdmin, String matriculeDemandeur, String numSerieMaterielRet, String netBios, String numTicketSD) {
        this.DMI = DMI;
        this.date = date;
        this.nature = nature;
        this.matriculeAdmin = matriculeAdmin;
        this.matriculeDemandeur = matriculeDemandeur;
        this.numSerieMaterielRet = numSerieMaterielRet;
        this.netBios = netBios;
        this.numTicketSD = numTicketSD;
    }

    public String getDMI() {
        return DMI;
    }

    public void setDMI(String DMI) {
        this.DMI = DMI;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getMatriculeAdmin() {
        return matriculeAdmin;
    }

    public void setMatriculeAdmin(String matriculeAdmin) {
        this.matriculeAdmin = matriculeAdmin;
    }

    public String getMatriculeDemandeur() {
        return matriculeDemandeur;
    }

    public void setMatriculeDemandeur(String matriculeDemandeur) {
        this.matriculeDemandeur = matriculeDemandeur;
    }

    public String getNumSerieNvMateriel() {
        return numSerieNvMateriel;
    }

    public void setNumSerieNvMateriel(String numSerieNvMateriel) {
        this.numSerieNvMateriel = numSerieNvMateriel;
    }

    public String getNumSerieMaterielRet() {
        return numSerieMaterielRet;
    }

    public void setNumSerieMaterielRet(String numSerieMaterielRet) {
        this.numSerieMaterielRet = numSerieMaterielRet;
    }

    public boolean[] getState() {
        return state;
    }

    public void setState(boolean[] state) {
        this.state = state;
    }
    
    public void updateState( final int index, final boolean newValue){
        this.state[index] = newValue;
    }
    
    public String getNetBios() {
        return netBios;
    }

    public void setNetBios(String netBios) {
        this.netBios = netBios;
    }

    public String getNumTicketSD() {
        return numTicketSD;
    }

    public void setNumTicketSD(String numTicketSD) {
        this.numTicketSD = numTicketSD;
    }

    @Override
    public String toString() {
        return "Dotation{" + "DMI=" + DMI + ", date=" + date + ", nature=" + nature + ", matriculeAdmin=" + matriculeAdmin + ", matriculeDemandeur=" + matriculeDemandeur + ", numSerieNvMateriel=" + numSerieNvMateriel + ", numSerieMaterielRet=" + numSerieMaterielRet + ", netBios=" + netBios + ", numTicketSD=" + numTicketSD + ", state=" + Arrays.toString(state) + '}';
    }

    
}
