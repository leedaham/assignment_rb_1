package me.hamtom.redblue.assignment.bulk;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.bulk.dto.BulkInsertResultDto;
import me.hamtom.redblue.assignment.bulk.dto.UserInfoDto;
import me.hamtom.redblue.assignment.bulk.entity.User;
import me.hamtom.redblue.assignment.bulk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    public BulkInsertResultDto bulkInsertUser(List<UserInfoDto> userInfoDtos) {
        //총 요청수 구하기
        int totalCount = userInfoDtos.size();
        log.info("요청 수: {}", totalCount);

        //insert 할 수 없는 값 구하기
        userInfoDtos.removeIf(u -> u.getUsername().isBlank());
        int failCount = userInfoDtos.size();
        log.info("실패 수: {}, username 값 없음", totalCount-failCount);

        //insert
        int batchSize = 500;
        int successCount = 0;
        while (!userInfoDtos.isEmpty()) {

            //UserInfDto -> User(Entity) 변환 후 한번에 넣을 List 만들기
            List<User> batchList = userInfoDtos.stream()
                    .map(UserInfoDto::getUsername)
                    .map(User::createUser)
                    .limit(batchSize)
                    .toList();

            //만들어진 BulkList insert 후 영속성 컨텍스트 비우기
            userRepository.saveAllAndFlush(batchList);
            em.clear();

            int batchInsertSize = batchList.size();
            log.info("insert count: {}", batchInsertSize);
            successCount += batchInsertSize;

            //기존 list 에서 insert 한 항목 지우기
            userInfoDtos.subList(0, batchInsertSize).clear();
        }
        log.info("성공 수: {}", successCount);

        return new BulkInsertResultDto(totalCount, successCount, failCount);
    }
}
