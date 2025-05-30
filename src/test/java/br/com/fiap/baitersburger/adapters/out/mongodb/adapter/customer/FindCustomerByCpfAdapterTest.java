package br.com.fiap.baitersburger.adapters.out.mongodb.adapter.customer;

import br.com.fiap.baitersburger.adapters.out.mongodb.repository.CustomerRepository;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.entity.CustomerEntity;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.mappers.CustomerEntityMapper;
import br.com.fiap.baitersburger.core.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindCustomerByCpfAdapterTest {

    @InjectMocks
    private FindCustomerByCpfAdapter adapter;

    @Mock
    private CustomerEntityMapper customerEntityMapper;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnsCustomerWhenCpfExistsInRepositoryTest() {
        var customerEntity = new CustomerEntity();
        var customer = new Customer("1", "12345678909", "Test User", "teste@baitersburger.com.br");
        when(customerRepository.findByCpf("12345678909")).thenReturn(Optional.of(customerEntity));
        when(customerEntityMapper.toCustomer(customerEntity)).thenReturn(customer);

        var result = adapter.find("12345678909");

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void returnsEmptyOptionalWhenCpfDoesNotExistInRepositoryTest() {
        when(customerRepository.findByCpf("99999999999")).thenReturn(Optional.empty());

        var result = adapter.find("99999999999");

        assertFalse(result.isPresent());
    }

    @Test
    void returnsCustomerWhenEmailExistsInRepositoryTest() {
        var customerEntity = new CustomerEntity();
        var customer = new Customer("1", "12345678909", "Test User", "teste@baitersburger.com.br");
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customerEntity));
        when(customerEntityMapper.toCustomer(customerEntity)).thenReturn(customer);

        var result = adapter.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void returnsEmptyOptionalWhenEmailDoesNotExistInRepositoryTest() {
        when(customerRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        var result = adapter.findByEmail("notfound@example.com");

        assertFalse(result.isPresent());
    }

}