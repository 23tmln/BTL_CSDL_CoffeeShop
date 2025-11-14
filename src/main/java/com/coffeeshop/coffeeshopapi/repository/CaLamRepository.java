package com.coffeeshop.coffeeshopapi.repository;

import com.coffeeshop.coffeeshopapi.entity.CaLam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface CaLamRepository extends JpaRepository<CaLam, String> {

    @Query("SELECT c.maNV FROM CaLam c WHERE c.ca = :ca AND c.ngayLam = :ngay")
    String findNhanVienByCaAndNgay(String ca, Date ngay);
}
