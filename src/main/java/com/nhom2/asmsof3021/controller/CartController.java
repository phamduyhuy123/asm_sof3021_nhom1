package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.factory.ProductFactory;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.UserRepository;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CategoryRepo categoryRepository;
    private final HttpSession session;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    private final Map<Integer, ProductFactory> factoryMap;
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


    private boolean isQuantityExceeding(Product product, int amount) {
        return product.getQuantity() + amount >= product.getStock();
    }

    private boolean isAmountExceeding(Product product, int amount) {
        return amount >= product.getStock();
    }
}
