package me.hamtom.redblue.assignment.bulk;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.bulk.dto.BulkInsertResultDto;
import me.hamtom.redblue.assignment.bulk.dto.UserInfoDto;
import me.hamtom.redblue.assignment.common.response.Result;
import me.hamtom.redblue.assignment.common.response.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/insert/bulk")
    public ResponseEntity<Result> bulkInsert(@RequestBody BulkInsertReq bulkInsertReq) {
        log.info("Bulk Insert 요청, userInfoDtos: {}", bulkInsertReq.userInfoDtos);

        //Bulk insert 시작
        BulkInsertResultDto bulkInsertResultDto = userService.bulkInsertUser(bulkInsertReq.userInfoDtos);

        log.info("Bulk Insert 응답, bulkInsertResultDto: {}", bulkInsertResultDto);
        return ResponseEntity.ok(new SuccessResult(bulkInsertResultDto));
    }

    @Data
    static class BulkInsertReq {
        private List<UserInfoDto> userInfoDtos;
    }
}
