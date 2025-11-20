package com.music.album.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер домашней страницы
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/albums";
    }
}
