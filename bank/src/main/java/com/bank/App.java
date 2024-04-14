package com.bank;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class App {

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void mainMenu(Session session) {
        Scanner sc = new Scanner(System.in);
        String opt = "z";
        while (!opt.equals("0")) {
            clearConsole();
            System.out.println("Escull una opció:\n\t1.Crear un nou client i compte\n\t2.Consultar les dades del client\n\t3.Consultar els comptes de client");
            opt = sc.next();
            switch (opt) {
                case "1":
                create(sc, session);
                    break;
                default:
                    break;
            }
        }
        sc.close();
        System.out.println("Arreveure, fins la pròxima!");
    }

    public static void create(Scanner sc, Session session) {
        System.out.println("Introdueix el DNI del client:");
        String id_fiscal = sc.next();
        System.out.println("Introdueix el nom del client:");
        String nom = sc.next();
        System.out.println("Introdueix el pais del client:");
        String pais = sc.next();
        System.out.println("Introdueix l'email del client:");
        String email = sc.next();

        System.out.println("Introdueix l'IBAN del compte:");
        String iban = sc.next();
        System.out.println("Introdueix el saldo del compte:");
        float saldo = Float.parseFloat(sc.next());
        System.out.println("Introdueix l'estat del compte:");
        char estat = sc.next().charAt(0);

    }

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        mainMenu(session);

        // Creació de les taules i dades d'exemple
        // Client cl = new Client("21798253A", "Pau Insa Delgado", "España", "pinsa04@gmail.com");
        // Compte cp = new Compte("ES2114650100722030876293", 1563894.35f, 'A', cl);
        // session.persist(cp);
        // session.getTransaction().commit();

        session.close();

    }
}