package elementi;


import jakarta.persistence.Entity;

@Entity
public class Libro extends ElementoCatalogo {
    private String autore;
    private String genere;

public Libro(String ISBN, String titolo, int annoPubblicazione, int numeroPagine,String autore,String genere){
    super(ISBN,titolo,annoPubblicazione,numeroPagine);
    this.autore = autore;
    this.genere = genere;
}

    public Libro() {

    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
