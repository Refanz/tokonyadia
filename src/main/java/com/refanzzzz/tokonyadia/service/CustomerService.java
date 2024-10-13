package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.dto.response.CustomerResponse;
import com.refanzzzz.tokonyadia.entity.Customer;

public interface CustomerService extends Service<CustomerResponse, CustomerRequest, Customer> {
}
