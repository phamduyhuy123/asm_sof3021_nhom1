package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Orders;
import com.nhom2.asmsof3021.model.OrdersDetail;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.service.EmailService;
import com.nhom2.asmsof3021.service.ThymeleafService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class CheckOutController {
    private final EmailService emailService;

    private final HttpSession session;
    @ModelAttribute("orders")
    public Orders createOrders(){
        List<Product> productsCheckOut;
        Orders orders=null;
        Optional<List<Product>> optionalCartItems = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
        if(optionalCartItems.isPresent()){
            try (Stream<Product> stream = optionalCartItems.orElseGet(Collections::emptyList).stream()) {
                productsCheckOut = stream
                        .filter(Product::isSelected)
                        .collect(Collectors.toList());
            }
            if(!productsCheckOut.isEmpty()){
                orders=new Orders();
                List<OrdersDetail> ordersDetailList  = productsCheckOut.stream()
                        .map(product -> {
                            if (product.getDisCount() > 0) {
                                BigDecimal discountedPrice = product.getPrice().multiply(BigDecimal.valueOf(1 - (product.getDisCount() / 100.0)));
                                BigDecimal totalPrice = discountedPrice.multiply(BigDecimal.valueOf(product.getQuantity()));
                                return OrdersDetail.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .disCount(product.getDisCount())
                                        .price(discountedPrice)
                                        .quantity(product.getQuantity())
                                        .totalPrice(totalPrice)
                                        .image(product.getImage())
                                        .build();
                            } else {
                                BigDecimal totalPrice= product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
                                return OrdersDetail.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .price(product.getPrice())
                                        .totalPrice(totalPrice)
                                        .quantity(product.getQuantity())
                                        .image(product.getImage())
                                        .build();
                            }
                        })
                        .collect(Collectors.toList());
                BigDecimal totalPrice = ordersDetailList .stream()
                        .map(OrdersDetail::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                orders.setTotalPrice(totalPrice);
                System.out.println(orders.getTotalPrice());
                orders.setOrdersDetails(ordersDetailList );
            }
        }
        return orders;
    }
    @GetMapping("/checkout")
    public ModelAndView getCheckOutPage(Authentication authentication, @ModelAttribute("orders") Orders orders){
        ModelAndView modelAndView=new ModelAndView();
        System.out.println(orders);
        modelAndView.setViewName("/user/CheckOut");
        if(orders!=null){
            modelAndView.addObject("orders",orders);
            modelAndView.addObject("totalPriceAfterDisCount",orders.getTotalPrice());
            Optional<List<Product>> optionalCartItems = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));
            if(optionalCartItems.isPresent()){
                modelAndView.addObject("productSelectedInCart",orders.getOrdersDetails());
            }
            modelAndView.addObject("isCheckOutPage",true);
        }else {
            modelAndView.setViewName("redirect:/cart");
        }
        return modelAndView;
    }
    @PostMapping("/checkout")
    public ModelAndView performCheckOut(@Validated @ModelAttribute("orders") Orders orders, Errors errors){
        System.out.println(orders);
        ModelAndView modelAndView=new ModelAndView();
        if(errors.hasErrors()){
            modelAndView.setViewName("redirect:/checkout");
        }else {
            orders.setCreateAt(new Date());
            boolean isSuccess= emailService.placeOrders(orders);
            if(isSuccess){
                modelAndView.setViewName("success");
            }else {
                modelAndView.setViewName("redirect:/checkout?error=true");
            }
        }
        return modelAndView;
    }
}
