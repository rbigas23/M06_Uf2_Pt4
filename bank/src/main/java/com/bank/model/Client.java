package com.bank.model;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENT")

public class Client implements Serializable {

    @Id
    @Column(name = "id_client")
    private String idClient;

    @Column(name = "nom")
    private String nom;

    @Column(name = "pais")
    private String pais;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_client", referencedColumnName = "id_client", updatable = false) --> Aquesta linia NO CAL amb la última versió de Hibernate
    private Set<Compte> comptes;

    public Client() {
    }

    public Client(String idClient, String nom, String pais, String email) {
        this.idClient = idClient;
        this.nom = nom;
        this.pais = pais;
        this.email = email;
    }

    public String getIdClient() {
        return idClient;
    }

    @Override
    public String toString() {
        return "Client [idClient=" + idClient + ", nom=" + nom + ", pais=" + pais + ", email=" + email + ", comptes="
                + comptes + "]";
    }
    
    
}
