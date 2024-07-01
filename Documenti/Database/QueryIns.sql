USE tswproj;

-- Inserimento dati nella tabella Utente
INSERT INTO Utente (username, email, imgPath, isAdmin, password)
VALUES ('john_doe', 'john@example.com', '/images/john_doe.jpg', 0, SHA2('password123', 512)),
       ('jane_admin', 'jane@example.com', '/images/jane_admin.jpg', 1, SHA2('adminpassword', 512)),
       ('alice_user', 'alice@example.com', '/images/alice_user.jpg', 0, SHA2('alicepassword', 512));

-- Inserimento dati nella tabella InfoConsegna
INSERT INTO InfoConsegna (citta, cap, via, altro, destinatario, idUtente, isDefault)
VALUES ('Milano', 20100, 'Via Roma, 1', 'Interno 5', 'John Doe', 1, 1),
       ('Torino', 10100, 'Corso Vittorio, 20', 'Scala B', 'Jane Admin', 2, 1),
       ('Firenze', 50100, 'Piazza Duomo, 15', NULL, 'Alice User', 3, 1);

-- Inserimento dati nella tabella Animale
INSERT INTO Animale (Nome)
VALUES ('Cane'),
       ('Gatto'),
       ('Uccello');

-- Inserimento di nuovi prodotti nella tabella Prodotto
INSERT INTO Prodotto (Nome, Descrizione, Disponibilità, Taglia, Categoria, TipoAnimale, MinEta, MaxEta, IVA, Prezzo,
                      Sterilizzati, Visibile, imgPath)
VALUES ('Cuccia per cani',
        'Cuccia comoda e resistente per cani di tutte le taglie, realizzata con materiali impermeabili e facili da pulire.',
        50, 'GRANDE', 'Accessori', 1, NULL, NULL, '22', 89.99, 1, 1, 'img/placeholder.png'),
       ('Tiragraffi per gatti',
        'Tiragraffi di alta qualità con diverse piattaforme e giochi inclusi, perfetto per mantenere il tuo gatto attivo e felice.',
        30, 'MEDIA', 'Giochi', 2, NULL, NULL, '22', 45.50, 0, 1, 'img/placeholder.png'),
       ('Mangime per pesci tropicali',
        'Mangime bilanciato e nutriente specifico per pesci tropicali, favorisce la crescita e la vivacità dei colori.',
        200, 'PICCOLA', 'Alimentazione', 3, NULL, NULL, '22', 12.99, 0, 1, 'img/placeholder.png'),
       ('Gabbia per uccelli',
        'Gabbia spaziosa e confortevole per uccelli di piccola e media taglia, dotata di accessori e giochi.', 15,
        'MEDIA', 'Accessori', 3, NULL, NULL, '22', 110.00, 0, 1, 'img/placeholder.png'),
       ('Pettorina per cani',
        'Pettorina ergonomica e regolabile per cani di tutte le taglie, realizzata in materiale traspirante.', 75,
        'GRANDE', 'Accessori', 1, NULL, NULL, '22', 35.99, 1, 1, 'img/placeholder.png'),
       ('Lettiera per gatti',
        'Lettiera agglomerante e profumata, garantisce una lunga durata e un controllo ottimale degli odori.', 120,
        'MEDIA', 'Accessori', 2, NULL, NULL, '22', 18.50, 0, 1, 'img/placeholder.png'),
       ('Gioco da masticare per cani',
        'Gioco resistente da masticare per cani, aiuta a mantenere i denti puliti e a prevenire il tartaro.', 80,
        'GRANDE', 'Giochi', 1, NULL, NULL, '22', 9.99, 1, 1, 'img/placeholder.png'),
       ('Snack per gatti',
        'Snack deliziosi e salutari per gatti, perfetti come ricompensa o come spuntino tra i pasti.', 150, 'PICCOLA',
        'Alimentazione', 2, NULL, NULL, '22', 5.49, 0, 1, 'img/placeholder.png');

-- Inserimento dati nella tabella Pet
INSERT INTO Pet (Nome, IdUtente, imgPath, Tipo, Taglia, Sterilizzato, DataNascita)
VALUES ('Buddy', 1, 'img/placeholder.png', 1, 'GRANDE', 1, '2020-01-15'),     -- Un cane per john_doe
       ('Whiskers', 3, 'img/placeholder.png', 2, 'PICCOLA', 0, '2021-03-10'), -- Un gatto per alice_user
       ('Tweety', 2, 'img/placeholder.png', 3, 'MEDIA', 0, '2019-07-20'),     -- Un uccello per jane_admin
       ('Max', 1, 'img/placeholder.png', 1, 'GRANDE', 1, '2018-11-22'),       -- Un altro cane per john_doe
       ('Shadow', 2, 'img/placeholder.png', 2, 'MEDIA', 1, '2020-05-18'),     -- Un gatto per jane_admin
       ('Charlie', 3, 'img/placeholder.png', 1, 'PICCOLA', 0, '2022-09-05'),  -- Un cane per alice_user
       ('Sunny', 1, 'img/placeholder.png', 3, 'PICCOLA', 0, '2021-08-30'),    -- Un uccello per john_doe
       ('Mittens', 3, 'img/placeholder.png', 2, 'GRANDE', 1, '2017-12-25');
-- Un altro gatto per alice_user

-- Inserimento dati nella tabella Ordine
INSERT INTO Ordine (idUtente, pathFattura, infoConsegna)
VALUES (1, '/invoices/invoice1.pdf', 1),
       (2, '/invoices/invoice2.pdf', 2),
       (3, '/invoices/invoice3.pdf', 3);

-- Inserimento dati nella tabella Carrello
INSERT INTO Carrello (IdUtente)
VALUES (1),
       (2),
       (3);

-- Inserimento dati nella tabella OrderItem
INSERT INTO OrderItem (idProdotto, IdOrdine, Prezzo, Quantita)
VALUES (1, 1, 89.99, 1),
       (2, 1, 45.50, 2),
       (3, 2, 12.99, 3),
       (4, 3, 110.00, 1),
       (5, 3, 35.99, 1);

-- Inserimento dati nella tabella CartItem
INSERT INTO CartItem (idProdotto, IdCarrello, Quantita)
VALUES (1, 1, 2),
       (2, 1, 1),
       (3, 2, 4),
       (4, 3, 1),
       (5, 3, 1);

-- Inserimento dati nella tabella Recensione
INSERT INTO Recensione (idUtente, titolo, commento, valutazione, data, idProdotto)
VALUES (1, 'Great Dog Food', 'My dog loves this food!', 4.5, '2023-06-01', 1),
       (2, 'Cat Toy Review', 'My cat plays with this toy all day!', 5.0, '2023-06-02', 2),
       (3, 'Bird Cage Review', 'Very spacious and well-made cage.', 4.0, '2023-06-03', 3);
