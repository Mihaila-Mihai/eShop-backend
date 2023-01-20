package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.VoucherDto;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.exceptions.InvalidVoucherException;
import com.javaproject.eshop.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {
    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("Save voucher test")
    void saveVoucher() {
        VoucherDto voucherDto = new VoucherDto("SUMMER-20", 20, true);

        Voucher voucher = Voucher.builder()
                .value(20)
                .name("SUMMER-20")
                .active(true)
                .build();
        when(voucherRepository.save(voucher)).thenReturn(voucher);


        Voucher result = voucherService.saveVoucher(voucherDto);

        assertEquals("SUMMER-20", result.getName());
        assertEquals(20, result.getValue());
        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Get voucher success test")
    void getVoucherSuccess() {
        int voucherId = 1;
        Voucher voucher = Voucher.builder()
                .value(20)
                .name("SUMMER-20")
                .active(true)
                .build();
        voucher.setVoucherId(1);
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));

        Voucher result = voucherService.getVoucher(voucherId);

        assertNotNull(result);
        assertEquals(1, voucher.getVoucherId());
    }

    @Test
    @DisplayName("Get voucher throws test")
    void getVoucherThrows() {
        String expected = "No voucher found";

        InvalidVoucherException result = assertThrows(InvalidVoucherException.class,
                () -> voucherService.getVoucher(1));

        assertEquals(expected, result.getMessage());
    }

}
