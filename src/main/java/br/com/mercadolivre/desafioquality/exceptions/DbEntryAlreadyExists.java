package br.com.mercadolivre.desafioquality.exceptions;

public class DbEntryAlreadyExists extends Exception {

    public DbEntryAlreadyExists(String msg) {
        super(msg);
    }

}
