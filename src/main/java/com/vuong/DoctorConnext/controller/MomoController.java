package com.vuong.DoctorConnext.controller;

import com.vuong.DoctorConnext.dto.request.MomoIPNRequest;
import com.vuong.DoctorConnext.dto.response.MomoCreateResponse;
import com.vuong.DoctorConnext.service.MomoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/momo")
@CrossOrigin(origins = "http://localhost:5175")
public class MomoController {
    private final MomoService momoService;

    @PostMapping("/create/{appointmentId}")
    public ResponseEntity<MomoCreateResponse> createMomoPayment(@PathVariable String appointmentId) {
        try {
            MomoCreateResponse response = momoService.createQR(appointmentId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Lỗi khi tạo thanh toán MoMo: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/ipn-handler")
    public ResponseEntity<String> handleMomoIpn(@RequestBody MomoIPNRequest request) {
        return momoService.handleIpn(request);
    }

}
