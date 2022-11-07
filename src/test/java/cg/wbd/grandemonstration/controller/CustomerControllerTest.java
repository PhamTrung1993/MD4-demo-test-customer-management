package cg.wbd.grandemonstration.controller;

import cg.wbd.grandemonstration.model.Customer;
import cg.wbd.grandemonstration.service.CustomerService;
import cg.wbd.grandemonstration.service.ProvinceService;
import cg.wbd.grandemonstration.service.impl.CustomerServiceImplWithSpringData;
import cg.wbd.grandemonstration.service.impl.ProvinceServiceImplWithSpringData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebAppConfiguration
@SpringJUnitJupiterConfig(CustomerControllerTestConfig.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;

    private final ProvinceService provinceService = Mockito.mock(ProvinceServiceImplWithSpringData.class);

    private final CustomerService customerService = Mockito.mock(CustomerServiceImplWithSpringData.class);
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void customersListPageIsExists() throws Exception {
        mockMvc
                .perform(get("/customers"))
                //Có thể thay is(200) bằng isOk():
                .andExpect(status().is(200))
                .andExpect(view().name("customers/browse"));
    }
    @Test
    void showInformationSuccessControlling() throws Exception {
        Customer customer = new Customer(1L, "Foo Bar", "a@dummy.im", "Nowhere");
        given(customerService.findOne(1L)).willReturn(Optional.of(customer));
        mockMvc
                .perform(get("/customers/1"))
                .andExpect(status().is(200))
                .andExpect(view().name("customers/info"));
    }
}
