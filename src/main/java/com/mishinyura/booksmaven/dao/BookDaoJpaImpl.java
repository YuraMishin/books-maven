package com.mishinyura.booksmaven.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Repository
public class BookDaoJpaImpl implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long getBooksCount() {
        return em
                .createQuery("select count(b) from Book b", Long.class)
                .getSingleResult();
    }
}