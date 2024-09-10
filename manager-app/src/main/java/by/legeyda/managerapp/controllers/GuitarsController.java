package by.legeyda.managerapp.controllers;


import by.legeyda.managerapp.client.BadRequestException;
import by.legeyda.managerapp.client.GuitarsRestClient;
import by.legeyda.managerapp.controllers.payload.NewGuitarPayload;
import by.legeyda.managerapp.entity.Guitar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/guitars")
public class GuitarsController {

    private final GuitarsRestClient guitarsRestClient;

    @GetMapping("list")
    public String getGuitarsList(Model model) {
        model.addAttribute("guitars", this.guitarsRestClient.findAll());
        return "catalogue/products/guitars/list";
    }

    @GetMapping("create")
    public String getNewGuitarPage() {
        return "catalogue/products/guitars/new_guitar";
    }

    @PostMapping("create")
    public String createGuitar(NewGuitarPayload payload,
                               Model model) {
        try {
            Guitar guitar = this.guitarsRestClient.createGuitar(payload.manufacturer(), payload.model(), payload.price(), payload.description());
            return "redirect:/catalogue/products/guitars/%d".formatted(guitar.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/guitars/new_guitar";
        }
    }
}