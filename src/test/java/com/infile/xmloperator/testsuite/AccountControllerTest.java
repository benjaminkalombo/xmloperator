package com.infile.xmloperator.testsuite;
import com.infile.xmloperator.controller.AccountController;
import com.infile.xmloperator.domain.Account;
import com.infile.xmloperator.filters.Filter;
import com.infile.xmloperator.service.AccountService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .addFilters(new Filter())
                .build();
    }
    @Test
    public void test_get_all_success() throws Exception {
        List<Account> accounts = Arrays.asList(
                new Account("Saving","ABCD-02", 30, "Benjamin", "Kalombo", "johannesburg"));

        when(accountService.findAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type", is("Saving")))
                .andExpect(jsonPath("$[0].accountid", is("ABCD-02")))
                .andExpect(jsonPath("$[0].amount", is(30)))
                .andExpect(jsonPath("$[0].lastname", is("Kalombo")))
                .andExpect(jsonPath("$[0].demographic", is("johannesburg")))
                .andExpect(jsonPath("$[0].firstname", is("Benjamin")));

        verify(accountService, times(1)).findAllAccounts();
        verifyNoMoreInteractions(accountService);
    }

    // =========================================== Get Account By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
      Account account =   new Account("Saving","ABCD-02", 30, "Benjamin", "Kalombo", "johannesburg");


        when(accountService.findAccountById(1L)).thenReturn(account);

        mockMvc.perform(get("/account/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1L)))
                .andExpect(jsonPath("$[0].type", is("Saving")))
                .andExpect(jsonPath("$[0].accountid", is("ABCD-02")))
                .andExpect(jsonPath("$[0].amount", is(30)))
                .andExpect(jsonPath("$[0].lastname", is("Kalombo")))
                .andExpect(jsonPath("$[0].demographic", is("johannesburg")))
                .andExpect(jsonPath("$[0].firstname", is("Benjamin")));


        verify(accountService, times(1)).findAccountById(1L);
        verifyNoMoreInteractions(accountService);
    }
    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(accountService.findAccountById(1L)).thenReturn(null);

        mockMvc.perform(get("/account/{id}", 1))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).findAccountById(1L);
        verifyNoMoreInteractions(accountService);
    }
    // =========================================== Filter Headers ===========================================
    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


