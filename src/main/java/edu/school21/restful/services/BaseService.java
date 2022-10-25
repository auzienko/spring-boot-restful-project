package edu.school21.restful.services;


import java.util.Set;

public interface BaseService<T>  {
    Set<T> findAll(int page,  int size);

    T findById(Long id);

    T save(T entity);

    void deleteById(Long id);

    void delete(T entity);

}