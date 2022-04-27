package com.example.IMS.convertor;

import com.example.IMS.dto.ItemDto;
import com.example.IMS.model.Item;
import com.example.IMS.model.ItemType;
import com.example.IMS.model.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ItemConvertorTest {

    private ItemConvertor itemConvertor = new ItemConvertor();

    // specs: convert a model object to a data transfer object
    @Test
    void modelToDtoSuccess() {
        long itemId = 1;
        int quantity = 1;
        double price = 1;
        double fineRate = 1;
        String name = "name";
        long invoiceNumber = 1;
        ItemType itemType = new ItemType();
        Vendor vendor = new Vendor();
        String itemTypeName = "type";
        String vendorName = "vendor";
        itemType.setTypeName(itemTypeName);
        vendor.setName(vendorName);

        Item item = new Item();
        item.setId(itemId);
        item.setQuantity(quantity);
        item.setPrice(price);
        item.setFineRate(fineRate);
        item.setName(name);
        item.setInvoiceNumber(invoiceNumber);
        item.setItemType(itemType);
        item.setVendor(vendor);

        ItemDto itemDto = itemConvertor.modelToDto(item);
        ItemDto expected = new ItemDto(itemId, quantity, price, fineRate, name,
                invoiceNumber, itemTypeName, vendorName);

        assertEquals(expected.getItemId(), itemDto.getItemId());
        assertEquals(expected.getItemQuantity(), itemDto.getItemQuantity());
        assertEquals(expected.getItemPrice(), itemDto.getItemPrice());
        assertEquals(expected.getFineRate(), itemDto.getFineRate());
        assertEquals(expected.getItemName(), itemDto.getItemName());
        assertEquals(expected.getInvoiceNumber(), itemDto.getInvoiceNumber());
        assertEquals(expected.getItemType(), itemDto.getItemType());
        assertEquals(expected.getVendorName(), itemDto.getVendorName());
    }

    @Test
    void modelToDtoNullPtr() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);
        System.setOut(output);

        // item has uninitialized fields
        Item item = new Item();
        itemConvertor.modelToDto(item);

        String result = out.toString();
        assertTrue(result.contains("Null Pointer Exception Caught in Item Convertor"));
    }

    // specs: convert a list of model object to a list of data transfer object
    @Test
    void modelToDtoArraySuccess() {
        long itemId = 1;
        int quantity = 1;
        double price = 1;
        double fineRate = 1;
        String name = "name";
        long invoiceNumber = 1;
        ItemType itemType = new ItemType();
        Vendor vendor = new Vendor();
        String itemTypeName = "type";
        String vendorName = "vendor";
        itemType.setTypeName(itemTypeName);
        vendor.setName(vendorName);

        Item item = new Item();
        item.setId(itemId);
        item.setQuantity(quantity);
        item.setPrice(price);
        item.setFineRate(fineRate);
        item.setName(name);
        item.setInvoiceNumber(invoiceNumber);
        item.setItemType(itemType);
        item.setVendor(vendor);

        List<Item> itemList = List.of(item);
        List<ItemDto> itemDtoList = itemConvertor.modelToDto(itemList);


        assertEquals(itemDtoList.size(), 1);

        ItemDto itemDto = itemDtoList.get(0);
        ItemDto expected = new ItemDto(itemId, quantity, price, fineRate, name,
                invoiceNumber, itemTypeName, vendorName);

        assertEquals(expected.getItemId(), itemDto.getItemId());
        assertEquals(expected.getItemQuantity(), itemDto.getItemQuantity());
        assertEquals(expected.getItemPrice(), itemDto.getItemPrice());
        assertEquals(expected.getFineRate(), itemDto.getFineRate());
        assertEquals(expected.getItemName(), itemDto.getItemName());
        assertEquals(expected.getInvoiceNumber(), itemDto.getInvoiceNumber());
        assertEquals(expected.getItemType(), itemDto.getItemType());
        assertEquals(expected.getVendorName(), itemDto.getVendorName());
    }

    @Test
    void modelToDtoArrayFail() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);
        System.setOut(output);

        // item has uninitialized fields
        Item item = new Item();
        List<Item> itemList = List.of(item);
        List<ItemDto> itemDtoList = itemConvertor.modelToDto(itemList);

        String result = out.toString();
        assertTrue(result.contains("Null Pointer Exception Caught in Item Convertor"));
    }

    // specs: convert a dto to a model
    @Test
    void dtoToModelSuccess() {
        long itemId = 1;
        int quantity = 1;
        double price = 1;
        double fineRate = 1;
        String name = "name";
        long invoiceNumber = 1;
        String itemTypeName = "type";
        String vendorName = "vendor";

        ItemDto itemDto = new ItemDto(itemId, quantity, price, fineRate, name,
                invoiceNumber, itemTypeName, vendorName);

        Item item = itemConvertor.dtoToModel(itemDto);

//        assertEquals(itemId, item.getId()); // not sure if itemId is supposed to be preserved
        assertEquals(quantity, item.getQuantity());
        assertEquals(price, item.getPrice());
        assertEquals(fineRate, item.getFineRate());
        assertEquals(name, item.getName());
        assertEquals(invoiceNumber, item.getInvoiceNumber());
    }

    // expect error converting dto with null fields
    @Test
    void dtoToModelFail() {
        ItemDto itemDto = new ItemDto();
        Exception exception = assertThrows(Exception.class, () -> {
            Item item = itemConvertor.dtoToModel(itemDto);
        });
    }
}