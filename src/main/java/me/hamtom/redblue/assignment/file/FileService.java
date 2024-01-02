package me.hamtom.redblue.assignment.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static me.hamtom.redblue.assignment.common.ControllerExceptionHandler.FAIL_MSG;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.path}")
    private String fileDir; //파일 저장 위치

    public String saveFile(MultipartFile file) {
        //저장 파일명
        String saveFileName = "";

        //디렉토리 확인
        checkDirectory();

        //파일명 중복시 재시도
        int retryCount=0;
        int retryLimit = 100;
        while (retryCount < retryLimit) {
            try {
                //저장 파일명 변경
                saveFileName = makeSaveFileName(file.getOriginalFilename());

                //파일 저장 경로 설정
                String filePath = fileDir + File.separator + saveFileName;
                File saveTo = new File(filePath);

                //파일 중복 여부 확인
                if (!saveTo.exists()) {
                    //파일 저장
                    file.transferTo(saveTo);
                    break;
                }

                //재시도 진행
                retryCount++;
                //일정 횟수 이상해도 중복이라면 재요청
                if (retryCount == retryLimit)
                    throw new PredictableRuntimeException(FAIL_MSG);

                log.warn("파일명 중복으로 재시도, 재시도 횟수: {}", retryCount);
            } catch (IOException e) {
                //파일 저장 실패 시 처리
                throw new RuntimeException();
            }
        }

        return saveFileName;
    }

    /**
     * 파일 저장 디렉토리 확인
     */
    private void checkDirectory() {
        //파일 디렉토리 확인 후 없을시 생성
        Path filePath = Path.of(this.fileDir);
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectory(filePath);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * 저장 파일명 생성
     * @param originalFilename 기존 파일명
     * @return 저장 파일명
     */
    private String makeSaveFileName(String originalFilename) {

        //현재 날짜 시간 가져오기
        LocalDateTime now = LocalDateTime.now();
        //날짜 시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        //날짜 시간을 문자열로 변환
        String formattedDateTime = now.format(formatter);

        //4자리의 랜덤 숫자 생성
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        //저장 파일명 리턴
        return formattedDateTime + randomNumber +"_"+ originalFilename;
    }

    /**
     * 파일 조회
     * @param filename 조회 파일명
     * @return 조회 파일
     */
    public Resource lookupFile(String filename) {

        //파일 조회
        Resource resource;
        try {
            Path filePath = Paths.get(fileDir).resolve(filename).normalize();
            resource = new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException();
        }

        //파일 없을 경우 예외 발생
        if (!resource.exists())
            throw new PredictableRuntimeException("파일을 찾을 수 없습니다.");

        return resource;
    }

}
