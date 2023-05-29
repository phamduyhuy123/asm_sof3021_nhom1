package com.nhom2.asmsof3021.utils;

import org.springframework.web.servlet.ModelAndView;

public class Router {
    public static ModelAndView routeToPage(String viewName){
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("viewName",viewName);
        return modelAndView;
    }
}
