package br.com.challenge2.service;

import br.com.challenge2.infrastructure.exception.InvalidCepChallengeGatewayException;
import br.com.challenge2.infrastructure.gateway.ChallengeGateway;
import br.com.challenge2.infrastructure.repository.AddressRepository;
import br.com.challenge2.model.Address;
import br.com.challenge2.model.AddressBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

    public static final String ADDRESS_WITH_AN_INVALID_CEP_ID = "addressWithAnInvalidCepId";
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ChallengeGateway challengeGateway;
    @InjectMocks
    private AddressService addressService;

    private static final String VALID_ADDRESS_ID = "validAddress";
    private static final String VALID_CEP = "12345678";
    private static final String INVALID_CEP = "46587848";

    private Address validAddress;
    private Address addressWithAnInvalidCep;

    @Before
    public void setUp() throws Exception {
        AddressBuilder addressBuilder = new AddressBuilder();

        validAddress = addressBuilder.withId(VALID_ADDRESS_ID)
                .withState("State 1")
                .withNumber("467913")
                .withStreet("Street 1")
                .withCity("City 1")
                .withCep(VALID_CEP)
                .withComplement("Complement 1")
                .withNeighborhood("Neighborhood 1")
                .build();

        addressWithAnInvalidCep = addressBuilder.withId(ADDRESS_WITH_AN_INVALID_CEP_ID)
                .withState("State 1")
                .withNumber("467913")
                .withStreet("Street 1")
                .withCity("City 1")
                .withCep(INVALID_CEP)
                .withComplement("Complement 1")
                .withNeighborhood("Neighborhood 1")
                .build();

        when(addressService.save(validAddress)).thenReturn(validAddress);
        when(addressService.find(VALID_ADDRESS_ID)).thenReturn(validAddress);

        when(challengeGateway.validateCep(VALID_CEP)).thenReturn(true);
        when(challengeGateway.validateCep(INVALID_CEP)).thenThrow(InvalidCepChallengeGatewayException.class);
    }

    @Test
    public void shouldSaveAValidAddress() throws Exception {
        Address persistedAddress = addressService.save(validAddress);

        assertThat(persistedAddress, is(validAddress));
    }

    @Test(expected = InvalidCepChallengeGatewayException.class)
    public void shouldNovSaveAnAddressWithAnInvalidCep() {
        addressService.save(addressWithAnInvalidCep);
    }

    @Test
    public void shouldFindAnAddress() {
        Address foundAddress = addressService.find(VALID_ADDRESS_ID);

        assertThat(foundAddress, is(validAddress));
    }

    // Just to assert that none exception is thrown
    @Test
    public void shouldDeleteAnAddress() {
        addressService.delete(VALID_ADDRESS_ID);
    }

    // Just to assert that none exception is thrown
    @Test
    public void shouldUpdateAnAddress() {
        addressService.update(validAddress);
    }


}