package by.legeyda.catalogueservice.controllers;


import by.legeyda.catalogueservice.controllers.payload.NewGuitarPayload;
import by.legeyda.catalogueservice.entity.Guitar;
import by.legeyda.catalogueservice.services.GuitarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/catalogue-api/products/guitars")
public class GuitarsRestController {
    private final GuitarService guitarService;

    @Autowired
    public GuitarsRestController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    @GetMapping
    public List<Guitar> findGuitars() {
        return guitarService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createGuitar(@Valid @RequestBody NewGuitarPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder)
            throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Guitar guitar = guitarService.createGuitar(payload.manufacturer(),
                    payload.model(),
                    payload.price(),
                    payload.description());
            return ResponseEntity.created(uriComponentsBuilder
                            .replacePath("/catalogue-api/products/guitars/{guitarId}")
                            .build(Map.of("guitarId", guitar.getId())))
                    .body(guitar);
        }
    }
}
