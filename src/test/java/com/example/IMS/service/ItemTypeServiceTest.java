package com.example.IMS.service;

import com.example.IMS.model.ItemRepair;
import com.example.IMS.model.ItemType;
import com.example.IMS.repository.IItemTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemTypeServiceTest {

    @Autowired
    private ItemTypeService service;

    @MockBean
    private IItemTypeRepository repository;

    private String itemTypeName;
    private ItemType itemType;

    @BeforeEach
    void setUp() {
        itemTypeName = "type";
        itemType = new ItemType();
        itemType.setTypeName(itemTypeName);

        List<ItemType> itemTypeList = List.of(itemType);
        when(repository.findAll())
                .thenReturn(itemTypeList);
    }

    // given name of item, return object or null if dne
    @Test
    void getItemTypeByNameExist() {
        assertEquals(itemType, service.getItemTypeByName(itemTypeName));
    }

    @Test
    void getItemTypeByNameNotExist() {
        assertNull(service.getItemTypeByName("not exist"));
    }

    // return empty string if an item type with input name exists, otherwise return error message
    @Test
    void validateItemTypeByNameExists() {
        assertEquals("", service.validateItemTypeByName(itemTypeName));
    }

    @Test
    void validateItemTypeByNameNotExists() {
        String notExist = "not exist";
        assertEquals("ItemType with name: " + notExist + " does not exist.", service.validateItemTypeByName(notExist));
    }

    @Test
    void saveItemTypeNoException() {
        assertDoesNotThrow(() -> service.saveItemType(new ItemType()));
    }
}