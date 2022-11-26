package com.example.weathertest.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepo extends JpaRepository<Model, Long> {

}
