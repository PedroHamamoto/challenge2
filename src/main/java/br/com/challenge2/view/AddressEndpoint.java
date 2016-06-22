package br.com.challenge2.view;

import br.com.challenge2.model.Address;
import br.com.challenge2.service.AddressService;
import br.com.challenge2.view.dto.Resource;
import br.com.challenge2.view.validator.AddressRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rs/address")
public class AddressEndpoint {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRequestValidator addressRequestValidator;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getAddress(@PathVariable String id, HttpServletRequest request) {
        addressRequestValidator.validate(id);

        Address address = addressService.find(id);

        Resource<Address> addressResource = new Resource<>(request.getRequestURI(), address);
        return new ResponseEntity(addressResource, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity postAddress(@RequestBody @Validated Address address, HttpServletRequest request) {
        Address persistedAddress = addressService.save(address);

        Resource<Address> addressResource = new Resource<>(request.getRequestURI(), persistedAddress);

        return new ResponseEntity(addressResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAddress(@PathVariable String id) {
        addressRequestValidator.validate(id);

        addressService.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity putAddress(@RequestBody @Validated Address address) {
        addressRequestValidator.validate(address.getId());

        addressService.update(address);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
