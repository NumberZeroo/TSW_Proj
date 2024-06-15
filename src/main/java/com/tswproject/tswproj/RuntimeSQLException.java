package com.tswproject.tswproj;

/**
 * Il webcontainer può intercettare solo le eccezioni runtime, questa eccezione deve essere usata come wrapper delle SQL exception
 * L'intenzione è non gestire ma di lanciarle quando delle controlled exception devono essere gestite bloccando l'applicazione
 * La "gestione" è semplicemente la redirezione verso una pagina di errore (web.xml)
 */

public class RuntimeSQLException extends RuntimeException {
    public RuntimeSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeSQLException(String message) {
        super(message);
    }
}
