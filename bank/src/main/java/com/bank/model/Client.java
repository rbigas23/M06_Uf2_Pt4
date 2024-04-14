package com.bank.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENT")

public class Client implements Serializable {

    @Id
    @Column(name = "id_fiscal")
    private String idFiscal;

    @Column(name = "nom")
    private String nom;

    @Column(name = "pais")
    private String pais;

    @Column(name = "email")
    private String email;

    public Client(String idFiscal, String nom, String pais, String email) {
        this.idFiscal = idFiscal;
        this.nom = nom;
        this.pais = pais;
        this.email = email;
    }
    
}
