package com.booking.controller;

import com.booking.entity.Client;
import com.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/add/client")
    public String addClient(@ModelAttribute Client client, BindingResult binding,
                            RedirectAttributes ra) {
        return execute(ra, binding, "Клиент добавлен",
                () -> clientService.addClient(client));
    }

    @PostMapping("/edit/client/{id}")
    public String editClient(@PathVariable Integer id, @ModelAttribute Client client,
                             BindingResult binding, RedirectAttributes ra) {
        return execute(ra, binding, "Клиент #" + id + " изменён",
                () -> clientService.updateClient(id, client));
    }

    @PostMapping("/delete/client/{id}")
    public String deleteClient(@PathVariable Integer id, RedirectAttributes ra) {
        return execute(ra, null, "Клиент #" + id + " удалён",
                () -> clientService.deleteClient(id));
    }
}
