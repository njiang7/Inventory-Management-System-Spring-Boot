package com.example.IMS;

import com.example.IMS.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DtoTests {
    private FineDto fdto;
    private ItemDto itemDto;
    private ItemIssuanceDto iiDto;
    private ItemRepairDto ipDto;
    private ItemReturnDto irDto;

    @BeforeEach
    public void setup() {
        fdto = new FineDto(123);
        itemDto = new ItemDto(1, 2, 3.0, 4.0, "book", 5, "good", "Terra");
        iiDto = new ItemIssuanceDto(1, 2, 3, 4);
        ipDto = new ItemRepairDto(1,2,3,4.0,5.0);
        irDto = new ItemReturnDto(1,2,"2022/04/28 00:00");
    }

    @Test
    public void setGetBorrowerIdFineDtoTest() {
        FineDto f = new FineDto();
        f.setBorrowerId(12);
        assertEquals(12, f.getBorrowerId());
    }

    @Test
    public void setGetTotalFineFineDtoTest() {
        fdto.setTotalFine(1000);
        assertEquals(1000, fdto.getTotalFine());
    }

    @Test
    public void setGetFinePaidFineFineDtoTest() {
        fdto.setFinePaid(101);
        assertEquals(101, fdto.getFinePaid());
    }

    @Test
    public void setGetItemIdItemDtoTest() {
        itemDto.setItemId(555);
        assertEquals(555, itemDto.getItemId());
    }

    @Test
    public void setGetItemQuantityItemDtoTest() {
        itemDto.setItemQuantity(101);
        assertEquals(101, itemDto.getItemQuantity());
    }

    @Test
    public void setGetItemPriceItemDtoTest() {
        itemDto.setItemPrice(123);
        assertEquals(123, itemDto.getItemPrice());
    }

    @Test
    public void setGetFineRateItemDtoTest() {
        itemDto.setFineRate(500);
        assertEquals(500, itemDto.getFineRate());
    }

    @Test
    public void setGetItemNameItemDtoTest() {
        itemDto.setItemName("135");
        assertEquals("135", itemDto.getItemName());
    }

    @Test
    public void setGetInvoiceNumberItemDtoTest() {
        itemDto.setInvoiceNumber(136);
        assertEquals(136, itemDto.getInvoiceNumber());
    }

    @Test
    public void setGetItemTypeItemDtoTest() {
        itemDto.setItemType("aaa");
        assertEquals("aaa", itemDto.getItemType());
    }

    @Test
    public void setGetVendorNameItemDtoTest() {
        itemDto.setVendorName("asd");
        assertEquals("asd", itemDto.getVendorName());
    }

    @Test
    public void setGetIdItemIssuanceDtoTest() {
        ItemIssuanceDto i = new ItemIssuanceDto(2, 4, 6, 8, 10);
        i.setId(101);
        assertEquals(101, i.getId());
    }

    @Test
    public void setGetItemIdItemIssuanceDtoTest() {
        iiDto.setItemId(123);
        assertEquals(123, iiDto.getItemId());
    }

    @Test
    public void setGetBorrowerIdItemIssuanceDtoTest() {
        ItemIssuanceDto i = new ItemIssuanceDto();
        i.setBorrowerId(1234);
        assertEquals(1234, i.getBorrowerId());
    }

    @Test
    public void setGetLoanDurationItemIssuanceDtoTest() {
        iiDto.setLoanDuration(75);
        assertEquals(75, iiDto.getLoanDuration());
    }

    @Test
    public void setGetFineAmountItemIssuanceDtoTest() {
        iiDto.setFineAmount(56);
        assertEquals(56, iiDto.getFineAmount());
    }

    @Test
    public void setGetIssueDateItemIssuanceDtoTest() {
        iiDto.setIssueDate("2022/04/28 00:00");
        assertEquals("2022/04/28 00:00", iiDto.getIssueDate());
    }

    @Test
    public void setGetDueDateItemIssuanceDtoTest() {
        iiDto.setDueDate("2022/04/28 00:00");
        assertEquals("2022/04/28 00:00", iiDto.getDueDate());
    }

    @Test
    public void setGetReturnDateItemIssuanceDtoTest() {
        iiDto.setReturnDate("2022/04/28 00:00");
        assertEquals("2022/04/28 00:00", iiDto.getReturnDate());
    }

    @Test
    public void setGetItemIdItemRepairDtoTest() {
        ipDto.setItemId(555);
        assertEquals(555, ipDto.getItemId());
    }

    @Test
    public void setGetIdItemRepairDtoTest() {
        ItemRepairDto i = new ItemRepairDto();
        i.setId(5555);
        assertEquals(5555, i.getId());
    }

    @Test
    public void setGetVendorIdNameItemRepairDtoTest() {
        ipDto.setVendorId(235);
        assertEquals(235, ipDto.getVendorId());
    }

    @Test
    public void setGetRepairCostItemRepairDtoTest() {
        ipDto.setRepairCost(1255);
        assertEquals(1255, ipDto.getRepairCost());
    }

    @Test
    public void setGetPriceItemRepairDtoTest() {
        ipDto.setPrice(645);
        assertEquals(645, ipDto.getPrice());
    }

    @Test
    public void setGetItemIdItemReturnDtoTest() {
        ItemReturnDto i = new ItemReturnDto();
        i.setItemId(123);
        assertEquals(123, i.getItemId());
    }

    @Test
    public void setGetBorrowerIdItemReturnDtoTest() {
        irDto.setBorrowerId(586);
        assertEquals(586, irDto.getBorrowerId());
    }

    @Test
    public void setGetReturnDateItemReturnDtoTest() {
        irDto.setReturnDate("2022/04/29 00:00");
        assertEquals("2022/04/29 00:00", irDto.getReturnDate());
    }

}
