package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.utils.ColumnNameUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


public class CategoryController {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private ColumnNameUtil columnNameUtil;
//    @Autowired
//    private CategoryRepo categoryRepository;
//
//    // ...
//
//    @GetMapping("/get-input-fields/{categoryId}")
//    @ResponseBody
//    public String getInputFields(@PathVariable Integer categoryId, Model model) {
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
//        Class<?> entityClass;
//
//        // Retrieve the entity type for the selected category
//        Metamodel metamodel = entityManager.getMetamodel();
//        EntityType<?> entityType=columnNameUtil.setColumnNamesAsInputAttributes(category.getClass().getName());
//
//
//        String inputFieldsHtml = generateInputFieldsHtml(entityType);
//
//        return inputFieldsHtml;
//    }
//
//    private String generateInputFieldsHtml(EntityType<?> entityType) {
//        StringBuilder inputFieldsHtml = new StringBuilder();
//
//        entityType.getAttributes().forEach(attribute -> {
//            String attributeName = attribute.getName();
//            inputFieldsHtml
//                    .append("<input type='text' name='")
//                    .append(attributeName).append("' ")
//                    .append("placeholder='")
//                    .append(entityType.);
//        });
//
//        return inputFieldsHtml.toString();
//    }
}
