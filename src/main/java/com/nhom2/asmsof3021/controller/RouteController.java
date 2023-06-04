package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.security.UserRepository;
import com.nhom2.asmsof3021.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;


@Controller
@RequiredArgsConstructor

public class RouteController {
    private final ProductService service;
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<Product> getProductByCategoryId(@PathVariable Integer categoryId){
//
//    }
    private final HttpSession session;
    private final UserRepository userRepository;
    @GetMapping({"/","/index"})
    public ModelAndView getHomePage(Principal principal){
        checkIsAuthenticated(principal,session,userRepository);

        ModelAndView modelAndView=new ModelAndView("index");


        List<Product> laptops= service.findProductCategoryById(1);
        modelAndView.addObject("laptops",laptops);


        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView routeToLoginPage( @RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView=new ModelAndView("LoginPage");
        if(error!=null){
            modelAndView.addObject("msg",error);
            return modelAndView;
        }



        return modelAndView;
    }

    @GetMapping("/admin/login")
    public String goToLoginPage(){
        return "admin/login";
    }
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String errorPage(final HttpServletRequest request, Model model){
        String errorMsg = "";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("httpCode",statusCode);
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorMsg = "Http Error Code: 404. Resource not found";

            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorMsg = "Http Error Code: 403. Forbidden";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorMsg = "Http Error Code: 500. Internal Server Error";

            }
        }

        model.addAttribute("errorMsg", errorMsg);
        return "error";
    }
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }

}
