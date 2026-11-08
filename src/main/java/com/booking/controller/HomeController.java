package com.booking.controller;

import com.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "index";
    }
}
