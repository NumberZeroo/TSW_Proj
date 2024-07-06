DROP
DATABASE IF EXISTS tswproj;
CREATE
DATABASE tswproj;
USE
tswproj;

CREATE TABLE Utente
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    imgPath  VARCHAR(255) NOT NULL,
    isAdmin  TINYINT(1) NOT NULL,
    password CHAR(128)    NOT NULL,
    #        SHA-512
    CONSTRAINT fk_utente_admin CHECK (isAdmin IN (0, 1))
);

CREATE TABLE InfoConsegna
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    citta        VARCHAR(255) NOT NULL,
    cap          INT          NOT NULL,
    via          VARCHAR(255) NOT NULL,
    altro        VARCHAR(255),
    destinatario VARCHAR(255) NOT NULL,
    idUtente     BIGINT UNSIGNED NOT NULL,
    isDefault    TINYINT(1) DEFAULT 0 NOT NULL,
    FOREIGN KEY (idUtente) REFERENCES Utente (id)
);

CREATE TABLE Ordine
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idUtente     BIGINT UNSIGNED NOT NULL,
    pathFattura  VARCHAR(255) NOT NULL,
    infoConsegna BIGINT UNSIGNED NOT NULL,
    dataOrdine   DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, idUtente),
    FOREIGN KEY (idUtente) REFERENCES Utente (id),
    FOREIGN KEY (infoConsegna) REFERENCES InfoConsegna (id)
);

CREATE TABLE Animale
(
    id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL
);

CREATE TABLE Pet
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    Nome         VARCHAR(255) NOT NULL,
    IdUtente     BIGINT UNSIGNED NOT NULL,
    imgPath      VARCHAR(255) NOT NULL,
    Tipo         BIGINT UNSIGNED NOT NULL,
    Taglia       ENUM('PICCOLA', 'MEDIA', 'GRANDE'),
    Sterilizzato TINYINT(1),
    DataNascita  DATE         NOT NULL,
    PRIMARY KEY (id, Nome, IdUtente),
    FOREIGN KEY (IdUtente) REFERENCES Utente (id),
    FOREIGN KEY (Tipo) REFERENCES Animale (id)
);

CREATE TABLE Prodotto
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nome          VARCHAR(255)  NOT NULL,
    Descrizione   VARCHAR(4096) NOT NULL,
    Disponibilità INT UNSIGNED NOT NULL,
    Taglia        ENUM('PICCOLA', 'MEDIA', 'GRANDE'),
    Categoria     VARCHAR(255)  NOT NULL,
    TipoAnimale   BIGINT UNSIGNED NOT NULL,
    MinEta        INT,
    MaxEta        INT,
    IVA           ENUM('4', '10', '22') NOT NULL,
    Prezzo        DECIMAL(7, 2) NOT NULL, -- 5 cifre intere, 2 decimali
    Sterilizzati  TINYINT(1),
    Visibile      TINYINT(1) DEFAULT 1 NOT NULL,
    imgPath       VARCHAR(255)  NOT NULL,
    FOREIGN KEY (TipoAnimale) REFERENCES Animale (id)
);

CREATE TABLE Carrello
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    IdUtente BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, IdUtente),
    FOREIGN KEY (IdUtente) REFERENCES Utente (id) ON DELETE CASCADE
);

CREATE TABLE OrderItem
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(255)  NOT NULL,
    idProdotto BIGINT UNSIGNED NOT NULL,
    IdOrdine   BIGINT UNSIGNED NOT NULL,
    Prezzo     DECIMAL(7, 2) NOT NULL,
    Quantita   INT           NOT NULL,
    IVA        ENUM('4', '10', '22') NOT NULL,
    PRIMARY KEY (id, idProdotto, IdOrdine),
    FOREIGN KEY (IdOrdine) REFERENCES Ordine (id) ON DELETE CASCADE,
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id)
);

CREATE TABLE CartItem
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idProdotto BIGINT UNSIGNED NOT NULL,
    IdCarrello BIGINT UNSIGNED NOT NULL,
    Quantita   BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, idProdotto, IdCarrello),
    FOREIGN KEY (IdCarrello) REFERENCES Carrello (id) ON DELETE CASCADE,
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id) ON DELETE CASCADE
);

CREATE TABLE Wishlist
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    IdUtente BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, IdUtente),
    FOREIGN KEY (IdUtente) REFERENCES Utente (id) ON DELETE CASCADE
);

CREATE TABLE WishlistItem
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idProdotto BIGINT UNSIGNED NOT NULL,
    IdWishlist BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, idProdotto, IdWishlist),
    FOREIGN KEY (IdWishlist) REFERENCES Wishlist (id) ON DELETE CASCADE,
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id) ON DELETE CASCADE
);

CREATE TABLE Recensione
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    idUtente    BIGINT UNSIGNED NOT NULL,
    titolo      VARCHAR(255)  NOT NULL,
    commento    VARCHAR(1024) NOT NULL,
    valutazione DECIMAL(2, 1) NOT NULL, -- ES: 1.5, 4, 3.5, ...
    data        DATE          NOT NULL,
    idProdotto  BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id, idUtente, idProdotto, data),
    FOREIGN KEY (idUtente) REFERENCES Utente (id),
    FOREIGN KEY (idProdotto) REFERENCES Prodotto (id),
    CONSTRAINT valutazione CHECK (valutazione <= 5)
);
