package br.com.compass.repository;

public interface BasicCRUD<T> {

    T findById(Long id);

    void save(T t);

    void update(T t);

    void delete(T t);

}
