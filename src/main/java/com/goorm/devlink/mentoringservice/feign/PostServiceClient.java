package com.goorm.devlink.mentoringservice.feign;


import com.goorm.devlink.mentoringservice.vo.response.MentoringPostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "post-service")
public interface PostServiceClient {

    @GetMapping
    List<MentoringPostResponse> getPostListForMentoring(@RequestParam List<String> postUuids);
}
