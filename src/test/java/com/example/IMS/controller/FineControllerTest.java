package com.example.IMS.controller;

import com.example.IMS.controller.FineController;
import com.example.IMS.dto.FineDto;
import com.example.IMS.model.Borrower;
import com.example.IMS.model.Loan;
import com.example.IMS.service.BorrowerService;
import com.example.IMS.service.ItemIssuanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FineController.class)
public class FineControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private ItemIssuanceService itemIssuanceService;


    // using mock services to provide canned responses
    @Test
    public void testGetFineView() throws Exception {
        List<Loan> loanList = new ArrayList<>();

        when(itemIssuanceService.getItemsWithFine())
                .thenReturn(loanList);

        mvc.perform(get("/FineView"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemsWithFineList", equalTo(loanList)));

    }

    // expect an fineDto attribute to be created in the page, no it should not be null
    @Test
    public void testGetFineCreate() throws Exception {
        mvc.perform(get("/FineCreate"))
                .andExpect((status().isOk()))
                .andExpect(model().attribute("fineDto", notNullValue()));
    }

    // valid borrower id, expect redirect
    @Test
    public void testPostFinePayment() throws Exception {
        Borrower borrower = new Borrower();
        long borrowerId = 1;
        borrower.setId(borrowerId);
        String err = "";
        when(borrowerService.validateBorrowerId(borrowerId))
                .thenReturn(err);
        when(borrowerService.getBorrowerById(borrowerId))
                .thenReturn(borrower);


        mvc.perform(MockMvcRequestBuilders
                .post("/FinePayment/" + borrowerId)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/FineView"));

    }

    // invalid borrower id
    @Test
    public void testPostFinePaymentInvalid() throws Exception {
        long borrowerId = 1;
        String err = "error";
        when(borrowerService.validateBorrowerId(borrowerId))
                .thenReturn(err);

        mvc.perform(MockMvcRequestBuilders.post("/FinePayment/" + borrowerId))
                .andExpect(status().isOk());

    }

    @Test
    public void testPostFineDetails() throws Exception {
        Borrower borrower = new Borrower();
        long borrowerId = 1;
        borrower.setId(borrowerId);
        when(borrowerService.getBorrowerById(borrowerId))
                .thenReturn(borrower);

        Assertions.assertNotNull(borrower);

        FineDto fineDto = new FineDto(borrowerId);
        fineDto.setTotalFine(10);

        // since the borrower has no fines, this will set it to 0
        mvc.perform(MockMvcRequestBuilders
                .post("/FineDetails")
                        .flashAttr("fineDto", fineDto)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<dt>Total Fine</dt>\n" +
                        "\t\t\t\t\t\t<dd>0.0</dd>")));
    }
}
