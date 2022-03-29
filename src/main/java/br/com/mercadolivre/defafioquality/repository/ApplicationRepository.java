package br.com.mercadolivre.defafioquality.repository;

import br.com.mercadolivre.defafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.defafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.defafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.defafioquality.exceptions.DbEntryAlreadyExists;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicationRepository<T, K> {
    public List<T> read() throws DatabaseReadException, DatabaseManagementException;
    public Optional<T> find(K id) throws DatabaseReadException, DatabaseManagementException;
    public List<T> add(List<T> listToAdd) throws DatabaseWriteException, DatabaseReadException, DbEntryAlreadyExists, DatabaseManagementException;
    public List<T> findBy(Map<String, Object> filters) throws DatabaseReadException, DatabaseManagementException;
    public Integer update(Map<String, Object> filters, Map<String, Object> values) throws DatabaseReadException, DatabaseWriteException, DatabaseManagementException;
}
