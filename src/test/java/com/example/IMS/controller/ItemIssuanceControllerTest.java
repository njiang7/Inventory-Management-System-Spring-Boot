package com.example.IMS.controller;

import com.example.IMS.convertor.ItemIssuanceConvertor;
import com.example.IMS.dto.ItemIssuanceDto;
import com.example.IMS.model.Borrower;
import com.example.IMS.model.Item;
import com.example.IMS.model.Loan;
import com.example.IMS.service.BorrowerService;
import com.example.IMS.service.ItemIssuanceService;
import com.example.IMS.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ItemIssuanceController.class)
class ItemIssuanceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemIssuanceService itemIssuanceService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private ItemIssuanceConvertor itemIssuanceConvertor;

    @Test
    void getView() throws Exception {
        List<Loan> loanList= new ArrayList<>();
        when(itemIssuanceService.getAllIssuedItems())
                .thenReturn(loanList);

        List<ItemIssuanceDto> itemIssuanceDtoList = new ArrayList<>();
        when(itemIssuanceConvertor.modelToDto(loanList))
                .thenReturn(itemIssuanceDtoList);

        mvc.perform(get("/ItemIssuanceView"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ItemIssuanceDtoList", itemIssuanceDtoList));
    }

    @Test
    void getCreate() throws Exception {
        mvc.perform(get("/ItemIssuanceCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemIssuanceDto", notNullValue()));
    }

    @Test
    void postCreate() throws Exception {
        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1, 2, 3, 4);

        String err1 = "";
        when(borrowerService.validateBorrowerId(3))
                .thenReturn(err1);

        Borrower borrower = new Borrower();
        borrower.setId(3);
        when(borrowerService.getBorrowerById(3))
                .thenReturn(borrower);

        String err2 = "";
        when(itemService.validateItemId(2))
                .thenReturn(err2);

        Item item = new Item();
        item.setId(2);
        item.setQuantity(1); // set to 0 to test out of stock
        when(itemService.getItemById(2))
                .thenReturn(item);

        when(itemIssuanceConvertor.dtoToModel(itemIssuanceDto))
                .thenReturn(new Loan());

        mvc.perform(post("/ItemIssuanceCreate").flashAttr("itemIssuanceDto", itemIssuanceDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemIssuanceView"));
    }

    @Test
    void postCreateOutOfStock() throws Exception {
        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1, 2, 3, 4);

        String err1 = "";
        when(borrowerService.validateBorrowerId(3))
                .thenReturn(err1);

        Borrower borrower = new Borrower();
        borrower.setId(3);
        when(borrowerService.getBorrowerById(3))
                .thenReturn(borrower);

        String err2 = "";
        when(itemService.validateItemId(2))
                .thenReturn(err2);

        Item item = new Item();
        item.setId(2);
        item.setQuantity(0);
        when(itemService.getItemById(2))
                .thenReturn(item);

        // don't redirect if out of stock
        mvc.perform(post("/ItemIssuanceCreate").flashAttr("itemIssuanceDto", itemIssuanceDto))
                .andExpect(status().isOk());
    }

    @Test
    void postCreateValidateError() throws Exception {
        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1, 2, 3, 4);

        String err1 = "error";
        when(borrowerService.validateBorrowerId(3))
                .thenReturn(err1);

        Borrower borrower = new Borrower();
        borrower.setId(3);
        when(borrowerService.getBorrowerById(3))
                .thenReturn(borrower);

        String err2 = "error";
        when(itemService.validateItemId(2))
                .thenReturn(err2);

        Item item = new Item();
        item.setId(2);
        item.setQuantity(0);
        when(itemService.getItemById(2))
                .thenReturn(item);

        // don't redirect if validate fails
        mvc.perform(post("/ItemIssuanceCreate").flashAttr("itemIssuanceDto", itemIssuanceDto))
                .andExpect(status().isOk());
    }

    @Test
    void getEdit() throws Exception {
        long loanId = 1;
        Loan loan = new Loan();
        loan.setId(loanId);

        when(itemIssuanceService.findItemIssuedById(loanId))
                .thenReturn(loan);

        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1,2,3,4);
        when(itemIssuanceConvertor.modelToDto(loan))
                .thenReturn(itemIssuanceDto);

        mvc.perform(get("/ItemIssuanceEdit/" + loanId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemIssuanceDto", itemIssuanceDto));
    }

    @Test
    void getDelete() throws Exception {
        long loanId = 1;
        Loan loan = new Loan();
        loan.setId(loanId);

        when(itemIssuanceService.findItemIssuedById(loanId))
                .thenReturn(loan);

        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1,2,3,4);
        when(itemIssuanceConvertor.modelToDto(loan))
                .thenReturn(itemIssuanceDto);

        mvc.perform(get("/ItemIssuanceDelete/" + loanId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemIssuanceDto", itemIssuanceDto));
    }

    @Test
    void postDelete() throws Exception {
        long loanId = 1;
        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(1,2,3,4);
        mvc.perform(post("/ItemIssuanceDelete/" + loanId).flashAttr("itemIssuanceDto", itemIssuanceDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemIssuanceView"));
    }
}