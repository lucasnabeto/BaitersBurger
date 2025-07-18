package br.com.fiap.baitersburger.application.usecase.customer;

import br.com.fiap.baitersburger.domain.model.Customer;
import br.com.fiap.baitersburger.domain.port.in.usecase.customer.InsertCustomerUseCase;
import br.com.fiap.baitersburger.domain.port.out.gateway.CustomerGateway;
import br.com.fiap.baitersburger.domain.port.out.repository.CustomerDataSource;
import br.com.fiap.baitersburger.domain.exception.CustomerAlreadyExistsException;

public class InsertCustomerUseCaseImpl implements InsertCustomerUseCase {

    private final CustomerGateway customerGateway;

    public InsertCustomerUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public void insert(Customer newCustomer) {
       var customer = customerGateway.findByCpf(newCustomer.getCpf());

       if (customer.isPresent()) {
           throw new CustomerAlreadyExistsException("Customer already exists");
       }

       var customerByEmail = customerGateway.findByEmail(newCustomer.getEmail());

       if (customerByEmail.isPresent()) {
           throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }

        customerGateway.insert(newCustomer);
    }
}
