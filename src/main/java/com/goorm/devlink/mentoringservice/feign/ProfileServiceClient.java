package com.goorm.devlink.mentoringservice.feign;

import com.goorm.devlink.mentoringservice.vo.request.ScheduleCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profile-service")
public interface ProfileServiceClient {

    /** 스케줄 생성 **/
    @PostMapping("/api/myprofile/schedule")
    public ResponseEntity<Void> setUserCalendarSchedule(@RequestHeader("userUuid") String userUuid,
                                                        @RequestBody ScheduleCreateRequest scheduleCreateRequest);
    /** 스케줄 취소 **/
    @DeleteMapping("/api/myprofile/schedule")
    public ResponseEntity<Void> deleteUserCalendarSchedule(@RequestHeader("userUuid") String userUuid,
                                                           @RequestParam("mentoringUuid") String mentoringUuid);

}
