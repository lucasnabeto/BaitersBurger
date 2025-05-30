package br.com.fiap.baitersburger.adapters.in.controller;

import br.com.fiap.baitersburger.adapters.in.controller.dto.customer.CustomerRequestDTO;
import br.com.fiap.baitersburger.adapters.in.controller.dto.customer.CustomerResponseDTO;
import br.com.fiap.baitersburger.adapters.in.controller.mapper.CustomerMapper;
import br.com.fiap.baitersburger.core.domain.model.Customer;
import br.com.fiap.baitersburger.core.application.ports.in.customer.FindCustomerByCpfInputPort;
import br.com.fiap.baitersburger.core.application.ports.in.customer.InsertCustomerInputPort;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerMapper customerMapper;
    private final InsertCustomerInputPort insertCustomerInputPort;
    private final FindCustomerByCpfInputPort findCustomerByCpfInputPort;

    public CustomerController(CustomerMapper customerMapper, InsertCustomerInputPort insertCustomerInputPort, FindCustomerByCpfInputPort findCustomerByCpfInputPort) {
        this.customerMapper = customerMapper;
        this.insertCustomerInputPort = insertCustomerInputPort;
        this.findCustomerByCpfInputPort = findCustomerByCpfInputPort;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerResponseDTO> find(@PathVariable final String cpf) {
        var customer = findCustomerByCpfInputPort.find(cpf);
        var customerResponseDTO = customerMapper.toCustomerResponseDTO(customer);
        return ResponseEntity.ok().body(customerResponseDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        var customer = customerMapper.toCustomer(customerRequestDTO);
        insertCustomerInputPort.insert(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
