package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.VoucherDto;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.exceptions.InvalidVoucherException;
import com.javaproject.eshop.repository.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher saveVoucher(VoucherDto voucherDto) {
        Voucher voucher = Voucher.builder()
                .value(voucherDto.getValue())
                .name(voucherDto.getName())
                .active(voucherDto.getIsActive())
                .build();

        return voucherRepository.save(voucher);
    }

    public Voucher getVoucher(int voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new InvalidVoucherException("No voucher found"));
    }
}
