package br.com.challenge2.view.validator;

import br.com.challenge2.infrastructure.exception.AddressNotFoundException;
import br.com.challenge2.infrastructure.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validates the address on the view, before sending to the service layer
 */
@Component
public class AddressRequestValidator {

    @Autowired
    private AddressRepository addressRepository;

    public void validate(String id) {
        if (!addressRepository.exists(id)) {
            throw new AddressNotFoundException();
        }
    }

}
