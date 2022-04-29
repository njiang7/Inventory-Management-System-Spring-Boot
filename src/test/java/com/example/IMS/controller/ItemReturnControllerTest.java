package com.example.IMS.controller;

import com.example.IMS.convertor.ItemIssuanceConvertor;
import com.example.IMS.dto.ItemIssuanceDto;
import com.example.IMS.dto.ItemReturnDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemReturnController.class)
class ItemReturnControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemIssuanceService itemIssuanceService;

    @MockBean
    private ItemIssuanceConvertor itemIssuanceConvertor;

    @Test
    void indexGet() throws Exception {
        List<Loan> loanList = new ArrayList<>();
        when(itemIssuanceService.getAllReturnedItem())
                .thenReturn(loanList);

        List<ItemIssuanceDto> itemIssuanceDtoList = new ArrayList<>();
        when(itemIssuanceConvertor.modelToDto(loanList))
                .thenReturn(itemIssuanceDtoList);

        mvc.perform(get("/ItemReturnView"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ItemIssuanceDtoList", itemIssuanceDtoList));
    }

    @Test
    void createGet() throws Exception {
        mvc.perform(get("/ItemReturnCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemReturnDto", notNullValue()));
    }


    // fails: Null Pointer Exception Caught in Item Return Controller.
    @Test
    void createPost() throws Exception {
        long itemId = 1;
        long borrowerId = 2;
        long loanId = 3;

        ItemReturnDto itemReturnDto = new ItemReturnDto(itemId,borrowerId,"date2");
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setLoanDuration(10);
        loan.setIssueDate("date1");
        loan.setReturnDate("date2");
        loan.setTotalFine(0);

        String err1 = "";
        when(borrowerService.validateBorrowerId(borrowerId))
                .thenReturn(err1);

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        borrower.addLoan(loan);
        loan.setBorrower(borrower);
        when(borrowerService.getBorrowerById(borrowerId))
                .thenReturn(borrower);

        String err2 = "";
        when(itemService.validateItemId(itemId))
                .thenReturn(err2);

        Item item = new Item();
        item.setId(itemId);
        item.setQuantity(1);
        item.addLoan(loan);
        loan.setItem(item);
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        when(itemIssuanceService.findItemIssued(borrower.getId(), item.getId()))
                .thenReturn(loan);

        mvc.perform(post("/ItemReturnCreate").flashAttr("itemReturnDto", itemReturnDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemReturnView"));

    }

    // case when error is not empty
    @Test
    void createPost2() throws Exception {
        long itemId = 1;
        long borrowerId = 2;
        long loanId = 3;

        ItemReturnDto itemReturnDto = new ItemReturnDto(itemId,borrowerId,"date2");
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setLoanDuration(10);
        loan.setIssueDate("date1");
        loan.setReturnDate("date2");
        loan.setTotalFine(0);

        String err1 = "err";
        when(borrowerService.validateBorrowerId(borrowerId))
                .thenReturn(err1);

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        borrower.addLoan(loan);
        loan.setBorrower(borrower);
        when(borrowerService.getBorrowerById(borrowerId))
                .thenReturn(borrower);

        String err2 = "err";
        when(itemService.validateItemId(itemId))
                .thenReturn(err2);

        Item item = new Item();
        item.setId(itemId);
        item.setQuantity(1);
        item.addLoan(loan);
        loan.setItem(item);
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        when(itemIssuanceService.findItemIssued(borrower.getId(), item.getId()))
                .thenReturn(loan);

        mvc.perform(post("/ItemReturnCreate").flashAttr("itemReturnDto", itemReturnDto))
                .andExpect(status().isOk());

    }
}