package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Data;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Data entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataRepository extends MongoRepository<Data, String> {
}
