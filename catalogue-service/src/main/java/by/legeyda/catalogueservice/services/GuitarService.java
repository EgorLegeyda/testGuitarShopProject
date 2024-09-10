package by.legeyda.catalogueservice.services;


import by.legeyda.catalogueservice.entity.Guitar;
import by.legeyda.catalogueservice.repositories.GuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GuitarService {

    private final GuitarRepository guitarRepository;


    @Autowired
    public GuitarService(GuitarRepository guitarRepository) {
        this.guitarRepository = guitarRepository;
    }

    public List<Guitar> findAll() {
        List<Guitar> guitarList = guitarRepository.findAll(Sort.by("id"));
        return guitarList;
    }

    public Optional<Guitar> findGuitarById(int id) {
        return guitarRepository.findById(id);
    }


    @Transactional
    public Guitar createGuitar(String manufacturer,
                               String model,
                               Integer price,
                               String description) {
        return guitarRepository.save(new Guitar(null, manufacturer, model, price, description));
    }


    @Transactional
    public void updateGuitar(Integer id, String manufacturer, String model, Integer price, String description) {

        guitarRepository.findById(id).
                ifPresentOrElse(guitar -> {
                    guitar.setId(id);
                    guitar.setManufacturer(manufacturer);
                    guitar.setModel(model);
                    guitar.setPrice(price);
                    guitar.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }


    @Transactional
    public void deleteGuitar(Integer id) {
        Guitar guitar = findGuitarById(id).get();
        guitarRepository.delete(guitar);
    }
}
