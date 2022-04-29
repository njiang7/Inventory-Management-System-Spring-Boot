package com.example.IMS.service;

import com.example.IMS.model.Borrower;
import com.example.IMS.model.Loan;
import com.example.IMS.repository.IBorrowerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BorrowerServiceTest {

    @Autowired
    private BorrowerService service;

    @MockBean
    private IBorrowerRepository borrowerRepository;

    // borrower is present
    @Test
    void getBorrowerById() {
        Borrower borrower = new Borrower();
        Optional<Borrower> optionalBorrower = Optional.of(borrower);
        long id = 1;
        when(borrowerRepository.findById(id))
                .thenReturn(optionalBorrower);

        assertEquals(borrower, service.getBorrowerById(id));
    }

    // borrower is absent, but exception was not thrown
    @Test
    void getBorrowerByIdAbsent() {
        Optional<Borrower> optionalBorrower = Optional.empty();
        long id = 1;
        when(borrowerRepository.findById(id))
                .thenReturn(optionalBorrower);
        Exception exception = assertThrows(Exception.class, () -> {
            service.getBorrowerById(id);
        });
    }

    @Test
    void updateBorrowerNoException() {
        assertDoesNotThrow(() -> service.updateBorrower(new Borrower()));
    }

    // ret id of borrower, or -1 if not found
    @Test
    void getBorrowerIdByLoanId() {

        long loanId = 456;
        long borrowerId = 123;
        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setBorrower(borrower);
        borrower.addLoan(loan);
        List<Borrower> borrowerList = List.of(borrower);

        when(borrowerRepository.findAll())
                .thenReturn(borrowerList);

        assertEquals(borrowerId, service.getBorrowerIdByLoanId(loanId));

    }

    @Test
    void getBorrowerIdByLoanIdNotFound() {
        long loanId = 456;
        long borrowerId = 123;
        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);
        Loan loan = new Loan();
        loan.setId(loanId+1);
        loan.setBorrower(borrower);
        borrower.addLoan(loan);
        List<Borrower> borrowerList = List.of(borrower);

        when(borrowerRepository.findAll())
                .thenReturn(borrowerList);
        assertEquals(-1, service.getBorrowerIdByLoanId(loanId));
    }

    // return borrowerid if exist, otherwise an error message
    @Test
    void validateBorrowerId() {
        Borrower borrower = new Borrower();
        Optional<Borrower> optionalBorrower = Optional.of(borrower);
        long id = 1;
        when(borrowerRepository.findById(id))
                .thenReturn(optionalBorrower);

        assertEquals("", service.validateBorrowerId(id));

    }

    @Test
    void validateBorrowerIdNotExist() {
        assertEquals("Borrower id does not exist.", service.validateBorrowerId(123));
    }

}