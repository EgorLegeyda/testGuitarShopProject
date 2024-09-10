package by.legeyda.catalogueservice.controllers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateGuitarPayload(
        @NotBlank
        String manufacturer,
        @NotBlank
        String model,

        @NotNull
        Integer price,

        @Size(min = 5, max = 1000, message = "{catalogue.errors.guitar.update.description_is_invalid}")
        String description) {}
