package cg.wbd.grandemonstration.service;

import cg.wbd.grandemonstration.model.Customer;
import cg.wbd.grandemonstration.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(CustomerServiceTestConfig.class)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    //Do service sẽ triệu gọi repository nên sẽ cần mock repository.
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void resetMocks() {
        Mockito.reset(customerRepository);
    }
    @Test
    void testFindAll() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "Foo Bar", "a@dummy.im", "Nowhere"));
        Pageable pageInfo = PageRequest.of(0, 25);
        Page<Customer> customerPage = new PageImpl<Customer>(customers, pageInfo, 1);
        when(customerRepository.findAll(pageInfo)).thenReturn(customerPage);

        Page<Customer> actual = customerService.findAll(pageInfo);
        verify(customerRepository).findAll(pageInfo);
        assertEquals(customerPage, actual);
    }
    @Test
    void testFindByIdNotFound() {
        given(customerRepository.findById(1L)).willReturn(Optional.empty());

        Optional<Customer> actual = customerService.findOne(1L);
        verify(customerRepository).findById(1L);
        assertEquals(Optional.empty(), actual);
    }
    @Test
    void saveCustomer() {
        Customer customer = new Customer(1L, "Foo Bar", "a@dummy.im", "Nowhere");
        customerService.save(customer);
        verify(customerRepository).save(customer);
    }
}
