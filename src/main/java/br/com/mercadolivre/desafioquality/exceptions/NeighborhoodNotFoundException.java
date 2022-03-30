package br.com.mercadolivre.desafioquality.exceptions;

public class NeighborhoodNotFoundException extends RuntimeException {
    public NeighborhoodNotFoundException(String msg) {
        super(msg);
    }
}
