package com.bank.model;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMPTE")

public class Compte implements Serializable {

    @Id
    @Column(name = "iban")
    private String iban;

    @Column(name = "saldo")
    private float saldo;

    @Column(name = "estat")
    private char estat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private Client client;

    public Compte() {
    }

    public Compte(String iban, float saldo, char estat, Client client) {
        this.iban = iban;
        this.saldo = saldo;
        this.estat = estat;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Compte [iban=" + iban + ", saldo=" + saldo + ", estat=" + estat + ", client=" + client.getIdClient() + "]";
    }

}
