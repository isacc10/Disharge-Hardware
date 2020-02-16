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
public abstract class Person {
    private String matricule;
    private String nom;
    private String prenom;
    private String entite;
    private String SA;
    
    public Person(String matricule, String nom, String prenom, String entite, String SA) {
        setMatricule(matricule);
        setNom(nom);
        setPrenom(prenom);
        setEntite(entite);
        setSA(SA);
    }
    
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEntite() {
        return entite;
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }

    public String getSA() {
        return SA;
    }

    public void setSA(String SA) {
        this.SA = SA;
    }

    @Override
    public String toString() {
        return "Person{" + "matricule=" + matricule + ", nom=" + nom + ", prenom=" + prenom + ", entite=" + entite + ", SA=" + SA + '}';
    }
    
    
}
