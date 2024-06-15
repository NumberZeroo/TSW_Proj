-- Inserimento prodotti

INSERT INTO Animale(Nome) VALUES ("Cane"), ("Gatto");
INSERT INTO Prodotto(Nome, Descrizione, Disponibilità, Taglia, Categoria, TipoAnimale, MinEta, MaxEta, IVA, Prezzo, Sterilizzati, imgPath) VALUES
       ("Prodotto1", "Descrizione del prodotto 1", 10, "PICCOLA", "Categoria 1", 1, 0, 99, '4', 12.5, 0, "/path/to/img"),
       ("Prodotto2", "Descrizione del prodotto 2", 11, "MEDIA", "Categoria 2", 2, 0, 99, '4', 13, 0, "/path/to/img2"),
       ("Prodotto3", "Descrizione del prodotto 3", 12, "GRANDE", "Categoria 2", 2, 0, 99, '4', 14.2, 0, "/path/to/img3");