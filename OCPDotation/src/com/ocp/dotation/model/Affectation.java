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
public class Affectation extends Transaction{

    public Affectation(String matricule, String numeroSerie, LocalDate dateTransaction) {
        super(matricule, numeroSerie, dateTransaction);
    }

    @Override
    public String toString() {
        return "Affectation{" + super.toString() +'}';
    }
    
}
