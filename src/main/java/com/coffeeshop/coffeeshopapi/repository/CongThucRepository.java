package com.coffeeshop.coffeeshopapi.repository;

import com.coffeeshop.coffeeshopapi.entity.CongThuc;
import com.coffeeshop.coffeeshopapi.entity.CongThucId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CongThucRepository extends JpaRepository<CongThuc, CongThucId> {


    List<CongThuc> findByMaSP(String maSP);
}
