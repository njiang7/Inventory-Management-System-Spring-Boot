package com.example.IMS.service;

import com.example.IMS.model.Borrower;
import com.example.IMS.model.Item;
import com.example.IMS.model.Loan;
import com.example.IMS.repository.IItemIssuanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemIssuanceServiceTest {

    @Autowired
    private ItemIssuanceService service;

    @MockBean
    private IItemIssuanceRepository itemIssuanceRepository;


    // return a list of items that have empty return dates
    @Test
    void getAllIssuedItems() {
        Loan loan = new Loan();
        loan.setReturnDate(""); // set to empty sr
        Loan loan1 = new Loan();
        loan1.setReturnDate("123");

        List<Loan> expected = List.of(loan);
        List<Loan> test = Arrays.asList(loan, loan1);

        when(itemIssuanceRepository.findAll())
                .thenReturn(test);

        assertEquals(expected, service.getAllIssuedItems());
    }

    // case where no items are available
    @Test
    void getAllIssuedItemsNoneAvailable() {
        Loan loan = new Loan();
        loan.setReturnDate("456"); // set to empty sr
        Loan loan1 = new Loan();
        loan1.setReturnDate("123");

        List<Loan> expected = new ArrayList<>(); // empty list
        List<Loan> test = Arrays.asList(loan, loan1);

        when(itemIssuanceRepository.findAll())
                .thenReturn(test);

        assertEquals(expected, service.getAllIssuedItems());
    }


    // ret loan with matchin id, throw exception if not found
    @Test
    void findItemIssuedById() {
        Loan loan = new Loan();
        Optional<Loan> optionalLoan = Optional.of(loan);
        long id = 1;
        when(itemIssuanceRepository.findById(id))
                .thenReturn(optionalLoan);

        assertEquals(loan, service.findItemIssuedById(id));
    }

    // fails, exception was not thrown
    @Test
    void findItemIssuedByIdNotFound() {
        Optional<Loan> optionalLoan = Optional.empty();
        long id = 1;
        when(itemIssuanceRepository.findById(id))
                .thenReturn(optionalLoan);
        Exception exception = assertThrows(Exception.class, () -> {
            service.findItemIssuedById(id);
        });
    }

    // return loan based on borrowerId and itemId, null if not found
    @Test
    void findItemIssued() {
        long itemId = 123;
        long borrowerId = 456;
        Loan loan = new Loan();
        Item item = new Item();
        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        item.setId(itemId);
        loan.setItem(item);
        loan.setBorrower(borrower);
        loan.setReturnDate("");

        List<Loan> test = List.of(loan);
        when(itemIssuanceRepository.findAll())
                .thenReturn(test);
        assertEquals(loan, service.findItemIssued(borrowerId, itemId));

    }

    @Test
    void findItemIssuedNotFound() {
        long itemId = 123;
        long borrowerId = 456;
        Loan loan = new Loan();
        Item item = new Item();
        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        item.setId(itemId);
        loan.setItem(item);
        loan.setBorrower(borrower);
        loan.setReturnDate("");

        List<Loan> test = List.of(loan);
        when(itemIssuanceRepository.findAll())
                .thenReturn(test);
        assertEquals(null, service.findItemIssued(borrowerId+1, itemId));

    }


    // return a list of items that have been return i.e. return date is not empty
    @Test
    void getAllReturnedItem() {
        Loan loan = new Loan();
        Loan loan1 = new Loan();
        loan.setId(0);
        loan.setReturnDate("");
        loan1.setId(1);
        loan1.setReturnDate("123"); // loan 1 has been returned

        List<Loan> loans = Arrays.asList(loan, loan1);

        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getAllReturnedItem();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    // no items are returned
    @Test
    void getAllReturnedItemNoneReturned() {
        Loan loan = new Loan();
        Loan loan1 = new Loan();
        loan.setReturnDate("");
        loan1.setReturnDate("");

        List<Loan> loans = Arrays.asList(loan, loan1);

        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getAllReturnedItem();
        assertEquals(0, result.size());
    }


    // all items are returned
    @Test
    void getAllReturnedItemAllReturned() {
        Loan loan = new Loan();
        Loan loan1 = new Loan();
        loan.setReturnDate("123");
        loan1.setReturnDate("456");

        List<Loan> loans = Arrays.asList(loan, loan1);

        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getAllReturnedItem();
        assertEquals(2, result.size());
    }

    // return empty string if loan with id exists, otherwise return error message
    @Test
    void validateLoanId() {
        Loan loan = new Loan();
        Optional<Loan> optionalLoan = Optional.of(loan);
        long id = 1;
        when(itemIssuanceRepository.findById(id))
                .thenReturn(optionalLoan);

        assertEquals("", service.validateLoanId(id));
    }

    @Test
    void validateLoanIdNotExist() {
        assertEquals("Loan ID does not exist. Invalid Input.", service.validateLoanId(1));
    }

    // return list of loans with fine > 0
    @Test
    void getItemsWithFine() {
        Loan loan = new Loan();
        loan.setTotalFine(10);

        List<Loan> loans = List.of(loan);
        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getItemsWithFine();
        assertEquals(1, result.size());
    }

    // test with loan with negative fine
    @Test
    void getItemsWithFineNeg() {
        Loan loan = new Loan();
        loan.setTotalFine(-10);

        List<Loan> loans = List.of(loan);
        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getItemsWithFine();
        assertEquals(0, result.size());
    }

    // test loan with fine of 0
    @Test
    void getItemsWithFineZero() {
        Loan loan = new Loan();
        loan.setTotalFine(0);

        List<Loan> loans = List.of(loan);
        when(itemIssuanceRepository.findAll())
                .thenReturn(loans);

        List<Loan> result = service.getItemsWithFine();
        assertEquals(0, result.size());
    }
}