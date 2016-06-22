package br.com.challenge2.service;

import br.com.challenge2.infrastructure.gateway.ChallengeGateway;
import br.com.challenge2.infrastructure.repository.AddressRepository;
import br.com.challenge2.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class responsible for all business rules related to the {@link Address} domain
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ChallengeGateway challengeGateway;

    public Address save(Address address) {
        challengeGateway.validateCep(address.getCep());

        Address persistedAddres = addressRepository.save(address);
        return persistedAddres;
    }

    public Address find(String id) {
        return addressRepository.findOne(id);
    }

    public void delete(String id) {
        addressRepository.delete(id);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

}
