CREATE TABLE ElementoCatalogo (
    ISBN VARCHAR(13) PRIMARY KEY,
    Titolo VARCHAR(255) NOT NULL,
    AnnoPubblicazione INT,
    NumeroPagine INT
);

CREATE TABLE Libro (
    ISBN VARCHAR(13) PRIMARY KEY,
    Autore VARCHAR(255),
    Genere VARCHAR(100),
    FOREIGN KEY (ISBN) REFERENCES ElementoCatalogo(ISBN)
);

CREATE TABLE Rivista (
    ISBN VARCHAR(13) PRIMARY KEY,
    Periodicita VARCHAR(50),
    FOREIGN KEY (ISBN) REFERENCES ElementoCatalogo(ISBN)
);

CREATE TABLE Utente (
    NumeroTessera SERIAL PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    Cognome VARCHAR(100) NOT NULL,
    DataNascita DATE
);

CREATE TABLE Prestito (
    IDPrestito SERIAL PRIMARY KEY,
    ISBN VARCHAR(13),
    NumeroTessera INT,
    DataInizio DATE,
    DataRestituzionePrevista DATE,
    DataRestituzioneEffettiva DATE,
    FOREIGN KEY (ISBN) REFERENCES ElementoCatalogo(ISBN),
    FOREIGN KEY (NumeroTessera) REFERENCES Utente(NumeroTessera)
);