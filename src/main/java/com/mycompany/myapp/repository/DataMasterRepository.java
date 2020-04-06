package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DataMaster;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DataMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataMasterRepository extends MongoRepository<DataMaster, String> {
}
