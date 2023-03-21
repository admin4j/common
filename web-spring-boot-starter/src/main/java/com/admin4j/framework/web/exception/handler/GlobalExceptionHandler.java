package com.admin4j.framework.web.exception.handler;

import com.admin4j.common.pojo.AbstractExceptionHandler;
import com.admin4j.framework.web.exception.BaseException;
import com.admin4j.framework.web.exception.BizException;
import com.admin4j.framework.web.pojo.R;
import com.admin4j.framework.web.pojo.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

/**
 * 全局异常处理器
 *
 * @author andanyang
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<R> baseException(BaseException e) {
        log.error("基础异常:" + e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(e.getMessage()));
    }


    @ExceptionHandler(BizException.class)
    public ResponseEntity<R> baseException(BizException e) {
        log.error("业务异常:" + e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<R> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException: getMaxUploadSize:{} getMessage: {}", e.getMaxUploadSize(), e.getMessage());
        long maxUploadSize = e.getMaxUploadSize() / 1024L / 1024L;
        Object[] objects = {maxUploadSize};
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.MAX_UPLOAD_SIZE_EXCEPTION, objects));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<R> handleException(Exception e) {
        log.error(e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(e.getMessage()));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<R> validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.VALIDATED_BIND_EXCEPTION, message));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<R> validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.METHOD_ARGUMENT_NOTVALID_EXCEPTION,
                e.getBindingResult().getFieldError().getField() + message));
    }

    /**
     * SQL 异常
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<R> preAuthorizeException(SQLException e) {
        log.error("SQL异常：" + e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.ERROR_SQL));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<R> nullPointerException(NullPointerException e) {
        log.error("空指针错误：" + e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.ERROR_NULL));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<R> runtimeException(RuntimeException e) {
        log.error("runtime错误：" + e.getMessage(), e);
        handlerException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail(ResponseEnum.ERROR_RUNTIME));
    }
}