package br.com.fiap.baitersburger.adapters.in.controller;

import br.com.fiap.baitersburger.adapters.in.controller.dto.customer.CustomerRequestDTO;
import br.com.fiap.baitersburger.adapters.in.controller.dto.customer.CustomerResponseDTO;
import br.com.fiap.baitersburger.adapters.in.controller.mapper.CustomerMapper;
import br.com.fiap.baitersburger.core.application.ports.in.customer.FindCustomerByCpfInputPort;
import br.com.fiap.baitersburger.core.application.ports.in.customer.InsertCustomerInputPort;
import br.com.fiap.baitersburger.core.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.ap.internal.model.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    CustomerMapper customerMapper;

    @Mock
    InsertCustomerInputPort insertCustomerInputPort;

    @Mock
    FindCustomerByCpfInputPort findCustomerByCpfInputPort;

    private final String CPF = "920.535.270-56";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.customerMapper = Mappers.getMapper(CustomerMapper.class);
        customerController = new CustomerController(customerMapper,insertCustomerInputPort,findCustomerByCpfInputPort);
    }



    @Test
    void findTest() {
        Customer customer = new Customer("1",CPF, "Daniel","teste@baitersburger.com.br");

        when(findCustomerByCpfInputPort.find(CPF)).thenReturn(customer);

        ResponseEntity<CustomerResponseDTO> response = customerController.find(CPF);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer.getCpf(), response.getBody().getCpf());
        assertEquals(customer.getEmail(), response.getBody().getEmail());
        assertEquals(customer.getName(), response.getBody().getName());
    }

    @Test
    void insert() {
        var response = customerController.insert(new CustomerRequestDTO());

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }
}