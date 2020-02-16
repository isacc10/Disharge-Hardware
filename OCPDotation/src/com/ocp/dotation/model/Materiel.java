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
public class Materiel {
    private String numeroDeSerie;
    private String libelle;
    private String model;
    private String lieuGeographique;

    public Materiel(String numeroDeSerie, String libelle, String model, String lieuGeographique) {
        this.numeroDeSerie = numeroDeSerie;
        this.libelle = libelle;
        this.model = model;
        this.lieuGeographique = lieuGeographique;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLieuGeographique() {
        return lieuGeographique;
    }

    public void setLieuGeographique(String lieuGeographique) {
        this.lieuGeographique = lieuGeographique;
    }

    @Override
    public String toString() {
        return "Materiel{" + "numeroDeSerie=" + numeroDeSerie + ", libelle=" + libelle + ", model=" + model + ", lieuGeographique=" + lieuGeographique + '}';
    }
}
