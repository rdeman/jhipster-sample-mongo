package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PropertySet;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PropertySet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertySetRepository extends MongoRepository<PropertySet, String> {
}
