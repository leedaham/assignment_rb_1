package me.hamtom.redblue.assignment.file;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import me.hamtom.redblue.assignment.common.response.Result;
import me.hamtom.redblue.assignment.common.response.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final FileService fileService;

    @PostMapping("/api/file/upload")
    public ResponseEntity<Result> uploadFile(@RequestParam("file") MultipartFile file) {
        //파일 유효성 검증
        validFileChecker(file);

        //파일 저장 및 저장 이름 가져오기
        String savedFileName = fileService.saveFile(file);

        //응답 값 생성 및 설정
        UploadRespData uploadRespData = new UploadRespData(savedFileName);

        return ResponseEntity.ok(new SuccessResult(uploadRespData));
    }

    private void validFileChecker(MultipartFile file) {
        List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "tiff");
        String wrongFileExtensionMsg = String.format("잘못된 파일 형식입니다. (지원 파일: %s)", allowedImageExtensions);

        //파일 없을 경우
        if (file.isEmpty())
            throw new PredictableRuntimeException("파일이 확인되지 않습니다.");

        //파일 이름 가져오기
        String orgFileName;
        try {
            //파일 이름 없을 경우 확장자 확인 불가
            orgFileName = Objects.requireNonNull(file.getOriginalFilename());
        }catch (NullPointerException e){
            throw new PredictableRuntimeException(wrongFileExtensionMsg);
        }

        //파일 확장자 검사
        String fileExtension = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!allowedImageExtensions.contains(fileExtension))
            throw new PredictableRuntimeException(wrongFileExtensionMsg);

    }

    @Data
    static class UploadRespData {
        private String savedFileName;

        public UploadRespData(String savedFileName) {
            this.savedFileName = savedFileName;
        }

    }
}
