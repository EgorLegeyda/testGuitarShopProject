package by.legeyda.catalogueservice.repositories;


import by.legeyda.catalogueservice.entity.Guitar;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Integer> {
    @Override
    List<Guitar> findAll(Sort sort);


}
