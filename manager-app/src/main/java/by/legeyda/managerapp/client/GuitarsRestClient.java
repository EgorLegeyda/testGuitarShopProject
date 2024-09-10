package by.legeyda.managerapp.client;

import by.legeyda.managerapp.entity.Guitar;

import java.util.List;
import java.util.Optional;

public interface GuitarsRestClient {

    List<Guitar> findAll();
    Guitar createGuitar(String manufacturer, String model, int price, String description);
    Optional<Guitar> findGuitarById(int guitarId);
    void updateGuitar(int guitarId, String manufacturer, String model, int price, String description);
    void deleteGuitar(int guitarId);

    }
