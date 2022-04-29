package com.example.IMS.service;

import com.example.IMS.model.Item;
import com.example.IMS.model.ItemRepair;
import com.example.IMS.model.Loan;
import com.example.IMS.repository.IItemRepairRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemRepairServiceTest {

    @Autowired
    private ItemRepairService service;

    @MockBean
    private IItemRepairRepository repository;

    // return the object if found by matching id, otherwise throw exception
    @Test
    void findItemRepairByIdFound() {
        ItemRepair itemRepair = new ItemRepair();
        Optional<ItemRepair> optionalItemRepair = Optional.of(itemRepair);
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optionalItemRepair);

        assertEquals(itemRepair, service.findItemRepairById(id));
    }

    @Test
    void getAllRepairItemsNoException() {
        assertDoesNotThrow(() -> service.getAllRepairItems());
    }

    @Test
    void saveItemRepairNoException() {
        assertDoesNotThrow(() -> service.saveItemRepair(new ItemRepair()));
    }

    @Test
    void deleteItemRepairByIdNoException() {
        assertDoesNotThrow(() -> service.deleteItemRepairById(1));
    }

    // fails, exception not thrown
    @Test
    void findItemRepairByIdNotFound() {
        Optional<ItemRepair> optionalItemRepair = Optional.empty();
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optionalItemRepair);
        Exception exception = assertThrows(Exception.class, () -> {
            service.findItemRepairById(id);
        });
    }
}