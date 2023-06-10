package com.nhom2.asmsof3021.controller.productController;

import com.nhom2.asmsof3021.factory.ProductFactory;
import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

import static com.nhom2.asmsof3021.controller.AdminPageController.productManagementDefault;
import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryRepo categoryRepository;
    private final HttpSession session;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    private final Map<Integer, ProductFactory> factoryMap;





    @GetMapping("/admin/product/category/{id}")
    public String getCategory(@PathVariable Integer id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("categoryViewName", "admin/product/" + category.getEntityClassName());
        return category.getEntityClassName();
    }

    @GetMapping("/admin/product/EDIT/{id}")
    public String edit(@PathVariable Integer id, Model model, Principal principal) {
        checkIsAuthenticated(principal, session, userRepository);
        productManagementDefault(model, categoryRepository, session, productRepo);
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("categoryViewName", "admin/product/" + product.getCategory().getEntityClassName());
        List<Product> list = productRepo.findAll();
        model.addAttribute("products", list);
        model.addAttribute("categoryName", product.getCategory().getEntityClassName());

        return "admin/productManagement";
    }

    @GetMapping("/admin/product/category/new")
    public String newForm(@RequestParam("categoryId") Integer id, Model model) {


        Category category = categoryRepository.findById(id).orElseThrow();
        String categoryEntityClassName = category.getEntityClassName();
        ProductFactory productFactory = factoryMap.get(id);
        if (productFactory == null) {
            throw new IllegalArgumentException("Invalid categoryId");
        }
        productManagementDefault(model, categoryRepository, session, productRepo);
        Product product = productFactory.createProduct();
        product.setCategory(category);
        model.addAttribute("categoryViewName", "admin/product/" + categoryEntityClassName);
        model.addAttribute("categoryName" + categoryEntityClassName);
        model.addAttribute("product", product);
        return "admin/productManagement";
    }

    @GetMapping("/product/{id}")
    public String productDetail(Model model, @PathVariable Integer id) {
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("productDetail", product);
        model.addAttribute("productsSimilar", productRepo.findAll());
        return "ProductDetail";
    }

    @GetMapping("/product/api/count/similar/{id}")
    public ResponseEntity<Integer> productsSimilar(@PathVariable Integer id) {
        Product product = productRepo.findById(id).orElseThrow();
        Set<Product> productsSimilar = getProductsSimilar(product);
        if (productsSimilar.isEmpty()) {
            throw new NoSuchElementException("No value present");
        }
        return ResponseEntity.ok(productRepo.findAll().size());
    }

    @GetMapping("/product/api/stock")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> checkStockInProduct(
            @RequestParam("amount") Integer amount,
            @RequestParam("id") Integer id) {
        Map<String, Integer> responseData = new HashMap<>();

        Optional<List<Product>> optionalProducts = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        if (optionalProducts.isPresent()) {
            List<Product> products = optionalProducts.get();
            for (Product p : products) {
                if (p.getId() == id) {
                    if (isQuantityExceeding(p, amount)) {
                        System.out.println("vuot qua so luong");
                        responseData.put("error", p.getStock() - p.getQuantity());
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseData);
                    } else {
                        responseData.put("success", 666);
                        return ResponseEntity.ok(responseData);
                    }
                }
            }
        } else {
            Product product = productRepo.findById(id).orElseThrow();
            if (isAmountExceeding(product, amount)) {
                responseData.put("error", product.getStock());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseData);
            } else {
                responseData.put("success", 666);
                return ResponseEntity.ok(responseData);
            }
        }

        // Nếu không tìm thấy sản phẩm
        responseData.put("error", 0);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }

    @PostMapping("/product/api/cart/add/{id}/{quantity}")
    @ResponseBody
    public ResponseEntity<List<Product>> findProductById(@PathVariable Integer id, @PathVariable Integer quantity) {
        List<Product> products;
        boolean isExceeding = false;
        Product product = productRepo.findById(id).orElseThrow();
        Optional<List<Product>> optionalProducts = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        if (optionalProducts.isPresent()) {
            products = optionalProducts.get();
            boolean isInCart=false;
            for (Product p : products) {
                if (p.getId() == id) {
                    isInCart=true;

                    if(quantity==1&& Objects.equals(p.getQuantity(), p.getStock())){
                        isExceeding=true;
                        break;
                    }
                    int tempQuantity=p.getQuantity();
                    p.setQuantity(p.getQuantity() + quantity);
                    isExceeding = p.getQuantity() > p.getStock();
                    if(isExceeding){
                        p.setQuantity(Math.min(tempQuantity + quantity, p.getStock()));
                    }
                    break;

                }
            }
            if(!isInCart){
                product.setQuantity(Math.min(quantity, product.getStock()));
                isExceeding = product.getQuantity() > product.getStock();
                products.add(product);
            }
        } else {
            products = new ArrayList<>();
            product.setQuantity(Math.min(quantity, product.getStock()));
            products.add(product);
        }

        session.setAttribute("cartItems", products);

        if (isExceeding) {
            System.out.println("isExceeding");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(products);
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/product/api/cart")
    @ResponseBody
    public ResponseEntity<List<Product>> getCartItems() {
        Optional<List<Product>> optionalCartItems = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        List<Product> cartItems = optionalCartItems.orElseThrow(() -> new NoSuchElementException("No value present"));

        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/product/api/cart/decrease/{id}")
    public ResponseEntity<List<Product>> decreaseItemFromCart(@PathVariable Integer id,@RequestParam("amount") Integer amount) {
        Product product = productRepo.findById(id).orElseThrow();
        List<Product> products=new ArrayList<>();
        Optional<List<Product>> optionalCartItems = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        if (optionalCartItems.isPresent()) {
            products = optionalCartItems.get();

            Iterator<Product> iterator = products.iterator();

            while (iterator.hasNext()) {
                Product p = iterator.next();
                if(p.getId()==id){
                    p.setQuantity(p.getQuantity()-1);
                    if(p.getQuantity()<1){
                        p.setQuantity(1);
                        session.setAttribute("cartItems",products);
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(products);
                    }
                    session.setAttribute("cartItems",products);
                    return ResponseEntity.ok(products);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(products);
    }
    @PutMapping("/product/api/cart/update/{id}")
    @ResponseBody
    public ResponseEntity<List<Product>> updateProductValueFromCart(
            @PathVariable Integer id,@RequestParam("amount") Integer quantity){
        List<Product> products;
        boolean isExceeding = false;
        Product product = productRepo.findById(id).orElseThrow();
        Optional<List<Product>> optionalProducts = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        if (optionalProducts.isPresent()) {
            products = optionalProducts.get();
            boolean isInCart=false;
            for (Product p : products) {
                if (p.getId() == id) {
                    isInCart=true;

//                    if(quantity==1&& Objects.equals(p.getQuantity(), p.getStock())){
//                        isExceeding=true;
//                        break;
//                    }
//                    int tempQuantity=p.getQuantity();
//                    p.setQuantity(quantity);
//                    isExceeding = p.getQuantity() > p.getStock();
//                    if(isExceeding){
//                        p.setQuantity(Math.min(tempQuantity + quantity, p.getStock()));
//                    }
                    int tempQuantity=p.getQuantity();
                    if(quantity>p.getStock()){
                        isExceeding=true;
                        p.setQuantity(p.getStock());
                    }else {
                        isExceeding=false;
                        p.setQuantity(quantity);
                    }
                    break;

                }
            }
            if(!isInCart){
                product.setQuantity(Math.min(quantity, product.getStock()));
                isExceeding = product.getQuantity() > product.getStock();
                products.add(product);
            }
        } else {
            products = new ArrayList<>();
            product.setQuantity(Math.min(quantity, product.getStock()));
            products.add(product);
        }

        session.setAttribute("cartItems", products);

        if (isExceeding) {
            System.out.println("isExceeding");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(products);
        } else {
            return ResponseEntity.ok(products);
        }
    }
    @DeleteMapping("/product/api/cart/remove/{id}")
    @ResponseBody
    public ResponseEntity<List<Product>> removeItemFromCart(@PathVariable Integer id) {
        Product product = productRepo.findById(id).orElseThrow();
        List<Product> products;
        if (session.getAttribute("cartItems") != null) {
            products = (List<Product>) session.getAttribute("cartItems");
            Iterator<Product> iterator = products.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                if (p.getId() == id) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            products = new ArrayList<>();
        }
        session.setAttribute("cartItems", products);
        return ResponseEntity.ok(products);
    }

    private Set<Product> getProductsSimilar(Product product) {
        Integer id = product.getId();
        Integer brandId = product.getBrand() != null ? product.getBrand().getBrandId() : null;
        Integer catalogId = product.getCategory() != null ? product.getCategory().getCatalogId() : null;
        Integer productLineId = product.getProductLine() != null ? product.getProductLine().getProductLineId() : null;
        Set<Product> productsSimilar = new HashSet<>();
        if (brandId != null && catalogId != null && productLineId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandIdAndCategory_CatalogIdAndProductLine_ProductLineId(brandId, catalogId, productLineId);
        } else if (brandId != null && catalogId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandIdAndCategory_CatalogId(brandId, catalogId);
        } else if (catalogId != null && productLineId != null) {
            productsSimilar = productRepo.findAllByCategory_CatalogIdAndProductLine_ProductLineId(catalogId, productLineId);
        } else if (catalogId != null) {
            productsSimilar = productRepo.findAllByCategory_CatalogId(catalogId);
        } else if (brandId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandId(brandId);
        } else if (productLineId != null) {
            productsSimilar = productRepo.findAllByProductLine_ProductLineId(productLineId);
        } else {
            productsSimilar = new HashSet<>(); // Không có thuộc tính nào được xác định, trả về danh sách rỗng.
        }
        for (Product p : productsSimilar
        ) {
            if (p.getId() == id) {
                productsSimilar.remove(p);
                break;
            }
        }
        return productsSimilar;
    }
    private boolean isQuantityExceeding(Product product, int amount) {
        return product.getQuantity() + amount >= product.getStock();
    }

    private boolean isAmountExceeding(Product product, int amount) {
        return amount >= product.getStock();
    }
}
