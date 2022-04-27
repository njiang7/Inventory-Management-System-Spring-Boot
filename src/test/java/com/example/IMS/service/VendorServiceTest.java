package com.example.IMS.service;

import com.example.IMS.model.Item;
import com.example.IMS.model.Vendor;
import com.example.IMS.repository.IVendorRepository;
import org.junit.jupiter.api.BeforeEach;
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
class VendorServiceTest {

    @Autowired
    private VendorService service;

    @MockBean
    private IVendorRepository repository;

    // return vendor object with matching id, otherwise throw exception
    @Test
    void getVendorById() {
        long id = 1;
        Vendor vendor = new Vendor();
        vendor.setId(id);
        Optional<Vendor> optional = Optional.of(vendor);

        when(repository.findById(id))
                .thenReturn(optional);

        assertEquals(vendor, service.getVendorById(id));
    }

    @Test
    void getVendorByIdException() {
        Optional<Vendor> optional = Optional.empty();
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optional);
        Exception exception = assertThrows(Exception.class, () -> {
            service.getVendorById(id);
        });
    }

    // return empty string if vendor id is valid, error message if dne
    @Test
    void validateVendorIdSuccess() {
        Vendor vendor = new Vendor();
        Optional<Vendor> optional = Optional.of(vendor);
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optional);

        assertEquals("", service.validateVendorId(id));
    }

    @Test
    void validateVendorIdFail() {
        Vendor vendor = new Vendor();
        Optional<Vendor> optional = Optional.of(vendor);
        long id = 1;
        when(repository.findById(id))
                .thenReturn(optional);

        assertEquals("Vendor ID does not exist", service.validateVendorId(id+1));
    }

    // given string name of vendor, return vendor object. Return null if dne.
    @Test
    void getVendorByNameExists() {
        Vendor vendor = new Vendor();
        String vendorName = "vendor";
        vendor.setName(vendorName);
        List<Vendor> vendors = List.of(vendor);
        when(repository.findAll())
                .thenReturn(vendors);
        assertEquals(vendor, service.getVendorByName(vendorName));
    }


    @Test
    void getVendorByNameDne() {
        Vendor vendor = new Vendor();
        String vendorName = "vendor";
        vendor.setName(vendorName);
        List<Vendor> vendors = List.of(vendor);
        when(repository.findAll())
                .thenReturn(vendors);
        assertEquals(null, service.getVendorByName("dne"));
    }

    // return empty string if input vendor name exists, otherwise return error message
    @Test
    void validateVendorNameSuccess() {
        Vendor vendor = new Vendor();
        String vendorName = "vendor";
        vendor.setName(vendorName);
        List<Vendor> vendors = List.of(vendor);
        when(repository.findAll())
                .thenReturn(vendors);
        assertEquals("", service.validateVendorName(vendorName));
    }

    @Test
    void validateVendorNameFail() {
        String vendorName = "dne";
        assertEquals("Vendor with name: " + vendorName + " does not exist.",
                service.validateVendorName(vendorName));
    }
}