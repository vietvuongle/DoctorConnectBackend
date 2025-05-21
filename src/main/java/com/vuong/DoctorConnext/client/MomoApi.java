package com.vuong.DoctorConnext.client;

import com.vuong.DoctorConnext.dto.request.MomoCreateRequest;
import com.vuong.DoctorConnext.dto.response.MomoCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momo", url = "${momo.end-point}")
public interface MomoApi {
    @PostMapping("/create")
    MomoCreateResponse createMomoQR(@RequestBody MomoCreateRequest momoCreateRequest);
}
