package com.bank;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.bank.model.Client;
import com.bank.model.Compte;
import org.hibernate.query.Query;

public class App {

    // Aquest mètode neteja la pantalla per fer més còmode l'us de la app
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Menú principal (també loop principal)
    public static void mainMenu(Session session) {
        Scanner sc = new Scanner(System.in);
        String opt = "z";
        clearConsole();
        while (!opt.equals("0")) {
            System.out.println("Escull una opció:\n\t1.Crear un nou client i compte\n\t2.Consultar les dades del client\n\t3.Consultar els comptes d'un client\n\t0.Sortir");
            opt = sc.nextLine();
            System.out.println("OPT: " + opt);
            switch (opt) {
                case "0":
                    break;
                case "1":
                    clearConsole();
                    inserirDades(sc, session);
                    break;
                case "2":
                    clearConsole();
                    consultarClient(sc, session);
                    break;
                case "3":
                    clearConsole();
                    consultarComptes(sc, session);
                    break;
                default:
                    clearConsole();
                    System.out.println("Opció incorrecta.\n");
                    break;
            }
        }
        session.getTransaction().commit();
        sc.close();
        clearConsole();
        System.out.println("Arreveure, fins la pròxima!");
    }

    // Mètode usat per a inserir registres. Demana la informació per CLI a l'usuari.
    public static void inserirDades(Scanner sc, Session session) {

        System.out.print("Introdueix el DNI del client: ");
        String dni = sc.nextLine();

        Query<Client> queryClient = session.createQuery("FROM Client WHERE idClient = :dni");
        queryClient.setParameter("dni", dni);
        List<Client> dataClient = queryClient.getResultList();
        
        if (dataClient.isEmpty()) {   // Si el client no existeeix fem el següent:

            clearConsole();
            System.out.println("El client no existeix, es crearà un de nou.");

            System.out.print("\nIntrodueix el nom del client: ");
            String nom = sc.nextLine();
    
            System.out.print("\nIntrodueix el pais del client: ");
            String pais = sc.nextLine();
    
            System.out.print("\nIntrodueix l'email del client: ");
            String email = sc.nextLine();

            Client client = new Client(dni, nom, pais, email);

            System.out.print("\nIntrodueix l'IBAN del compte: ");
            String iban = sc.nextLine();
    
            System.out.print("\nIntrodueix el saldo del compte: ");
            float saldo = Float.parseFloat(sc.nextLine());
    
            System.out.print("\nIntrodueix l'estat del compte: ");
            char estat = sc.nextLine().charAt(0);

            Compte compte = new Compte(iban, saldo, estat, client);

            session.persist(compte); // Al persistir el compte, també es persisteix el client associat
            clearConsole();
            System.out.println("Client i compte creats exitosament.\n");

        } else {

            Client client = dataClient.get(0);

            clearConsole();
            System.out.println("El client ja existeix.");

            System.out.print("\nIntrodueix l'IBAN del compte: ");
            String iban = sc.nextLine();

            Query<Client> queryCompte = session.createQuery("FROM Compte WHERE iban = :iban");
            queryCompte.setParameter("iban", iban);
            List<Client> dataCompte = queryCompte.getResultList();

            if (dataCompte.isEmpty()) {

                clearConsole();
                System.out.println("El compte no existeix, es crearà un de nou.");

                System.out.print("\nIntrodueix el saldo del compte: ");
                float saldo = Float.parseFloat(sc.nextLine());
        
                System.out.print("\nIntrodueix l'estat del compte: ");
                char estat = sc.nextLine().charAt(0);

                Compte compte = new Compte(iban, saldo, estat, client);

                session.persist(compte); // Al persistir el compte, també es persisteix el client associat
                clearConsole();
                System.out.println("Compte creat exitosament.\n");
                
            } else {

                clearConsole();
                System.out.println("ERROR: El compte i el client ja existeixen.\n");
                
            }

        }

    }

    // Mètode usat per consultar dades d'un client concret. Demana el DNI del client per CLI.
    public static void consultarClient(Scanner sc, Session session) {

        System.out.print("Insereix el DNI del client: ");
        String dni = sc.nextLine();
        Query<Client> q = session.createQuery("FROM Client WHERE idClient = :dni");
        q.setParameter("dni", dni);
        List<Client> data = q.getResultList();

        if (data.isEmpty()) {
            
            clearConsole();
            System.out.println("El client no existeix.\n");

        } else {

            Client client = data.get(0);
            clearConsole();
            System.out.println(client.toString() + "\n");

        }

    }

    public static void consultarComptes(Scanner sc, Session session) {

        System.out.print("Insereix el DNI del client: ");

        String dni = sc.nextLine();
        Query<Compte> q = session.createQuery("FROM Compte WHERE client.idClient = :dni");
        q.setParameter("dni", dni);
        List<Compte> data = q.getResultList();

        clearConsole();
        for (Compte compte : data) {
            System.out.println(compte.toString());
        }
        
        System.out.println("\n");

    }


    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        mainMenu(session);

        session.close();

        /*--- Creació de les taules i dades d'exemple (executar només a l'inici) ---*/ 

        // Client cl = new Client("21798253A", "Pau Insa Delgado", "España", "pinsa04@gmail.com");
        // Compte cp = new Compte("ES2114650100722030876293", 1563894.35f, 'A', cl);
        // Compte cp2 = new Compte("ES2114650100722034686293", 32562.35f, 'C', cl);
        // session.persist(cp);
        // session.persist(cp2);
        // session.getTransaction().commit();

    }
}
