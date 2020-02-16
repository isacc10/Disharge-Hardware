/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.model;

/**
 *
 * @author Jamal
 */
public class Demandeur extends Person{

    public Demandeur(String matricule, String nom, String prenom, String entite, String SA) {
        super(matricule, nom, prenom, entite, SA);
    }

    @Override
    public String toString() {
        return "Demandeur{" +super.toString()+ '}';
    }
    
}
