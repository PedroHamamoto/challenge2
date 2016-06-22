package br.com.challenge2.view;

import br.com.challenge2.ApplicationConfig;
import br.com.challenge2.infrastructure.repository.AddressRepository;
import br.com.challenge2.model.Address;
import br.com.challenge2.model.AddressBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
public class AddressEndpointIT {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    private static final String ADDRESS_RESOURCE_URI = "/rs/address";
    private static final String ADDRESS_URI = ADDRESS_RESOURCE_URI + "/{cep}";
    private static final String FORMAT_ADDRESS_URI = ADDRESS_RESOURCE_URI + "/%s";
    private static final String ADDRESS_WITHOUT_SOME_REQUIRED_FIELDS_ID = "addressWithoutSomeRequiredFields";

    private static final String EXISTING_ADDRESS_ID = "XPTO_1";
    private static final String NONEXISTENT_ADDRESS_ID = "NonexistentAddressID";
    private static final String NEW_ADDRESS_ID = "newAddressId";

    @Autowired
    private AddressRepository addressRepository;
    private Address existingAddress;
    private Address newAddress;
    private Address addressWithoutRequiredFields;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.
                webAppContextSetup(context)
                .build();

        AddressBuilder addressBuilder = new AddressBuilder();

        existingAddress = addressBuilder.withId(EXISTING_ADDRESS_ID)
                .withStreet("Street 1")
                .withCity("City 1")
                .withCep("03193120")
                .withComplement("Complement 1")
                .withNeighborhood("")
                .withNumber("49578")
                .withState("State 1")
                .build();

        newAddress = addressBuilder.withId(NEW_ADDRESS_ID)
                .withStreet("Street 2")
                .withCity("City 2")
                .withCep("12345678")
                .withComplement("")
                .withNeighborhood("Neighborhood 2")
                .withNumber("468")
                .withState("State 2")
                .build();

        addressWithoutRequiredFields = addressBuilder.withId(ADDRESS_WITHOUT_SOME_REQUIRED_FIELDS_ID)
                .withStreet("")
                .withCity("City 2")
                .withCep("12345678")
                .withComplement("Complement 2")
                .withNeighborhood("Neighborhood 2")
                .withNumber("")
                .withState("")
                .build();

        addressRepository.save(existingAddress);
    }

    @After
    public void tearDown() throws Exception {
        addressRepository.delete(EXISTING_ADDRESS_ID);

    }

    @Test
    public void shouldGetAnExistingAddress() throws Exception {
        mvc.perform(get(ADDRESS_URI, EXISTING_ADDRESS_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(String.format(FORMAT_ADDRESS_URI, EXISTING_ADDRESS_ID))))
                .andExpect(jsonPath("$.item.id", is(existingAddress.getId())))
                .andExpect(jsonPath("$.item.street", is(existingAddress.getStreet())))
                .andExpect(jsonPath("$.item.cep", is(existingAddress.getCep())))
                .andExpect(jsonPath("$.item.neighborhood", is(existingAddress.getNeighborhood())))
                .andExpect(jsonPath("$.item.number", is(existingAddress.getNumber())))
                .andExpect(jsonPath("$.item.state", is(existingAddress.getState())));
    }

    @Test
    public void shouldNotGetANonexistentAddress() throws Exception {
        mvc.perform(get(ADDRESS_URI, NONEXISTENT_ADDRESS_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(String.format(FORMAT_ADDRESS_URI, NONEXISTENT_ADDRESS_ID))))
                .andExpect(jsonPath("$.item.code", is("404")))
                .andExpect(jsonPath("$.item.message", is("Endereço não encontrado")));
    }

    @Test
    public void shouldDeleteAnExistingAddress() throws Exception {
        mvc.perform(delete(ADDRESS_URI, EXISTING_ADDRESS_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteANonexistentAddress() throws Exception {
        mvc.perform(delete(ADDRESS_URI, NONEXISTENT_ADDRESS_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(String.format(FORMAT_ADDRESS_URI, NONEXISTENT_ADDRESS_ID))))
                .andExpect(jsonPath("$.item.code", is("404")))
                .andExpect(jsonPath("$.item.message", is("Endereço não encontrado")));
    }

    @Test
    public void shouldPostANewAddress() throws Exception {
        mvc.perform(post(ADDRESS_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(newAddress))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(ADDRESS_RESOURCE_URI)))
                .andExpect(jsonPath("$.item.id", is(newAddress.getId())))
                .andExpect(jsonPath("$.item.street", is(newAddress.getStreet())))
                .andExpect(jsonPath("$.item.cep", is(newAddress.getCep())))
                .andExpect(jsonPath("$.item.number", is(newAddress.getNumber())))
                .andExpect(jsonPath("$.item.state", is(newAddress.getState())))
                .andExpect(jsonPath("$.item.city", is(newAddress.getCity())))
                .andExpect(jsonPath("$.item.neighborhood", is(newAddress.getNeighborhood())))
                .andExpect(jsonPath("$.item.complement", is(newAddress.getComplement())));

        addressRepository.delete(NEW_ADDRESS_ID);
    }

    @Test
    public void shouldNotPostAnAddressWithAnInvalidCep() throws Exception {
        newAddress.setCep("00000000");

        mvc.perform(post(ADDRESS_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(newAddress))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(ADDRESS_RESOURCE_URI)))
                .andExpect(jsonPath("$.item.code", is("400")))
                .andExpect(jsonPath("$.item.message", is("CEP inválido")));
    }

    @Test
    public void shouldNotPostAnAddressWithoutAnyRequiredFields() throws Exception {
        mvc.perform(post(ADDRESS_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(addressWithoutRequiredFields))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(ADDRESS_RESOURCE_URI)))
                .andExpect(jsonPath("$.item.code", is("400")))
                .andExpect(jsonPath("$.item.errors", hasSize(greaterThan(0))));
    }

    @Test
    public void shouldPutAExistentAddress() throws Exception {
        existingAddress.setCity("New City");

        mvc.perform(put(ADDRESS_URI, EXISTING_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(existingAddress))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotPutANonexistentAddress() throws Exception {
        existingAddress.setId(NONEXISTENT_ADDRESS_ID);

        mvc.perform(put(ADDRESS_URI, NONEXISTENT_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(existingAddress))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(String.format(FORMAT_ADDRESS_URI, NONEXISTENT_ADDRESS_ID))))
                .andExpect(jsonPath("$.item.code", is("404")))
                .andExpect(jsonPath("$.item.message", is("Endereço não encontrado")));
    }

    @Test
    public void shouldNotPutAnAddressWithoutAnyRequiredFields() throws Exception {
        existingAddress.setStreet("");
        existingAddress.setCity("");

        mvc.perform(put(ADDRESS_URI, EXISTING_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(addressToJSON(existingAddress))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uri", is(String.format(FORMAT_ADDRESS_URI, EXISTING_ADDRESS_ID))))
                .andExpect(jsonPath("$.item.code", is("400")))
                .andExpect(jsonPath("$.item.errors", hasSize(greaterThan(0))));
    }

    private byte[] addressToJSON(Address address) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(address);
    }

}
