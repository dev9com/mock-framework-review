package com.dev9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Justin Graham
 * @since 6/30/17
 */
@Controller
@RequestMapping("/")
public class SwaggerController {

    @GetMapping
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui.html");
    }
}
