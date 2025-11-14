package com.coffeeshop.coffeeshopapi.repository;

import com.coffeeshop.coffeeshopapi.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienRepository extends JpaRepository<NhanVien, String> {

    @Query("SELECT MAX(n.maNV) FROM NhanVien n")
    String findMaxId();

}
