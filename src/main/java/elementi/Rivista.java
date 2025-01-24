package elementi;


import jakarta.persistence.Entity;

@Entity
public class Rivista extends ElementoCatalogo {
    private String periodicita;

    public Rivista(){}

public Rivista (String ISBN, String titolo, int annoPubblicazione, int numeroPagine,String periodicita){
    super(ISBN,titolo,annoPubblicazione,numeroPagine);
    this.periodicita = periodicita;
}

    public String getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(String periodicita) {
        this.periodicita = periodicita;
    }
}