package com.coffeeshop.coffeeshopapi.repository;

import com.coffeeshop.coffeeshopapi.entity.ChiTietDonHang;
import com.coffeeshop.coffeeshopapi.entity.ChiTietDonHangId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, ChiTietDonHangId> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ChiTietDonHang WHERE MaDonHang = :maDonHang", nativeQuery = true)
    void deleteByMaDonHang(@Param("maDonHang") String maDonHang);
}
