package com.example.demo.Repository;

import com.example.demo.Model.Owner;
import com.example.demo.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {

    Property findPropertiesById(Integer id);
    List<Property> findPropertiesByLeaseEndDateBetween(LocalDate startDate, LocalDate endDate);
    List<Property> findPropertyByStatus(String status);

    List<Property> findByOwner(Owner owner);
}
