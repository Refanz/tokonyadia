package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.dto.response.CustomerResponse;
import com.refanzzzz.tokonyadia.entity.Customer;
import com.refanzzzz.tokonyadia.repository.CustomerRepository;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.specification.CustomerSpecification;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Page<CustomerResponse> getAll(CustomerRequest request) {

        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Specification<Customer> customerSpecification = CustomerSpecification.getCustomerSpecification(request);

        Page<Customer> customerResponsePage = customerRepository.findAll(customerSpecification, pageable);

        return customerResponsePage.map(this::toCustomerResponse);
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = getOne(id);

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @Override
    public CustomerResponse create(CustomerRequest data) {
        Customer customer = Customer.builder()
                .name(data.getName())
                .email(data.getEmail())
                .phoneNumber(data.getPhoneNumber())
                .address(data.getAddress())
                .build();

        customerRepository.saveAndFlush(customer);
        return toCustomerResponse(customer);
    }

    @Override
    public void remove(String id) {
        Customer customer = getOne(id);
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse update(String id, CustomerRequest data) {
        Customer customer = getOne(id);

        customer.setName(data.getName());
        customer.setEmail(data.getEmail());
        customer.setAddress(data.getAddress());
        customer.setPhoneNumber(data.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);
        return toCustomerResponse(savedCustomer);
    }

    @Override
    public Customer getOne(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found!"));
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
