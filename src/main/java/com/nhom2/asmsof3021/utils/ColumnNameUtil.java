package com.nhom2.asmsof3021.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ColumnNameUtil {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityType<?>  setColumnNamesAsInputAttributes(String entityName) {
        Metamodel metamodel = entityManager.getMetamodel();
        Class<?> entityClass;

        try {
            entityClass = Class.forName(entityName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid entity name: " + entityName);
        }

        return metamodel.entity(entityClass);

    }

}

