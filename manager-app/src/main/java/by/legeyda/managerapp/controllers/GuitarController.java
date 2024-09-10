package by.legeyda.managerapp.controllers;

import by.legeyda.managerapp.client.BadRequestException;
import by.legeyda.managerapp.client.GuitarsRestClient;
import by.legeyda.managerapp.controllers.payload.UpdateGuitarPayload;
import by.legeyda.managerapp.entity.Guitar;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(("catalogue/products/guitars/{guitarId:\\d+}"))
@RequiredArgsConstructor
public class GuitarController {

    private final GuitarsRestClient guitarsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("guitar")
    public Guitar guitar(@PathVariable("guitarId") int guitarId) {
        return this.guitarsRestClient.findGuitarById(guitarId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping
    public String getGuitar() {
        return "catalogue/products/guitars/guitar";
    }

    @GetMapping("edit")
    public String getGuitarEditPage() {
        return "catalogue/products/guitars/edit";
    }

    @PostMapping("edit")
    public String updateGuitar(@ModelAttribute(name = "guitar", binding = false) Guitar guitar,
                               UpdateGuitarPayload payload,
                               Model model) {
        try {
            this.guitarsRestClient.updateGuitar(guitar.id(), payload.manufacturer(), payload.model(), payload.price(), payload.description());
            return "redirect:/catalogue/products/guitars/%d".formatted(guitar.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/guitars/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Guitar guitar) {
        this.guitarsRestClient.deleteGuitar(guitar.id());
        return "redirect:/catalogue/products/guitars/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}