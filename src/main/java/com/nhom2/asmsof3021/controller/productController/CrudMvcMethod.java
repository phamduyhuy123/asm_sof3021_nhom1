package com.nhom2.asmsof3021.controller.productController;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class CrudMvcMethod<T,K>{

    public abstract String create(T product, MultipartFile[] images,MultipartFile photoImage, RedirectAttributes redirectAttributes) ;
    public abstract String delete(K id, RedirectAttributes redirectAttributes);
    public abstract String update(T product, K id,MultipartFile[] images,MultipartFile photoImage, RedirectAttributes redirectAttributes);
}
