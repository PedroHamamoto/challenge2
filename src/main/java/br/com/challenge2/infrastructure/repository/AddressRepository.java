package br.com.challenge2.infrastructure.repository;

import br.com.challenge2.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Does all <strong>CRUD</strong> operations related to an {@link Address} MongoDb document
 */
@Repository
public interface AddressRepository extends MongoRepository<Address, String>{

}
