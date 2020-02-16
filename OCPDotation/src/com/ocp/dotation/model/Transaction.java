/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.model;

import java.time.LocalDate;

/**
 *
 * @author Jamal
 */
public abstract class Transaction {
    private String matricule;
    private String numeroSerie;
    private LocalDate dateTransaction;

    public Transaction(String matricule, String numeroSerie, LocalDate dateTransaction) {
        setMatricule(matricule);
        setNumeroSerie(numeroSerie);
        setDateTransaction(dateTransaction);
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    @Override
    public String toString() {
        return "Transaction{" + "matricule=" + matricule + ", numeroSerie=" + numeroSerie + ", dateTransaction=" + dateTransaction + '}';
    }
    
    
}
