package com.example.IMS.service;

import com.example.IMS.model.Borrower;
import com.example.IMS.model.Item;
import com.example.IMS.model.ItemType;
import com.example.IMS.model.Loan;
import com.example.IMS.repository.IItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService service;

    @MockBean
    private IItemRepository repository;

    // return item with matching id, otherwise throw exception
    @Test
    void getItemByIdFound() {
        long id = 1;
        Item item = new Item();
        item.setId(id);
        Optional<Item> optional = Optional.of(item);

        when(repository.findById(id))
                .thenReturn(optional);

        assertEquals(item, service.getItemById(id));
    }

    // fails exception not thrown
    @Test
    void getItemByIdNotFound() {
        Optional<Item> optional = Optional.empty();
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optional);
        Exception exception = assertThrows(Exception.class, () -> {
            service.getItemById(id);
        });
    }

    // return an empty string if item with id exists, otherwise return error message
    @Test
    void validateItemIdSuccess() {
        Item item = new Item();
        Optional<Item> optional = Optional.of(item);
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optional);

        assertEquals("", service.validateItemId(id));
    }

    @Test
    void validateItemIdFail() {
        assertEquals("Item id does not exist", service.validateItemId(1));
    }


    // return the id of an item corresponding to the loan id, or -1 if not found
    @Test
    void findItemIdByLoanIdFound() {
        long loanId = 1;
        Item item = new Item();
        long itemId = 2;
        item.setId(itemId);
        Loan loan = new Loan();
        loan.setId(loanId);
        item.addLoan(loan);

        List<Item> itemList = List.of(item);
        when(repository.findAll())
                .thenReturn(itemList);

        assertEquals(itemId, service.findItemIdByLoanId(loanId));

    }

    @Test
    void findItemIdByLoanIdNotFound() {
        long loanId = 1;
        Item item = new Item();
        long itemId = 2;
        item.setId(itemId);
        Loan loan = new Loan();
        loan.setId(loanId);
        item.addLoan(loan);

        List<Item> itemList = List.of(item);
        when(repository.findAll())
                .thenReturn(itemList);

        assertEquals(-1, service.findItemIdByLoanId(loanId+1));
    }


    // check if given strings itemName and itemType matches an item already in the database
    // return empty string if not exist, otherwise return error message
    @Test
    void testValidateItemExists() {
        String itemName = "item";
        String itemTypeName = "type";
        ItemType itemType = new ItemType();
        itemType.setTypeName(itemTypeName);

        Item item = new Item();
        item.setName(itemName);
        item.setItemType(itemType);

        List<Item> itemList = List.of(item);
        when(repository.findAll())
                .thenReturn(itemList);

        assertEquals("Item already exists in the database. Cannot add.", service.validateItemId(itemName, itemTypeName));

    }

    @Test
    void testValidateItemIdNotExists() {
        String itemName = "item";
        String itemTypeName = "type";
        ItemType itemType = new ItemType();
        itemType.setTypeName(itemTypeName);

        Item item = new Item();
        item.setName(itemName);
        item.setItemType(itemType);

        List<Item> itemList = List.of(item);
        when(repository.findAll())
                .thenReturn(itemList);

        assertEquals("", service.validateItemId("itemName", "itemTypeName"));

    }


}