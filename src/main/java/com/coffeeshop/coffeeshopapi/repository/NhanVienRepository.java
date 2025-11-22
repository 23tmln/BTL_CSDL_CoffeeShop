package com.coffeeshop.coffeeshopapi.repository;

import com.coffeeshop.coffeeshopapi.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, String> {

    @Query("SELECT MAX(n.maNV) FROM NhanVien n")
    String findMaxId();

    @Query("""
           SELECT n FROM NhanVien n
           WHERE n.maNV LIKE %:keyword%
              OR n.tenNV LIKE %:keyword%
              OR n.sdt LIKE %:keyword%
           """)
    List<NhanVien> search(@Param("keyword") String keyword);
}
