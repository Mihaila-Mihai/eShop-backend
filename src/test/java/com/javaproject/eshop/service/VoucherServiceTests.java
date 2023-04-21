package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.VoucherDto;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.exceptions.InvalidVoucherException;
import com.javaproject.eshop.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {
    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;
    private Voucher voucher;
    private VoucherDto voucherDto;

    @BeforeEach
    void setup() {
        voucherDto = new VoucherDto("SUMMER-20", 20, true);
        voucher = Voucher.builder()
                .value(20)
                .voucherCode("SUMMER-20")
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Save voucher test")
    void saveVoucher() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        Voucher result = voucherService.saveVoucher(voucherDto);

        assertEquals("SUMMER-20", result.getVoucherCode());
        assertEquals(20, result.getValue());
        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Get voucher success test")
    void getVoucherSuccess() {
        String voucherCode = "SUMMER-20";
        voucher.setVoucherId(1);
        when(voucherRepository.findVoucherByVoucherCode(voucherCode)).thenReturn(Optional.of(voucher));

        Voucher result = voucherService.getVoucher(voucherCode);

        assertNotNull(result);
        assertEquals(1, voucher.getVoucherId());
    }

    @Test
    @DisplayName("Get voucher throws test")
    void getVoucherThrows() {
        String expected = "No voucher found";

        InvalidVoucherException result = assertThrows(InvalidVoucherException.class,
                () -> voucherService.getVoucher("SUMMER-20"));

        assertEquals(expected, result.getMessage());
    }

}
