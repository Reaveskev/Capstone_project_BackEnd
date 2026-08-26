package com.capstone.backend.controller;

import com.capstone.backend.dto.CustomerRequest;
import com.capstone.backend.entity.Customer;
import com.capstone.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        return customerRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest request) {
        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("A customer with this email already exists");
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setRewardPointsBalance(
                request.getRewardPointsBalance() != null ? request.getRewardPointsBalance() : 0
        );

        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody CustomerRequest request) {
        return customerRepository.findById(id)
                .<ResponseEntity<?>>map(customer -> {
                    customer.setName(request.getName());
                    customer.setEmail(request.getEmail());
                    customer.setPhone(request.getPhone());
                    if (request.getRewardPointsBalance() != null) {
                        customer.setRewardPointsBalance(request.getRewardPointsBalance());
                    }
                    return ResponseEntity.ok(customerRepository.save(customer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}