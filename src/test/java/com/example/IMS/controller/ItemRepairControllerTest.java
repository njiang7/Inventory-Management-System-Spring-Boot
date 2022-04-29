package com.example.IMS.controller;

import com.example.IMS.convertor.ItemRepairConvertor;
import com.example.IMS.dto.ItemIssuanceDto;
import com.example.IMS.dto.ItemRepairDto;
import com.example.IMS.model.ItemRepair;
import com.example.IMS.model.Loan;
import com.example.IMS.service.ItemRepairService;
import com.example.IMS.service.ItemService;
import com.example.IMS.service.VendorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(ItemRepairController.class)
class ItemRepairControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRepairService itemRepairService;

    @MockBean
    private VendorService vendorService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemRepairConvertor itemRepairConvertor;

    @Test
    void viewGet() throws Exception {
        List<ItemRepair> itemRepairList = new ArrayList<>();
        when(itemRepairService.getAllRepairItems())
                .thenReturn(itemRepairList);

        List<ItemRepairDto> itemRepairDtoList = new ArrayList<>();
        when(itemRepairConvertor.modelToDto(itemRepairList))
                .thenReturn(itemRepairDtoList);

        mvc.perform(get("/ItemRepairView"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ItemRepairDtoList", itemRepairDtoList));
    }

    @Test
    void createGet() throws Exception {
        mvc.perform(get("/ItemRepairCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemRepairDto", notNullValue()));
    }

    @Test
    void createPost() throws Exception {
        ItemRepairDto itemRepairDto = new ItemRepairDto(1,2,3,4,5);

        String err1 = "";
        when(vendorService.validateVendorId(3))
                .thenReturn(err1);

        String err2 = "";
        when(itemService.validateItemId(2))
                .thenReturn(err2);

        ItemRepair itemRepair = new ItemRepair();
        itemRepair.setId(1);
        when(itemRepairConvertor.DtoToModel(itemRepairDto))
                .thenReturn(itemRepair);

        mvc.perform(post("/ItemRepairCreate").flashAttr("itemRepairDto", itemRepairDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemRepairView"));
    }

    @Test
    void createPostValidateError() throws Exception {
        ItemRepairDto itemRepairDto = new ItemRepairDto(1,2,3,4,5);

        String err1 = "error";
        when(vendorService.validateVendorId(3))
                .thenReturn(err1);

        String err2 = "error";
        when(itemService.validateItemId(2))
                .thenReturn(err2);

        mvc.perform(post("/ItemRepairCreate").flashAttr("itemRepairDto", itemRepairDto))
                .andExpect(status().isOk());
    }

    @Test
    void editGet() throws Exception {
        long itemRepairId = 1;
        ItemRepair itemRepair = new ItemRepair();

        when(itemRepairService.findItemRepairById(itemRepairId))
                .thenReturn(itemRepair);

        ItemRepairDto itemRepairDto = new ItemRepairDto(1,2,3,4,5);
        when(itemRepairConvertor.modelToDto(itemRepair))
                .thenReturn(itemRepairDto);

        mvc.perform(get("/ItemRepairEdit/" + itemRepairId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemRepairDto", itemRepairDto));
    }

    @Test
    void deleteGet() throws Exception {
        long itemRepairId = 1;
        ItemRepair itemRepair = new ItemRepair();

        when(itemRepairService.findItemRepairById(itemRepairId))
                .thenReturn(itemRepair);

        ItemRepairDto itemRepairDto = new ItemRepairDto(1,2,3,4,5);
        when(itemRepairConvertor.modelToDto(itemRepair))
                .thenReturn(itemRepairDto);

        mvc.perform(get("/ItemRepairDelete/" + itemRepairId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("itemRepairDto", itemRepairDto));
    }

    @Test
    void deletePost() throws Exception {
        long itemRepairId = 1;
        ItemRepairDto itemRepairDto = new ItemRepairDto(1,2,3,4,5);
        mvc.perform(post("/ItemRepairDelete/" + itemRepairId).flashAttr("itemRepairDto", itemRepairDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ItemRepairView"));

    }
}