package com.example.demo.Repository;

import com.example.demo.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository <Owner,Integer> {
    Owner findOwnerById(Integer id);

    boolean existsByEmail(String email);
}
