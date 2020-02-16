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
public class Admin extends Person{
    
    private String nomUtilisateur;
    private String motDePasse;
    
    public Admin(String matricule, String nom, String prenom, String entite, String SA, String nomUtilisateur, String motDePasse) {
        super(matricule, nom, prenom, entite, SA);
        setNomUtilisateur(nomUtilisateur);
        setMotDePasse(motDePasse);
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Admin{"+ super.toString() + "nomUtilisateur=" + nomUtilisateur + ", motDePasse=" + motDePasse + '}';
    }
    
}
