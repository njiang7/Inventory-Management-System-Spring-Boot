package com.example.IMS.convertor;

import com.example.IMS.Utilities.Helper;
import com.example.IMS.dto.ItemIssuanceDto;
import com.example.IMS.model.Borrower;
import com.example.IMS.model.Item;
import com.example.IMS.model.Loan;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemIssuanceConvertorTest {

    private ItemIssuanceConvertor itemIssuanceConvertor = new ItemIssuanceConvertor();

    // convert loan model to dto
    @Test
    void modelToDtoSuccess() {
        long id = 1;
        long loanDuration = 1;
        String issueDate = "01-01-2000";
        String returnDate = "01-15-2000";
        double totalFine = 0;
        long itemId = 2;
        Item item = new Item();
        item.setId(itemId);
        Borrower borrower = new Borrower();
        long borrowerId = 3;
        borrower.setId(borrowerId);

        Loan loan = new Loan();
        loan.setId(id);
        loan.setLoanDuration(loanDuration);
        loan.setIssueDate(issueDate);
        loan.setReturnDate(returnDate);
        loan.setTotalFine(totalFine);
        loan.setItem(item);
        loan.setBorrower(borrower);

        ItemIssuanceDto itemIssuanceDto = itemIssuanceConvertor.modelToDto(loan);

        assertEquals(id, itemIssuanceDto.getId());
        assertEquals(loanDuration, itemIssuanceDto.getLoanDuration());
        assertEquals(issueDate, itemIssuanceDto.getIssueDate());
        assertEquals(returnDate, itemIssuanceDto.getReturnDate());
        assertEquals(totalFine, itemIssuanceDto.getFineAmount());
        assertEquals(itemId, itemIssuanceDto.getItemId());
        assertEquals(borrowerId, itemIssuanceDto.getBorrowerId());

    }

    // convert model with uninitialized fields fail
    @Test
    void modelToDtoFail() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);
        System.setOut(output);

        Loan loan = new Loan();
        ItemIssuanceDto itemIssuanceDto = itemIssuanceConvertor.modelToDto(loan);

        String result = out.toString();
        assertTrue(result.contains("Null Pointer Exception Caught in Item Issuance Convertor."));


    }

    @Test
    void dtoToModelSuccess() {
        long id = 1;
        long itemId = 2;
        long borrowerId = 3;
        long loanDuration = 1;
        double totalFine = 0;
        ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto(id, itemId, borrowerId, loanDuration, totalFine);


        Loan loan = itemIssuanceConvertor.dtoToModel(itemIssuanceDto);

        assertEquals(loanDuration, loan.getLoanDuration());
        assertEquals(Helper.getCurrentTime(), loan.getIssueDate());
        assertEquals(totalFine, loan.getTotalFine());
        assertEquals("", loan.getReturnDate());

    }

    @Test
    void arrayModelToDtoSuccess() {
        long id = 1;
        long loanDuration = 1;
        String issueDate = "01-01-2000";
        String returnDate = "01-15-2000";
        double totalFine = 0;
        long itemId = 2;
        Item item = new Item();
        item.setId(itemId);
        Borrower borrower = new Borrower();
        long borrowerId = 3;
        borrower.setId(borrowerId);

        Loan loan = new Loan();
        loan.setId(id);
        loan.setLoanDuration(loanDuration);
        loan.setIssueDate(issueDate);
        loan.setReturnDate(returnDate);
        loan.setTotalFine(totalFine);
        loan.setItem(item);
        loan.setBorrower(borrower);

        List<Loan> loanList = List.of(loan);
        List<ItemIssuanceDto> itemIssuanceDtoList = itemIssuanceConvertor.modelToDto(loanList);

        assertEquals(itemIssuanceDtoList.size(), 1);

        ItemIssuanceDto itemIssuanceDto = itemIssuanceDtoList.get(0);

        assertEquals(id, itemIssuanceDto.getId());
        assertEquals(loanDuration, itemIssuanceDto.getLoanDuration());
        assertEquals(issueDate, itemIssuanceDto.getIssueDate());
        assertEquals(returnDate, itemIssuanceDto.getReturnDate());
        assertEquals(totalFine, itemIssuanceDto.getFineAmount());
        assertEquals(itemId, itemIssuanceDto.getItemId());
        assertEquals(borrowerId, itemIssuanceDto.getBorrowerId());
    }


    @Test
    void arrayModelToDtoFail() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);
        System.setOut(output);

        Loan loan = new Loan();
        List<Loan> loanList = List.of(loan);
        List<ItemIssuanceDto> itemIssuanceDtoList = itemIssuanceConvertor.modelToDto(loanList);

        String result = out.toString();
        assertTrue(result.contains("Null Pointer Exception Caught in Item Issuance Convertor."));
    }

}