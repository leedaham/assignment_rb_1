package me.hamtom.redblue.assignment.bulk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.bulk.dto.BulkInsertResultDto;
import me.hamtom.redblue.assignment.bulk.dto.UserInfoDto;
import me.hamtom.redblue.assignment.bulk.entity.User;
import me.hamtom.redblue.assignment.bulk.repository.UserBatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBatchRepository userBatchRepository;

    public BulkInsertResultDto bulkInsertUser(List<UserInfoDto> userInfoDtos) {
        //총 요청수 구하기
        int totalCount = userInfoDtos.size();
        log.info("요청 수: {}", totalCount);

        //insert 할 수 없는 값 구하기
        userInfoDtos.removeIf(u -> u.getUsername().isBlank());
        int failCount = userInfoDtos.size();
        log.info("실패 수: {}, username 값 없음", totalCount-failCount);

            //UserInfDto -> User(Entity) 변환 후 한번에 넣을 List 만들기
            List<User> batchList = userInfoDtos.stream()
                    .map(UserInfoDto::getUsername)
                    .map(User::createUser)
                    .toList();

            //만들어진 BulkList insert 후 영속성 컨텍스트 비우기
            userBatchRepository.saveAll(batchList);

        return new BulkInsertResultDto(totalCount, 0, failCount);
    }
}
