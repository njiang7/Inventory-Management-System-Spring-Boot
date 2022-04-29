package com.example.IMS;
import com.example.IMS.Utilities.Helper;
import com.example.IMS.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTests {
    private Borrower borrower;
    private ItemType itemType;
    private Vendor vendor;
    private Item item;
    private Loan loan;
    private ManagerType mtype;
    private InventoryManager manager;
    private ItemRepair itemRepair;

    @BeforeEach
    void setup() {
        // set up borrower
        borrower = new Borrower();
        borrower.setLastName("Hu");
        borrower.setEmail("huchuheng@gmail.com");
        borrower.setId(1);
        borrower.setFirstName("Michael");
        List<Loan> l = new ArrayList<>();
        borrower.setLoan(l);
        // set up an item type
        itemType = new ItemType();
        itemType.setId(3);
        itemType.setTypeName("book");
        // set up a vendor
        vendor = new Vendor();
        vendor.setEmail("vendor@gmail.com");
        vendor.setId(9);
        vendor.setName("Diligent");
        // set up an item
        item = new Item();
        item.setId(2);
        item.setItemType(itemType);
        item.setFineRate(2.0);
        item.setInvoiceNumber(10);
        item.setName("2046");
        item.setQuantity(5);
        item.setVendor(vendor);
        item.setPrice(100);
        item.setInvoiceNumber(10086);
        List<Loan> loan_list = new ArrayList<>();
        item.setLoan(loan_list);
        // set up a loan
        loan = new Loan();
        loan.setId(4);
        loan.setBorrower(borrower);
        loan.setIssueDate("2022/04/28 00:00:00");
        loan.setLoanDuration(1);
        // set up a manager type
        mtype = new ManagerType();
        mtype.setId(123);
        mtype.setTypeName("boss");
        // set up a inventory manager
        manager = new InventoryManager();
        manager.setEmail("manager@gmail.com");
        manager.setId(7);
        manager.setName("Do");
        manager.setPassword("001");
        manager.setManagerType(mtype);
        // set up a repair item
        itemRepair = new ItemRepair();
        itemRepair.setItem(item);
        itemRepair.setPrice(1002);
        itemRepair.setCost(1001);
        itemRepair.setVendor(vendor);
        itemRepair.setId(1000);
    }

    @Test
    public void getFirstNameBorrowerTest() {
        String first_name = borrower.getFirstName();
        assertTrue(first_name.equals("Michael"));
    }

    @Test
    public void getLastNameBorrowerTest() {
        String last_name = borrower.getLastName();
        assertTrue(last_name.equals("Hu"));
    }

    @Test
    public void getEmailBorrowerTest() {
        String email = borrower.getEmail();
        assertTrue(email.equals("huchuheng@gmail.com"));
    }

    @Test
    public void getIdBorrowerTest() {
        long id = borrower.getId();
        assertTrue(id == 1);
    }

    @Test
    public void getLoanBorrowerTest() {
        List<Loan> ls = borrower.getLoan();
        assertTrue(ls.size() == 0);
    }

    @Test
    public void addLoanBorrowerTest() {
        List<Loan> ls = borrower.getLoan();
        assertTrue(ls.size() == 0);
        Loan l = new Loan();
        borrower.addLoan(l);
        assertTrue(ls.size() == 1);
    }

    // This test failed because the addLoan function doesn't check if the added loan has already existed
    // The same loan is added twice
    @Test
    public void addSameLoanTwiceBorrowerTest() {
        List<Loan> ls = borrower.getLoan();
        assertTrue(ls.size() == 0);
        Loan l = new Loan();
        l.setId(1);
        borrower.addLoan(l);
        borrower.addLoan(l);
        assertTrue(ls.size() == 1);
    }

    @Test
    public void removeLoadBorrowerTest() {
        List<Loan> ls = borrower.getLoan();
        assertEquals(ls.size(), 0);
        Loan l = new Loan();
        l.setBorrower(borrower);
        borrower.addLoan(l);
        borrower.removeLoan(l);
        assertEquals(ls.size(), 0);
        assertNull(l.getBorrower());
    }

    @Test
    public void totalFineBorrowerTest() {
        loan.setReturnDate("2022/04/31 00:00:00");
        loan.setItem(item);
        borrower.addLoan(loan);
        Loan loan2 = new Loan();
        loan2.setIssueDate("2022/04/28 00:00:00");
        loan2.setItem(item);
        loan2.setReturnDate("2022/04/31 00:00:00");
        borrower.addLoan(loan2);
        assertEquals(300, borrower.totalFine());
    }

    // This test fails since the implementation in Borrower.java line 101 is wrong
    // Note that l.setTotalFine's parameter fine should never be negative (if you pay fine of a loan it should be zero)
    @Test
    public void updateFineBorrowerTest() {
        loan.setReturnDate("2022/04/31 00:00:00");
        loan.setItem(item);
        loan.setTotalFine(loan.calculateFine());
        borrower.addLoan(loan);
        Loan loan2 = new Loan();
        loan2.setIssueDate("2022/04/28 00:00:00");
        loan2.setItem(item);
        loan2.setReturnDate("2022/04/31 00:00:00");
        loan2.setTotalFine(loan2.calculateFine());
        borrower.addLoan(loan2);
        borrower.updateFine(200);
        assertEquals(0, loan.getTotalFine());
        assertEquals(100, loan2.getTotalFine());
    }

    @Test
    public void updateFineNegativeBorrowerTest() {
        loan.setReturnDate("2022/04/31 00:00:00");
        loan.setItem(item);
        loan.setTotalFine(loan.calculateFine());
        borrower.addLoan(loan);
        Loan loan2 = new Loan();
        loan2.setIssueDate("2022/04/28 00:00:00");
        loan2.setItem(item);
        loan2.setReturnDate("2022/04/31 00:00:00");
        loan2.setTotalFine(loan2.calculateFine());
        borrower.addLoan(loan2);
        borrower.updateFine(-100);
        assertEquals(150, loan.getTotalFine());
        assertEquals(150, loan2.getTotalFine());
    }

    @Test
    public void getIdItemTypeTest() {
        assertEquals(itemType.getId(), 3);
    }

    @Test
    public void getTypeNameItemTypeTest() {
        assertEquals(itemType.getTypeName(), "book");
    }

    @Test
    public void getIdVendorTest() {
        assertEquals(vendor.getId(), 9);
    }

    @Test
    public void getEmailVendorTest() {
        assertEquals(vendor.getEmail(), "vendor@gmail.com");
    }

    @Test
    public void getNameVendorTest() {
        assertEquals(vendor.getName(), "Diligent");
    }

    @Test
    public void getIdItemTest() {
        assertEquals(item.getId(), 2);
    }

    @Test
    public void getQuantityItemTest() {
        assertEquals(item.getQuantity(), 5);
    }

    @Test
    public void getPriceItemTest() {
        assertEquals(item.getPrice(), 100);
    }

    @Test
    public void getFineRateItemTest() {
        assertEquals(item.getFineRate(), 2.0);
    }

    @Test
    public void getNameItemTest() {
        assertEquals(item.getName(), "2046");
    }

    @Test
    public void getInvoiceNumberItemTest() {
        assertEquals(item.getInvoiceNumber(), 10086);
    }

    @Test
    public void getItemTypeItemTest() {
        ItemType it = item.getItemType();
        assertEquals(it.getTypeName(), itemType.getTypeName());
        assertEquals(it.getId(), itemType.getId());
    }

    @Test
    public void getVendorItemTest() {
        Vendor vd = item.getVendor();
        assertEquals(vd, vendor);
    }

    @Test
    public void getLoanItemTest() {
        List<Loan> l = item.getLoan();
        assertTrue(l.isEmpty());
    }

    @Test
    public void getIdLoanTest() {
        long id = loan.getId();
        assertEquals(id, 4);
    }

    @Test
    public void getReturnDateDefaultLoanTest() {
        loan.setReturnDate();
        String current_time = Helper.getCurrentTime();
        assertEquals(loan.getReturnDate(), current_time);
    }

    @Test
    public void getReturnDateLoanTest() {
        loan.setReturnDate("2022/04/28 00:00:00");
        String r = loan.getReturnDate();
        assertEquals(r, "2022/04/28 00:00:00");
    }

    @Test
    public void getTotalFineLoanTest() {
        loan.setTotalFine(1001);
        double fine = loan.getTotalFine();
        assertEquals(fine, 1001);
    }

    @Test
    public void getItemLoanTest() {
        loan.setItem(item);
        assertEquals(loan.getItem(), item);
    }

    @Test
    public void getBorrowerLoanTest() {
        loan.setBorrower(borrower);
        assertEquals(borrower, loan.getBorrower());
    }

    @Test
    public void getIssueDateLoanTest() {
        String d = loan.getIssueDate();
        assertEquals("2022/04/28 00:00:00", d);
    }

    @Test
    public void getLoanDurationLoanTest() {
        double d = loan.getLoanDuration();
        assertEquals(1, d);
    }

    @Test
    public void calculateFineLoanTest() {
        loan.setReturnDate("2022/04/31 00:00:00");
        loan.setItem(item);
        assertEquals(150, loan.calculateFine());
    }

    @Test
    public void calculateFineZeroLoanTest() {
        loan.setReturnDate("2022/04/27 00:00:00");
        loan.setItem(item);
        assertEquals(0, loan.calculateFine());
    }

    @Test
    public void calculateFineNoReturnDateLoanTest() {
        loan.setItem(item);
        assertEquals(0, loan.calculateFine());
    }

    @Test
    public void calculateFineExceptionLoanTest() {
        assertEquals(0, loan.calculateFine()); //should print "Null Pointer Exception Caught"
    }

    // This test fails because the implementation error in line 112 in Loan.java
    // Note that fineRate = Helper.lowPrecedenceFineRate is always true in the implementation which is wrong
    @Test
    public void calculateFineHighLoanTest() {
        loan.setReturnDate("2022/04/31 00:00:00");
        ItemType it = new ItemType();
        it.setTypeName("High Precedence");
        item.setItemType(it);
        loan.setItem(item);
        assertEquals(300, loan.calculateFine());
    }

    @Test
    public void equalsLoanSameIdTest() {
        Loan l1 = new Loan();
        l1.setId(1);
        l1.setReturnDate("2022/04/31 00:00:00");
        Loan l2 = new Loan();
        l2.setId(1);
        l2.setReturnDate("2022/04/30 00:00:00");
        assertEquals(l1, l2);
    }

    @Test
    public void equalsLoanSameLoanTest() {
        Loan l1 = new Loan();
        l1.setId(1);
        l1.setReturnDate("2022/04/31 00:00:00");
        assertEquals(l1, l1);
    }

    @Test
    public void equalsLoanDifferentLoanTest() {
        Loan l1 = new Loan();
        l1.setId(1);
        l1.setReturnDate("2022/04/31 00:00:00");
        Loan l2 = new Loan();
        l2.setId(2);
        l2.setReturnDate("2022/04/30 00:00:00");
        assertNotEquals(l2, l1);
    }

    @Test
    public void equalsLoanNotLoanTest() {
        Loan l1 = new Loan();
        l1.setId(1);
        l1.setReturnDate("2022/04/31 00:00:00");
        Item l2 = new Item();
        assertNotEquals(l2, l1);
    }

    @Test
    public void hashCodeLoanTest() {
        Loan l1 = new Loan();
        l1.setId(1);
        l1.setReturnDate("2022/04/31 00:00:00");
        Loan l2 = new Loan();
        l2.setId(1);
        l2.setReturnDate("2022/04/30 00:00:00");
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    public void getIdManagerTypeTest() {
        assertEquals(123, mtype.getId());
    }

    @Test
    public void getTypeNameManagerTypeTest() {
        assertEquals("boss", mtype.getTypeName());
    }

    @Test
    public void getIdInventoryManagerTest() {
        assertEquals(7, manager.getId());
    }

    @Test
    public void getNameInventoryManagerTest() {
        assertEquals("Do", manager.getName());
    }

    @Test
    public void getEmailInventoryManagerTest() {
        assertEquals("manager@gmail.com", manager.getEmail());
    }

    @Test
    public void getPasswordInventoryManagerTest() {
        assertEquals("001", manager.getPassword());
    }

    @Test
    public void getManagerTypeInventoryManagerTest() {
        assertEquals(mtype, manager.getManagerType());
    }

    @Test
    public void getIdItemRepair() {
        assertEquals(1000, itemRepair.getId());
    }

    @Test
    public void getCostItemRepair() {
        assertEquals(1001, itemRepair.getCost());
    }

    @Test
    public void getPriceItemRepair() {
        assertEquals(1002, itemRepair.getPrice());
    }

    @Test
    public void getItemItemRepair() {
        assertEquals(item, itemRepair.getItem());
    }

    @Test
    public void getVendorItemRepair() {
        assertEquals(vendor, itemRepair.getVendor());
    }

    // removing a loan from item should set the borrower to null
    @Test
    void itemRemoveLoan() {
        item.addLoan(loan);
        loan.setBorrower(borrower);
        item.removeLoan(loan);
        assertNull(loan.getBorrower());
    }

    @Test
    void itemIncreaseQuantity() {
        item.setQuantity(1);
        item.increaseQuantity();
        assertEquals(2, item.getQuantity());
    }

    // if calling loan.equals on not a loan object, return false
    @Test
    void loanEqualsWrongObjectType() {
        assertFalse(loan.equals(borrower));
    }

    // return false if comparing a loan without id
    @Test
    void loanEqualsNullId() {
        assertFalse(loan.equals(new Loan()));
    }

    // return date is empty causes error
    @Test
    void loanCalculateFineReturnDateEmpty() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);
        System.setOut(output);

        Loan loan = new Loan();
        loan.calculateFine();
        String result = out.toString();
        assertTrue(result.contains("Null Pointer Exception Caught"));
    }
}
