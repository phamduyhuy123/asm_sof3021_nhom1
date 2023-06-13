package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.Orders;
import com.nhom2.asmsof3021.model.OrdersDetail;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.OrdersRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring6.context.webmvc.SpringWebMvcThymeleafRequestContext;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final Configuration configuration;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final ProductService productService;
    private final OrdersRepository ordersRepository;
    private final ThymeleafService thymeleafService;
    @Qualifier("ORDERS_QUEUE")
    private final ArrayDeque<Orders> ordersQueue;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;
    private final HttpSession session;
    @Transactional
    public boolean placeOrders(Orders orders){
        Orders orders1= ordersRepository.save(orders);
        ordersQueue.addLast(orders1);
        Optional<List<Product>> optionalCartItems = Optional.ofNullable((List<Product>) session.getAttribute("cartItems"));

        if(optionalCartItems.isPresent()){
            List<Product> productList=optionalCartItems.get();
            List<OrdersDetail> ordersDetails=orders.getOrdersDetails();
           List<Product> itemsOrdered= productList.stream()
                   .filter(product ->
                           ordersDetails
                                   .stream()
                                   .anyMatch(ordersDetail -> ordersDetail.getProductId() == product.getId())).toList();
            for (Product p:itemsOrdered
                 ) {
                int updatedStock= p.getStock()-p.getQuantity();
                if(updatedStock<0){
                    p.setStock(0);
                }else {
                    p.setStock(updatedStock);
                }
                productList.remove(p);
                productService.saveProduct(p);
            }
            session.setAttribute("cartItems",productList);
            return true;

        }
        return false;

    }
    @Scheduled(fixedDelay = 5000, initialDelay = 6000)
    public void sendEmail() throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

        helper.setSubject("Hóa đơn thanh toán");
        while (!ordersQueue.isEmpty()){
            Orders orders=ordersQueue.poll();
            helper.setTo(orders.getEmail());
            Context context=new Context();
            context.setVariable("orders",orders);

            helper.setText(thymeleafService.setContent(context), true);
            for (OrdersDetail ordersDetail:orders.getOrdersDetails()
            ) {
                String imagePath = "static/images/product/"+ordersDetail.getImage();
                helper.addInline(ordersDetail.getImage(),new ClassPathResource(imagePath));
            }

            javaMailSender.send(mimeMessage);
        }

    }

//    String getEmailContent(Orders orders) throws IOException, TemplateException {
//        StringWriter stringWriter = new StringWriter();
//        Map<String, Object> model = new HashMap<>();
//        model.put("orders", orders);
//        configuration.getTemplate("receipt.ftlh").process(model, stringWriter);
//        return stringWriter.getBuffer().toString();
//    }
}
