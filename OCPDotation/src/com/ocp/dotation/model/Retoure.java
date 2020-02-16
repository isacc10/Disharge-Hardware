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
public class Retoure extends Transaction{
    
    private String netBios;
    private String numeroTicketSD;
    
    public Retoure(String matricule, String numeroSerie, LocalDate dateTransaction, String netBios, String numeroTicketSD) {
        super(matricule, numeroSerie, dateTransaction);
        setNetBios(netBios);
        setNumeroTicketSD(numeroTicketSD);
    }

    public String getNetBios() {
        return netBios;
    }

    public void setNetBios(String netBios) {
        this.netBios = netBios;
    }

    public String getNumeroTicketSD() {
        return numeroTicketSD;
    }

    public void setNumeroTicketSD(String numeroTicketSD) {
        this.numeroTicketSD = numeroTicketSD;
    }
    
    
}
