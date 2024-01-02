package me.hamtom.redblue.assignment.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import me.hamtom.redblue.assignment.common.response.Result;
import me.hamtom.redblue.assignment.common.response.SuccessResult;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LookupController {
    private final FileService fileService;
    private static final String RETURN_TAG_URL = "/api/file/return-tag";

    @GetMapping("/api/file/lookup")
    public ResponseEntity<Result> uploadFile(HttpServletRequest request, @RequestParam("filename") String filename) {
        //요청값 유효성 검사
        validFilenameChecker(filename);

        //Image 확인
        fileService.lookupFile(filename);

        // 이미지 파일의 URL을 생성하여 반환
        String imageUrl = String.format("<img src='%s/%s'>", RETURN_TAG_URL, filename);

        //응답 값 생성 및 설정
        LookupRespData uploadRespData = new LookupRespData(filename, imageUrl);
        return ResponseEntity.ok(new SuccessResult(uploadRespData));
    }

    @GetMapping(RETURN_TAG_URL+"/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable(name = "filename") String filename) {
        //요청값 유효성 검사
        validFilenameChecker(filename);

        //Image 가져오기
        Resource resource = fileService.lookupFile(filename);

        // 이미지를 보여주기 위해 MediaType을 IMAGE_JPEG 또는 IMAGE_PNG 등으로 설정
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 또는 다른 이미지 타입 설정
                .body(resource);
    }

    private void validFilenameChecker(String filename) {
        //요청값 NULL, 빈 칸 확인
        if (filename == null || filename.isBlank())
            throw new PredictableRuntimeException("filename 값이 비어있습니다. filename 값은 필수값입니다.");

        //요청값 길이 확인
        if (filename.length() > 200)
            throw new PredictableRuntimeException("filename 값이 너무 깁니다. filename 값은 200자 미만입니다.");

        //요청값 금지 문자 확인
        if (filename.contains("/"))
            throw new PredictableRuntimeException("filename 값에 '/'가 포함될 수 없습니다.");
    }


    @Data
    static class LookupRespData {
        private String filename;
        private String imgSrc;

        public LookupRespData(String filename, String imgSrc) {
            this.filename = filename;
            this.imgSrc = imgSrc;
        }
    }
}
