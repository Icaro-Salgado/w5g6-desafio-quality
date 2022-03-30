package br.com.mercadolivre.desafioquality.services.exceptions;

public class NeighborhoodNotFoundException extends RuntimeException {
    public NeighborhoodNotFoundException(String msg) {
        super(msg);
    }
}
