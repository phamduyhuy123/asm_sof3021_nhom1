package com.nhom2.asmsof3021.controller.productController;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public abstract class CrudMvcMethod<T,K>{

    public abstract String create(T product, MultipartFile[] file, MultipartFile fileName , RedirectAttributes redirectAttributes) ;
    public abstract String delete(K id, RedirectAttributes redirectAttributes);
    public abstract String update(T product, K id, MultipartFile[] file, MultipartFile fileName ,RedirectAttributes redirectAttributes);
}
