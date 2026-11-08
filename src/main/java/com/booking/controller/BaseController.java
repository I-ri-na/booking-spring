package com.booking.controller;

import com.booking.service.BusinessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class BaseController {

    protected static final String REDIRECT_HOME = "redirect:/";

    protected String execute(RedirectAttributes ra, BindingResult binding,
                             String successMessage, Runnable action) {
        if (binding != null && binding.hasErrors()) {
            ra.addFlashAttribute("error", "Некорректные данные формы (проверьте даты и поля)");
            return REDIRECT_HOME;
        }
        try {
            action.run();
            ra.addFlashAttribute("message", successMessage);
        } catch (BusinessException e) {
            ra.addFlashAttribute("error", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error",
                    "Данные нарушают ограничения базы (дубликат телефона/email или неверные даты)");
        }
        return REDIRECT_HOME;
    }
}
