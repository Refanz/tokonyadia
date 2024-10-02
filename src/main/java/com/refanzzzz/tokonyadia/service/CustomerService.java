package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entitiy.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomer();

    Customer getCustomerById(String id);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(String id, Customer customer);

    String deleteCustomerById(String id);
}
