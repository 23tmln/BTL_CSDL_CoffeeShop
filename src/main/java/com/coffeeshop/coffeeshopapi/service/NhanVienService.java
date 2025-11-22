package com.coffeeshop.coffeeshopapi.service;

import com.coffeeshop.coffeeshopapi.dto.NhanVienRequest;
import com.coffeeshop.coffeeshopapi.dto.NhanVienResponse;
import com.coffeeshop.coffeeshopapi.entity.NhanVien;
import com.coffeeshop.coffeeshopapi.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NhanVienService {

    private final NhanVienRepository nvRepo;

    // Tạo mã NV tự động
    private String generateMaNV() {
        String maxId = nvRepo.findMaxId();
        if (maxId == null) return "NV01";

        int num = Integer.parseInt(maxId.substring(2));
        num++;
        return String.format("NV%02d", num);
    }

    // CREATE NV
    public NhanVienResponse create(NhanVienRequest req) {
        NhanVien nv = new NhanVien();

        nv.setMaNV(generateMaNV());
        nv.setTenNV(req.getTenNV());
        nv.setSdt(req.getSdt());
        nv.setGioiTinh(req.getGioiTinh());
        nv.setNgaySinh(req.getNgaySinh());

        // MẶC ĐỊNH
        nv.setLuong(4000000);
        nv.setTrangThaiLamViec("Đang làm");
        nv.setChucVu("Phục vụ");

        return convertToDto(nvRepo.save(nv));
    }

    // GET ALL
    public List<NhanVienResponse> getAll() {
        return nvRepo.findAll().stream().map(this::convertToDto).toList();
    }

    // GET BY ID
    public NhanVienResponse getById(String id) {
        NhanVien nv = nvRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên: " + id));
        return convertToDto(nv);
    }

    // UPDATE
    public NhanVienResponse update(String id, NhanVienRequest req) {
        NhanVien nv = nvRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên: " + id));

        nv.setTenNV(req.getTenNV());
        nv.setSdt(req.getSdt());
        nv.setGioiTinh(req.getGioiTinh());
        nv.setNgaySinh(req.getNgaySinh());
        nv.setLuong(req.getLuong());
        nv.setTrangThaiLamViec(req.getTrangThaiLamViec());
        nv.setChucVu(req.getChucVu());

        return convertToDto(nvRepo.save(nv));
    }

    // DELETE
    public void delete(String id) {
        nvRepo.deleteById(id);
    }

    // SEARCH
    public List<NhanVienResponse> search(String keyword) {
        return nvRepo.search(keyword).stream()
                .map(this::convertToDto)
                .toList();
    }

    // Convert Entity → DTO
    private NhanVienResponse convertToDto(NhanVien nv) {
        NhanVienResponse dto = new NhanVienResponse();

        dto.setMaNV(nv.getMaNV());
        dto.setTenNV(nv.getTenNV());
        dto.setSdt(nv.getSdt());
        dto.setGioiTinh(nv.getGioiTinh());
        dto.setNgaySinh(nv.getNgaySinh());
        dto.setLuong(nv.getLuong());
        dto.setTrangThaiLamViec(nv.getTrangThaiLamViec());
        dto.setChucVu(nv.getChucVu());

        return dto;
    }
}
