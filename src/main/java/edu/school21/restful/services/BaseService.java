package edu.school21.restful.services;

import edu.school21.restful.models.Lesson;

import java.util.Set;

public interface BaseService<T> {
    Set<T> findAll();

    T findById(Long id);

    T save(T entity);

    void deleteById(Long id);

    void delete(T entity);

}