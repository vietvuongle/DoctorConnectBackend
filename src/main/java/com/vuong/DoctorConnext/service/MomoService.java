package com.vuong.DoctorConnext.service;

import com.vuong.DoctorConnext.client.MomoApi;
import com.vuong.DoctorConnext.dto.request.MomoCreateRequest;
import com.vuong.DoctorConnext.dto.request.MomoIPNRequest;
import com.vuong.DoctorConnext.dto.response.MomoCreateResponse;
import com.vuong.DoctorConnext.entity.Appointment;
import com.vuong.DoctorConnext.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomoService {

    @Value("${momo.partner-code}")
    private String PARTNER_CODE;

    @Value("${momo.access-key}")
    private String ACCESS_KEY;

    @Value("${momo.secret-key}")
    private String SECRET_KEY;

    @Value("${momo.return-url}")
    private String REDIRECT_URL;

    @Value("${momo.ipn-url}")
    private String IPN_URL;

    @Value("${momo.request-type}")
    private String REQUEST_TYPE;

    private final MomoApi momoApi;
    private final AppointmentRepository appointmentRepository;

    public MomoCreateResponse createQR(String appointmentId){

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        String orderId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toan don hang: " + orderId;
        String requestId = UUID.randomUUID().toString();
        String extraData = appointmentId;
        long amount = appointment.getPrice();

        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                ACCESS_KEY, amount, extraData, IPN_URL, orderId, orderInfo, PARTNER_CODE, REDIRECT_URL, requestId, REQUEST_TYPE);


        String prettySignature = "";
        try {
            prettySignature = signHmacSHA256(rawSignature, SECRET_KEY);

        } catch (Exception e) {
            log.error("Co loi khi hash code: " + e);
            return null;
        }

        if(prettySignature.isBlank()) {
            log.error("signature is bland");
        }

        MomoCreateRequest request = MomoCreateRequest.builder()
                .partnerCode(PARTNER_CODE)
                .requestType(REQUEST_TYPE)
                .ipnUrl(IPN_URL)
                .redirectUrl(REDIRECT_URL)
                .orderId(orderId)
                .orderInfo(orderInfo)
                .requestId(requestId)
                .extraData(extraData)
                .amount(amount)
                .signature(prettySignature)
                .lang("vi")
                .build();
        return momoApi.createMomoQR(request);
    }

    public ResponseEntity<String> handleIpn(MomoIPNRequest request) {
        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&message=%s&orderId=%s&orderInfo=%s&orderType=%s&partnerCode=%s&payType=%s&requestId=%s&responseTime=%s&resultCode=%s&transId=%s",
                ACCESS_KEY, request.getAmount(), request.getExtraData(), request.getMessage(), request.getOrderId(),
                request.getOrderInfo(), request.getOrderType(), request.getPartnerCode(), request.getPayType(),
                request.getRequestId(), request.getResponseTime(), request.getResultCode(), request.getTransId()
        );

        try {
            String computedSignature = signHmacSHA256(rawSignature, SECRET_KEY);
            if (!computedSignature.equals(request.getSignature())) {
                log.warn("Invalid signature in IPN");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            }

            // ✅ Signature hợp lệ → update DB
            String appointmentId = request.getExtraData();

            log.info("appointmentId: {}", appointmentId);


            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
            if (optionalAppointment.isPresent()) {
                Appointment appointment = optionalAppointment.get();
                if (request.getResultCode() == 0) {
                    appointment.setPayment(true);
                    appointment.setConfirm(true);

                    appointmentRepository.save(appointment);

                    log.info("Cập nhật thanh toán thành công cho appointmentId {}", appointmentId);
                } else {
                    log.warn("MoMo báo thanh toán thất bại cho appointmentId {}", appointmentId);
                }
            }

            return ResponseEntity.ok("IPN received");
        } catch (Exception e) {
            log.error("Lỗi xử lý IPN: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }

    private String signHmacSHA256(String data, String key) throws Exception {
        // Create a Mac instance using HmacSHA256 algorithm
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        // Prepare the secret key
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretKey);
        // Compute the HMAC on the input data
        byte[] hash = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        // Convert the result to a hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

