package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.entitiy.Customer;
import com.refanzzzz.tokonyadia.repository.CustomerRepository;
import com.refanzzzz.tokonyadia.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) return customerOptional.get();

        throw new RuntimeException("Customer tidak ada!");
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String id, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
//
//        if (customerOptional.isPresent()) {
//            Customer updateCustomer = customerOptional.get();
//            updateCustomer.setName(customer.getName());
//            updateCustomer.setEmail(customer.getEmail());
//            updateCustomer.setAddress(customer.getAddress());
//            updateCustomer.setPhoneNumber(customer.getPhoneNumber());
//
//            return customerRepository.save(updateCustomer);
//        }

        throw new RuntimeException("Customer tidak ada!");
    }

    @Override
    public String deleteCustomerById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
            return "Berhasil hapus customer dengan id: " + id;
        }

        throw new RuntimeException("Customer tidak ada!");
    }
}
