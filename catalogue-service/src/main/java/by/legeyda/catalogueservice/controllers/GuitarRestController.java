package by.legeyda.catalogueservice.controllers;


import by.legeyda.catalogueservice.controllers.payload.UpdateGuitarPayload;
import by.legeyda.catalogueservice.entity.Guitar;
import by.legeyda.catalogueservice.services.GuitarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/catalogue-api/products/guitars/{guitarId:\\d+}")
public class GuitarRestController {
    private final GuitarService guitarService;
    private final MessageSource messageSource;


    @Autowired
    public GuitarRestController(GuitarService guitarService, MessageSource messageSource) {
        this.guitarService = guitarService;
        this.messageSource = messageSource;
    }

    @ModelAttribute("guitar")
    public Guitar getGuitar(@PathVariable("guitarId") int guitarId) {
        return guitarService.findGuitarById(guitarId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.guitar.not_found"));
    }


    @GetMapping
    public Guitar findGuitar(@ModelAttribute("guitar") Guitar guitar) {
        return guitar;
    }

    @PatchMapping
    public ResponseEntity<?> updateGuitar(@PathVariable("guitarId") int guitarId,
                                          @Valid @RequestBody UpdateGuitarPayload payload,
                                          BindingResult bindingResult,
                                          Locale locale) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            guitarService.updateGuitar(guitarId, payload.manufacturer(),
                    payload.model(),
                    payload.price(),
                    payload.description());
            return ResponseEntity.noContent().build();
        }
    }


    @DeleteMapping
    ResponseEntity<Void> deleteProduct(@PathVariable("guitarId") int guitarId) {
        guitarService.deleteGuitar(guitarId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale)));
    }
}
