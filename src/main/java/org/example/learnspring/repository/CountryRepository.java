package org.example.learnspring.repository;

import org.example.learnspring.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
    
}
