package com.thymeleaf.starter.config;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.spring5.view.ThymeleafView;

public class LoggingThymeleafView extends ThymeleafView {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        super.render(model, request, response);
        System.out.println("Thymeleaf rendered " + request.getRequestURI() + " in " + (System.currentTimeMillis() - start) + " ms");
    }
}