package edu.school21.restful.services;

import edu.school21.restful.models.Lesson;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(Long id);

    T save(T entity);

    void deleteById(Long id);

    void delete(T entity);
}