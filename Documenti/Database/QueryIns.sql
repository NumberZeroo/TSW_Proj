use tswProj;

INSERT INTO Utente (username, email, imgPath, isAdmin, password)
VALUES ('john_doe', 'john@example.com', 'img/users/john_doe.jpg', 0, SHA2('password123', 512)),
       ('jane_admin', 'jane@example.com', 'img/users/jane_admin.jpg', 1, SHA2('adminpassword', 512)),
       ('alice_user', 'alice@example.com', 'img/users/alice_user.jpg', 0, SHA2('alicepassword', 512));

INSERT INTO InfoConsegna (citta, cap, via, altro, destinatario, idUtente, isDefault)
VALUES ('Milano', 20100, 'Via Roma, 1', 'Interno 5', 'John Doe', 1, 1),
       ('Torino', 10100, 'Corso Vittorio, 20', 'Scala B', 'Jane Admin', 2, 1),
       ('Firenze', 50100, 'Piazza Duomo, 15', NULL, 'Alice User', 3, 1);

INSERT INTO Ordine (idUtente, pathFattura, infoConsegna)
VALUES (1, 'img/invoices/invoice1.pdf', 1),
       (2, 'img/invoices/invoice2.pdf', 2),
       (3, 'img/invoices/invoice3.pdf', 3);

INSERT INTO Animale (Nome)
VALUES ('Cane'),
       ('Gatto'),
       ('Uccello');

INSERT INTO Pet (Nome, IdUtente, imgPath, Tipo, Taglia, Sterilizzato, DataNascita)
VALUES ('Buddy', 1, 'img/products/placeholder.png', 1, 'GRANDE', 1, '2020-01-15'),
       ('Whiskers', 3, 'img/products/placeholder.png', 2, 'PICCOLA', 0, '2021-03-10'),
       ('Tweety', 2, 'img/products/placeholder.png', 3, 'MEDIA', 0, '2019-07-20');

INSERT INTO Prodotto (Nome, Descrizione, Disponibilità, Taglia, Categoria, TipoAnimale, MinEta, MaxEta, IVA, Prezzo,
                      Sterilizzati, Visibile, imgPath)
VALUES ('Collare per cani', 'Collare regolabile e resistente, disponibile in vari colori.', 50, 'GRANDE', 'Giocattoli',
        1, NULL, NULL, '22', 15.99, 1, 1, 'img/products/PettorinaCane.jpg'),
       ('Gioco per cani', 'Gioco interattivo per cani, perfetto per mantenerli attivi.', 100, 'MEDIA', 'Giocattoli', 1,
        NULL, NULL, '22', 12.99, 0, 1, 'img/products/GiocoMasticareCane.jpg'),
       ('Mangiatoia per uccelli', 'Mangiatoia comoda e pratica per uccelli di piccola taglia.', 75, 'PICCOLA',
        'Alimentari', 3, NULL, NULL, '22', 9.99, 0, 1, 'img/products/MangiatoiaUccelli.jpg'),
       ('Cuccia per cani', 'Cuccia confortevole e resistente, adatta a cani di taglia media e grande.', 40, 'GRANDE',
        'Giocattoli', 1, NULL, NULL, '22', 49.99, 1, 1, 'img/products/CucciaPerCani.jpg'),
       ('Tiragraffi per gatti', 'Tiragraffi robusto e stabile, ideale per gatti di tutte le età.', 30, 'MEDIA',
        'Giocattoli', 2, NULL, NULL, '22', 29.99, 0, 1, 'img/products/TiragraffiGatti.jpg'),
       ('Gabbietta per uccelli', 'Gabbietta spaziosa e accessoriata, perfetta per piccoli uccelli.', 20, 'MEDIA',
        'Giocattoli', 3, NULL, NULL, '22', 39.99, 0, 1, 'img/products/GabbiettaUccelli.jpg'),
       ('Guinzaglio per cani', 'Guinzaglio resistente e comodo, adatto per passeggiate sicure.', 60, 'GRANDE',
        'Giocattoli', 1, NULL, NULL, '22', 19.99, 1, 1, 'img/products/GuinzaglioCane.jpg'),
       ('Snack per gatti', 'Snack deliziosi e salutari per gatti, perfetti come ricompensa.', 150, 'PICCOLA',
        'Alimentari', 2, NULL, NULL, '22', 5.49, 0, 1, 'img/products/SnackGatto.jpg'),
       ('Altalena per uccelli', 'Altalena divertente per uccelli, stimola l\'attività e il gioco.', 25, 'PICCOLA',
        'Giocattoli', 3, NULL, NULL, '22', 14.99, 0, 1, 'img/products/AltalenaUccelli.jpg');

INSERT INTO Carrello (IdUtente)
VALUES (1),
       (2),
       (3);

INSERT INTO OrderItem (nome, idProdotto, IdOrdine, Prezzo, Quantita, IVA)
VALUES ('Collare per cani', 1, 1, 15.99, 1, '22'),
       ('Gioco per gatti', 2, 1, 12.99, 2, '22'),
       ('Mangiatoia per uccelli', 3, 2, 9.99, 3, '22'),
       ('Collare per cani', 1, 3, 15.99, 1, '22'),
       ('Gioco per gatti', 2, 3, 12.99, 2, '22'),
       ('Mangiatoia per uccelli', 3, 3, 9.99, 3, '22');

INSERT INTO CartItem (idProdotto, IdCarrello, Quantita, Nome)
VALUES (1, 1, 2, "Collare per Cani"),
       (2, 1, 1, "Gioco per cani"),
       (3, 2, 4, "Mangiatoia per uccelli"),
       (4, 3, 1, "Cuccia per cani"),
       (5, 3, 1, "Tiragraffi per gatti");

INSERT INTO Recensione (idUtente, titolo, commento, valutazione, data, idProdotto)
VALUES (1, 'Great Dog Collar', 'My dog loves this collar!', 4.5, '2023-06-01', 1),
       (2, 'Cat Toy Review', 'My cat plays with this toy all day!', 5.0, '2023-06-02', 2),
       (3, 'Bird Feeder Review', 'Very convenient and well-designed feeder.', 4.0, '2023-06-03', 3);