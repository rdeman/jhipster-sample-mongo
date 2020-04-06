package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Classification;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Classification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificationRepository extends MongoRepository<Classification, String> {
}
