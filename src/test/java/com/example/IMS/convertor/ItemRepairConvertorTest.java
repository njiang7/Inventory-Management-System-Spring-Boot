package com.example.IMS.convertor;

import com.example.IMS.controller.FineController;
import com.example.IMS.dto.ItemRepairDto;
import com.example.IMS.model.Item;
import com.example.IMS.model.ItemRepair;
import com.example.IMS.model.Vendor;
import com.example.IMS.service.ItemService;
import com.example.IMS.service.VendorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepairConvertorTest {

    @Autowired
    private ItemRepairConvertor convertor;

    @MockBean
    private VendorService vendorService;

    @MockBean
    private ItemService itemService;

    // dto type: ItemRepairDto(itemRepairId, itemId, vendorId, repairCost, itemPrice)
    @Test
    void modelToDtoSuccess() {
        long id = 1;
        double cost = 2;
        double price = 3;
        Item item = new Item();
        long itemId = 4;
        item.setId(itemId);
        Vendor vendor = new Vendor();
        long vendorId = 5;
        vendor.setId(vendorId);


        ItemRepair itemRepair = new ItemRepair();
        itemRepair.setId(id);
        itemRepair.setCost(cost);
        itemRepair.setPrice(price);
        itemRepair.setItem(item);
        itemRepair.setVendor(vendor);

        ItemRepairDto dto = convertor.modelToDto(itemRepair);

        assertEquals(id, dto.getId());
        assertEquals(itemId, dto.getItemId());
        assertEquals(vendorId, dto.getVendorId());
        assertEquals(cost, dto.getRepairCost());
        assertEquals(price, dto.getPrice());

    }


    // test with null input
    @Test
    void modelToDtoFail() {
        Exception exception = assertThrows(Exception.class, () -> {
            ItemRepairDto dto = convertor.modelToDto((ItemRepair) null);
        });
    }

    // need to use springboottest to inject and mock services used by converters
    @Test
    void dtoToModelSuccess() {
        long id = 1;
        double cost = 2;
        double price = 3;
        long itemId = 4;
        long vendorId = 5;
        ItemRepairDto dto = new ItemRepairDto(id, itemId, vendorId, cost, price);

        Item item = new Item();
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        Vendor vendor = new Vendor();
        when(vendorService.getVendorById(vendorId))
                .thenReturn(vendor);

        ItemRepair itemRepair = convertor.DtoToModel(dto);

        assertEquals(id, itemRepair.getId());
        assertEquals(cost, itemRepair.getCost());
        assertEquals(price, itemRepair.getPrice());
        assertEquals(item, itemRepair.getItem());
        assertEquals(vendor, itemRepair.getVendor());
    }

    // test null input
    @Test
    void dtoToModelFail() {
        Exception exception = assertThrows(Exception.class, () -> {
            ItemRepair item = convertor.DtoToModel((ItemRepairDto) null);
        });
    }


    @Test
    void arrayModelToDtoSuccess() {
        long id = 1;
        double cost = 2;
        double price = 3;
        Item item = new Item();
        long itemId = 4;
        item.setId(itemId);
        Vendor vendor = new Vendor();
        long vendorId = 5;
        vendor.setId(vendorId);


        ItemRepair itemRepair = new ItemRepair();
        itemRepair.setId(id);
        itemRepair.setCost(cost);
        itemRepair.setPrice(price);
        itemRepair.setItem(item);
        itemRepair.setVendor(vendor);

        List<ItemRepair> itemRepairList = List.of(itemRepair);
        List<ItemRepairDto> itemRepairDtoList = convertor.modelToDto(itemRepairList);

        assertEquals(itemRepairDtoList.size(), 1);
        ItemRepairDto dto = itemRepairDtoList.get(0);

        assertEquals(id, dto.getId());
        assertEquals(itemId, dto.getItemId());
        assertEquals(vendorId, dto.getVendorId());
        assertEquals(cost, dto.getRepairCost());
        assertEquals(price, dto.getPrice());
    }

    // test null input
    @Test
    void arrayModelToDtoFail() {
        Exception exception = assertThrows(Exception.class, () -> {
            List<ItemRepairDto> itemRepairDtoList = convertor.modelToDto((List<ItemRepair>) null);
        });
    }


    @Test
    void arrayDtoToModelSuccess() {
        long id = 1;
        double cost = 2;
        double price = 3;
        long itemId = 4;
        long vendorId = 5;
        ItemRepairDto dto = new ItemRepairDto(id, itemId, vendorId, cost, price);

        List<ItemRepairDto> itemRepairDtoList = List.of(dto);

        Item item = new Item();
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        Vendor vendor = new Vendor();
        when(vendorService.getVendorById(vendorId))
                .thenReturn(vendor);

        List<ItemRepair> itemRepairList = convertor.DtoToModel(itemRepairDtoList);
        assertEquals(itemRepairList.size(), 1);


        ItemRepair itemRepair = itemRepairList.get(0);

        assertEquals(id, itemRepair.getId());
        assertEquals(cost, itemRepair.getCost());
        assertEquals(price, itemRepair.getPrice());
        assertEquals(item, itemRepair.getItem());
        assertEquals(vendor, itemRepair.getVendor());
    }

    // test null input
    @Test
    void arrayDtoToModelFail() {
        Exception exception = assertThrows(Exception.class, () -> {
            List<ItemRepair> itemRepairList = convertor.DtoToModel((List<ItemRepairDto>) null);
        });
    }
}