package org.example;

import DAO.ElementoCatalogoDAO;
import DAO.PrestitoDAO;
import elementi.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestioneBiblioteca");
        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO elementoCatalogoDAO = new ElementoCatalogoDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("1. Aggiungi un elemento del catalogo");
            System.out.println("2. Rimuovi un elemento del catalogo dato un codice ISBN");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Ricerca per titolo o parte di esso");
            System.out.println("7. Ricerca degli elementi attualmente in prestito dato un numero di tessera utente");
            System.out.println("8. Ricerca di tutti i prestiti scaduti e non ancora restituiti");
            System.out.println("9. Aggiungi un prestito");
            System.out.println("10. Esci");
            System.out.print("Opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    aggiungiElemento(scanner, elementoCatalogoDAO);
                    break;

                case 2:
                    rimuoviElemento(scanner, elementoCatalogoDAO);
                    break;

                case 3:
                    ricercaPerISBN(scanner, elementoCatalogoDAO);
                    break;

                case 4:
                    ricercaPerAnnoPubblicazione(scanner, elementoCatalogoDAO);
                    break;

                case 5:
                    ricercaPerAutore(scanner, elementoCatalogoDAO);
                    break;

                case 6:
                    ricercaPerTitolo(scanner, elementoCatalogoDAO);
                    break;

                case 7:
                    ricercaElementiInPrestito(scanner, prestitoDAO);
                    break;

                case 8:
                    ricercaPrestitiScaduti(prestitoDAO);
                    break;

                case 9:
                    aggiungiPrestito(scanner, prestitoDAO, elementoCatalogoDAO);
                    break;

                case 10:
                    System.out.println("Uscita...");
                    em.close();
                    emf.close();
                    scanner.close();
                    return;

                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private static void aggiungiElemento(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci il tipo di elemento (1. Libro, 2. Rivista): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Inserisci titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Inserisci anno di pubblicazione: ");
        int annoPubblicazione = scanner.nextInt();
        System.out.print("Inserisci numero di pagine: ");
        int numeroPagine = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Inserisci autore: ");
            String autore = scanner.nextLine();
            System.out.print("Inserisci genere: ");
            String genere = scanner.nextLine();

            Libro libro = new Libro(isbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
            elementoCatalogoDAO.aggiungiElemento(libro);
            System.out.println("Libro aggiunto con successo!");
        } else if (tipo == 2) {
            System.out.print("Inserisci periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            String periodicita = scanner.nextLine();

            Rivista rivista = new Rivista(isbn, titolo, annoPubblicazione, numeroPagine, periodicita);
            elementoCatalogoDAO.aggiungiElemento(rivista);
            System.out.println("Rivista aggiunta con successo!");
        } else {
            System.out.println("Tipo non valido.");
        }
    }

    private static void rimuoviElemento(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci ISBN da eliminare: ");
        String isbn = scanner.nextLine();

        elementoCatalogoDAO.rimuoviElemento(isbn);
        System.out.println("Elemento eliminato (se presente).");
    }

    private static void ricercaPerISBN(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci ISBN da cercare: ");
        String isbn = scanner.nextLine();

        ElementoCatalogo elemento = elementoCatalogoDAO.ricercaPerISBN(isbn);
        if (elemento != null) {
            System.out.println("Elemento trovato: " + elemento.getTitolo());
        } else {
            System.out.println("Nessun elemento trovato con ISBN: " + isbn);
        }
    }

    private static void ricercaPerAnnoPubblicazione(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

        List<ElementoCatalogo> elementi = elementoCatalogoDAO.ricercaPerAnnoPubblicazione(anno);
        if (elementi.isEmpty()) {
            System.out.println("Nessun elemento trovato per l'anno: " + anno);
        } else {
            System.out.println("Elementi trovati:");
            for (ElementoCatalogo elemento : elementi) {
                System.out.println("- " + elemento.getTitolo());
            }
        }
    }

    private static void ricercaPerAutore(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci autore: ");
        String autore = scanner.nextLine();

        List<Libro> libri = elementoCatalogoDAO.ricercaPerAutore(autore);
        if (libri.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore: " + autore);
        } else {
            System.out.println("Libri trovati:");
            for (Libro libro : libri) {
                System.out.println("- " + libro.getTitolo());
            }
        }
    }

    private static void ricercaPerTitolo(Scanner scanner, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci titolo o parte di esso: ");
        String titolo = scanner.nextLine();

        List<ElementoCatalogo> elementi = elementoCatalogoDAO.ricercaPerTitolo(titolo);
        if (elementi.isEmpty()) {
            System.out.println("Nessun elemento trovato con titolo: " + titolo);
        } else {
            System.out.println("Elementi trovati:");
            for (ElementoCatalogo elemento : elementi) {
                System.out.println("- " + elemento.getTitolo());
            }
        }
    }

    private static void ricercaElementiInPrestito(Scanner scanner, PrestitoDAO prestitoDAO) {
        System.out.print("Inserisci numero di tessera utente: ");
        int numeroTessera = scanner.nextInt();
        scanner.nextLine();

        List<ElementoCatalogo> elementi = prestitoDAO.ricercaElementiInPrestito(numeroTessera);
        if (elementi.isEmpty()) {
            System.out.println("Nessun elemento in prestito per l'utente con tessera: " + numeroTessera);
        } else {
            System.out.println("Elementi in prestito:");
            for (ElementoCatalogo elemento : elementi) {
                System.out.println("- " + elemento.getTitolo());
            }
        }
    }

    private static void ricercaPrestitiScaduti(PrestitoDAO prestitoDAO) {
        List<Prestito> prestiti = prestitoDAO.ricercaPrestitiScaduti();
        if (prestiti.isEmpty()) {
            System.out.println("Nessun prestito scaduto e non ancora restituito.");
        } else {
            System.out.println("Prestiti scaduti e non ancora restituiti:");
            for (Prestito prestito : prestiti) {
                System.out.println("- Prestito ID: " + prestito.getIdPrestito() + ", Elemento: " + prestito.getElementoPrestato().getTitolo());
            }
        }
    }

    private static void aggiungiPrestito(Scanner scanner, PrestitoDAO prestitoDAO, ElementoCatalogoDAO elementoCatalogoDAO) {
        System.out.print("Inserisci ISBN dell'elemento da prestare: ");
        String isbn = scanner.nextLine();


        ElementoCatalogo elemento = elementoCatalogoDAO.ricercaPerISBN(isbn);
        if (elemento == null) {
            System.out.println("Nessun elemento trovato con ISBN: " + isbn);
            return;
        }

        System.out.print("Inserisci numero di tessera utente: ");
        int numeroTessera = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci la data di inizio prestito (AAAA-MM-GG): ");
        String dataInizioStr = scanner.nextLine();
        LocalDate dataInizio = LocalDate.parse(dataInizioStr);

        LocalDate dataRestituzionePrevista = dataInizio.plusDays(30);

        Utente utente = new Utente();
        utente.setNumeroTessera(numeroTessera);



        Prestito prestito = new Prestito();
        prestito.setElementoPrestato(elemento);
        prestito.setUtente(utente);
        prestito.setDataInizio(dataInizio);
        prestito.setDataRestituzionePrevista(dataRestituzionePrevista);
        prestitoDAO.aggiungiPrestito(prestito);
        System.out.println("Prestito aggiunto con successo!");
    }
}