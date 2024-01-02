package me.hamtom.redblue.assignment.common;

import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import me.hamtom.redblue.assignment.common.response.ErrorResult;
import me.hamtom.redblue.assignment.common.response.FailResult;
import me.hamtom.redblue.assignment.common.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외 발생 처리
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    public static final String FAIL_MSG = "요청이 실패했습니다. 다시 시도 해주세요. 계속해서 문제가 발생한다면 관리자에게 문의해주십시오.";

    /**
     * HTTP Method 잘못된 경우 발생하는 Exception
     * @param ex HttpRequestMethodNotSupportedException
     * @return 실패 응답 (HttpStatus.BAD_REQUEST)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> handleMethodNotSupportException(HttpRequestMethodNotSupportedException ex) {
        String message = ex.getMessage();
        log.warn(message, ex);
        return ResponseEntity.badRequest().body(new FailResult(message));
    }

    /**
     * 직접 정의한 Exception, 예상 가능한 예외 처리에 사용
     * @param ex 직접 정의한 Exception
     * @return 실패 응답 (HttpStatus.BAD_REQUEST)
     */
    @ExceptionHandler(PredictableRuntimeException.class)
    public ResponseEntity<Result> handleCustomException(PredictableRuntimeException ex) {
        String message = ex.getMessage();
        log.warn(message, ex);
        return ResponseEntity.badRequest().body(new FailResult(message));
    }

    /**
     * 앞에서 걸려진 예외 외의 예외로 예상하지 못한 Exception
     * @param ex Exception
     * @return 오류 응답 (HttpStatus.INTERNAL_SERVER_ERROR)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        return ResponseEntity.internalServerError().body(new ErrorResult(FAIL_MSG));
    }

}
