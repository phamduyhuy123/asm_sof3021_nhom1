package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.data.jpa.repository.Query;
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

}