package DAO;

import elementi.ElementoCatalogo;
import elementi.Libro;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ElementoCatalogoDAO {
    private EntityManager em;

public ElementoCatalogoDAO(EntityManager em){
    this.em = em;
}

    public void aggiungiElemento(ElementoCatalogo elemento) {
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }

    public void rimuoviElemento(String ISBN) {
        ElementoCatalogo elemento = em.find(ElementoCatalogo.class, ISBN);
        if (elemento != null) {
            em.getTransaction().begin();
            em.remove(elemento);
            em.getTransaction().commit();
        }
    }

    public ElementoCatalogo ricercaPerISBN(String ISBN) {
        return em.find(ElementoCatalogo.class, ISBN);
    }

    public List<ElementoCatalogo> ricercaPerAnnoPubblicazione(int anno) {
        return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class)
                .setParameter("anno", anno)
                .getResultList();
    }

    public List<Libro> ricercaPerAutore(String autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }

    public List<ElementoCatalogo> ricercaPerTitolo(String titolo) {
        return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCatalogo.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }
}

