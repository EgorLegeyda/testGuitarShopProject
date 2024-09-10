package by.legeyda.catalogueservice.controllers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewGuitarPayload(
        @NotBlank
        String manufacturer,
        @NotBlank
        String model,

        @NotNull
        Integer price,

        //@Min(value = 5, message = "{catalogue.errors.guitar.create.description_is_invalid}")

        @Size(min = 5, max = 1000, message = "{catalogue.errors.guitar.create.description_is_invalid}")
        String description){

}
