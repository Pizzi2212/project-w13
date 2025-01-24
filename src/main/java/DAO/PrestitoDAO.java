package DAO;

import elementi.ElementoCatalogo;
import elementi.Prestito;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PrestitoDAO {
    private EntityManager em;

public PrestitoDAO(EntityManager em){
    this.em = em;
}

public void aggiungiPrestito(Prestito prestito){
    em.getTransaction().begin();
    em.persist(prestito);
    em.getTransaction().commit();
}
    public List<ElementoCatalogo> ricercaElementiInPrestito(int numeroTessera) {
        return em.createQuery("SELECT p.elementoPrestato FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", ElementoCatalogo.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
    }
    public List<Prestito> ricercaPrestitiScaduti() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .getResultList();
    }
}
