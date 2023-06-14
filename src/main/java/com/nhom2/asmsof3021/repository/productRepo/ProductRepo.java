package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface ProductRepo extends ProductRepoAbstract<Product> {

    Set<Product> findAllByProductLine_ProductLineId(int productLineId);
    Set<Product> findAllByBrand_BrandIdAndCategory_CatalogIdAndProductLine_ProductLineId(int brandId, int categoryId, int productLineId);
    Set<Product> findAllByBrand_BrandId(int brandId);
    Set<Product> findAllByBrand_BrandIdAndCategory_CatalogId(int brandId,int categoryId);
    Set<Product> findAllByBrand_BrandIdAndProductLine_ProductLineId(int brandId, int productLineId);
    Set<Product> findAllByCategory_CatalogId(int categoryId);
    Set<Product> findAllByCategory_CatalogIdAndProductLine_ProductLineId(int categoryId,int productLineId);
    //Count
    Integer countAllByProductLine_ProductLineId(int productLineId);
    Integer countAllByBrand_BrandIdAndCategory_CatalogIdAndProductLine_ProductLineId(int brandId, int categoryId, int productLineId);
    Integer countAllByBrand_BrandId(int brandId);
    Integer countAllByBrand_BrandIdAndCategory_CatalogId(int brandId,int categoryId);
    Integer countAllByBrand_BrandIdAndProductLine_ProductLineId(int brandId, int productLineId);
    Integer countAllByCategory_CatalogId(int categoryId);
    @Query("select  p.stock from Product p where p.id=?1 ")
    Integer checkStock(int id);
    Integer countAllByCategory_CatalogIdAndProductLine_ProductLineId(int categoryId,int productLineId);
    @Query("SELECT p FROM Product p JOIN p.category c JOIN p.brand b JOIN p.productLine pl WHERE (:categoryIds IS NULL OR c.catalogId IN (:categoryIds)) AND (:brandIds IS NULL OR b.brandId IN (:brandIds)) AND (:productLineIds IS NULL OR pl.productLineId IN (:productLineIds))")
    List<Product> findProductsByCategory_CatalogIdsAndBrand_BrandIdsAndProductLine_ProductLineIds(
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("brandIds") List<Integer> brandIds,
            @Param("productLineIds") List<Integer> productLineIds
    );
    void deleteById(Integer id);
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.brand " +
            "LEFT JOIN FETCH p.productLine " +
            "WHERE (p.name LIKE %?1% OR p.category.name LIKE %?1% OR p.brand.name LIKE %?1% OR p.productLine.name LIKE %?1% OR p.category.entityClassName LIKE %?1%)")
    List<Product> searchProduct(String search, Pageable pageable);

//    Page<Product> findAll(Pageable pageable, Sort sort);
}