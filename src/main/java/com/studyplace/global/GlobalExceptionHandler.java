package com.studyplace.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(
            NotFoundException e
    ) {
        return new ApiErrorResponse(
                404,
                "Not Found",
                e.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        return new ApiErrorResponse(
                400,
                "Bad Request",
                e.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(
            Exception e
    ) {
        return new ApiErrorResponse(
                500,
                "Internal Server Error",
                "서버 내부 오류가 발생했습니다."
        );
    }
}