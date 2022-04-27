package com.example.IMS.controller;

import com.example.IMS.controller.ItemController;
import com.example.IMS.convertor.ItemConvertor;
import com.example.IMS.dto.ItemDto;
import com.example.IMS.model.Item;
import com.example.IMS.model.ItemType;
import com.example.IMS.model.Vendor;
import com.example.IMS.service.ItemService;
import com.example.IMS.service.ItemTypeService;
import com.example.IMS.service.VendorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemTypeService itemTypeService;

    @MockBean
    private VendorService vendorService;

    @MockBean
    private ItemConvertor itemConvertor;

    @Test
    public void getItemView() throws Exception {
        List<Item> itemList = new ArrayList<>();
        ItemDto dto = new ItemDto(1, 1, 1, 1, "item", 1, "type 1", "vender");
        List<ItemDto> itemDtoList = List.of(dto);
        when(itemService.getAllItems())
                .thenReturn(itemList);
        when(itemConvertor.modelToDto(itemList))
                .thenReturn(itemDtoList);

        mvc.perform(get("/ItemView"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemDtoList", itemDtoList));

    }
    @Test
    public void getItemCreate() throws Exception {
        List<ItemType> itemTypeList = new ArrayList<>();
        when(itemTypeService.getAllItemTypes())
                .thenReturn(itemTypeList);

        mvc.perform(get("/ItemCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemDto", notNullValue()))
                .andExpect(model().attribute("itemTypeList", itemTypeList));

    }

    @Test
    public void postItemCreate() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(1);
        vendor.setName("vender");
        ItemDto itemDto = new ItemDto(1, 1, 1, 1, "item", 1, "type", "vender");
        String err = "";
        ItemType itemType = new ItemType();
        itemType.setId(1);
        itemType.setTypeName("type");

        when(vendorService.validateVendorName("vender"))
                .thenReturn(err);
        when(vendorService.getVendorByName("item"))
                .thenReturn(vendor);
        when(itemTypeService.validateItemTypeByName("type"))
                .thenReturn(err);
        when(itemTypeService.getItemTypeByName("type"))
                .thenReturn(itemType);
        when(itemService.validateItemId("item", "type"))
                .thenReturn(err);
        when(itemConvertor.dtoToModel(itemDto))
                .thenReturn(new Item());

        mvc.perform(post("/ItemCreate").flashAttr("itemDto", itemDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemView"));
    }

    @Test
    public void postItemCreateError() throws Exception {
        ItemDto itemDto = new ItemDto(1, 1, 1, 1, "item", 1, "type", "vender");
        String err = "error";
        when(vendorService.validateVendorName("vender"))
                .thenReturn(err);
        when(itemTypeService.validateItemTypeByName("type"))
                .thenReturn(err);
        when(itemService.validateItemId("item", "type"))
                .thenReturn(err);

        mvc.perform(post("/ItemCreate").flashAttr("itemDto", itemDto))
                .andExpect(status().isOk());
    }

    @Test
    public void getItemEdit() throws Exception {
        long itemId = 1;
        Item item = new Item();
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        ItemDto itemDto = new ItemDto(1, 1, 1, 1, "item", 1, "type", "vender");
        when(itemConvertor.modelToDto(item))
                .thenReturn(itemDto);

        List<ItemType> itemTypeList = new ArrayList<>();
        when(itemTypeService.getAllItemTypes())
                .thenReturn(itemTypeList);

        mvc.perform(get("/ItemEdit/" + itemId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemDto", itemDto))
                .andExpect(model().attribute("itemTypeList", itemTypeList));

    }

    @Test
    public void getItemDelete() throws Exception {
        long itemId = 1;
        Item item = new Item();
        when(itemService.getItemById(itemId))
                .thenReturn(item);

        ItemDto itemDto = new ItemDto(1, 1, 1, 1, "item", 1, "type", "vender");
        when(itemConvertor.modelToDto(item))
                .thenReturn(itemDto);

        mvc.perform(get("/ItemDelete/" + itemId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemDto", itemDto));
    }

    @Test
    public void postItemDelete() throws Exception {
        long itemId = 1;
        ItemDto itemDto = new ItemDto(1, 1, 1, 1, "item", 1, "type", "vender");
        mvc.perform(post("/ItemDelete/" + itemId).flashAttr("itemDto", itemDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemView"));
    }

}
