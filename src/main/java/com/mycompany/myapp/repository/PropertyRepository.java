package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Property;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
}
