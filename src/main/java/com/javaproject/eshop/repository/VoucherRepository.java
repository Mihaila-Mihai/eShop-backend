package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Optional<Voucher> findVoucherByVoucherCode(String voucherCode);
}
