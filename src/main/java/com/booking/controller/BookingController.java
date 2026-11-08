package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookingController extends BaseController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/add/booking")
    public String addBooking(@RequestParam(name = "clientId", required = false) Integer clientId,
                             @ModelAttribute Booking booking, BindingResult binding,
                             RedirectAttributes ra) {
        return execute(ra, binding, "Бронирование добавлено",
                () -> bookingService.addBooking(clientId, booking));
    }

    @PostMapping("/edit/booking/{id}")
    public String editBooking(@PathVariable Integer id, @ModelAttribute Booking booking,
                              BindingResult binding, RedirectAttributes ra) {
        return execute(ra, binding, "Бронирование #" + id + " изменено",
                () -> bookingService.updateBooking(id, booking));
    }

    @PostMapping("/delete/booking/{id}")
    public String deleteBooking(@PathVariable Integer id, RedirectAttributes ra) {
        return execute(ra, null, "Бронирование #" + id + " удалено",
                () -> bookingService.deleteBooking(id));
    }
}
