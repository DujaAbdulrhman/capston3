package com.example.demo.Repository;

import com.example.demo.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor,Integer> {


    Investor getInvestoreById(Integer id);

    Investor findInvestorById(Integer investorId);
    boolean existsByEmail(String email);
}
