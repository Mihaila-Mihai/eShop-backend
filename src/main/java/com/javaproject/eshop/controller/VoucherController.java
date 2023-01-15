package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.VoucherDto;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.service.VoucherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/voucher")
    public ResponseEntity<Voucher> saveVoucher(@RequestBody @Valid VoucherDto voucherDto) {
        Voucher voucher = voucherService.saveVoucher(voucherDto);
        return ResponseEntity.created(URI.create("/voucher" + voucher.getVoucherId())).body(voucher);
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable int voucherId) {
        return ResponseEntity.ok(voucherService.getVoucher(voucherId));
    }
}
