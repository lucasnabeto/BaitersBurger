package br.com.fiap.baitersburger.adapters.out.mongodb.adapter.customer;

import br.com.fiap.baitersburger.adapters.in.controller.dto.customer.CustomerResponseDTO;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.CustomerRepository;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.entity.CustomerEntity;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.mappers.CustomerEntityMapper;
import br.com.fiap.baitersburger.core.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InsertCustomerAdapterTest {

    @InjectMocks
    private InsertCustomerAdapter adapter;

    @Mock
    private CustomerEntityMapper mapper;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void insertTest() {
        var customer = new Customer("1","01234567890","Daniel","teste@baitersburger.com.br");
        var customerEntity = new CustomerEntity();
        when(mapper.toCustomerEntity(customer)).thenReturn(customerEntity);

        adapter.insert(customer);

        verify(mapper).toCustomerEntity(customer);
        verify(repository).insert(customerEntity);
    }


}