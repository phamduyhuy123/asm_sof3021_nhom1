package com.nhom2.asmsof3021.interceptor;

import com.nhom2.asmsof3021.model.Brand;
import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.repository.BrandRepository;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.ProductLineRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalInterceptor implements HandlerInterceptor {
    private final CategoryRepo categoryRepo;
    private final BrandRepository brandRepository;
    private final ProductLineRepo productLineRepo;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri=request.getRequestURI();
        if(!uri.contains("admin")&&!uri.contains("api")){
            List<Category> categories=categoryRepo.findAll();

            request.setAttribute("categories",categories);

        }


    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
