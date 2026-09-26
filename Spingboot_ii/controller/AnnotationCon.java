package hy452.ws.spring_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hy452.ws.spring_client.TextParser;
import hy452.ws.spring_client.service.AnnotationService;

@Controller
public class AnnotationCon {
	
    private final 	AnnotationService service;
    private final TextParser parser;

    public AnnotationCon(AnnotationService service, TextParser parser) { /*the object parameters are created and injected by Spring using their beans (@Service ...) because it stores a singleton of these classes and keeps them in the application context*/
        this.service = service;
        this.parser = parser;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/DBpedia_annotate")
    public String annotate(@RequestParam String text, Model model) { /*The Model is request-scoped, meaning it exists only for the duration of a single HTTP request and is a simple data structure, specifically a map of key-value pairs, that the controller uses to pass data to the view (Thymeleaf template).*/
        try {
            String json = service.call_DBpedia(text);
            String annotated = parser.annotate_text(text, json);

            model.addAttribute("original", text);
            model.addAttribute("annotatedHtml", annotated);

        } catch (Exception e) {
            model.addAttribute("error", "API error: " + e.getMessage());
        }
        return "index";
    }

}
