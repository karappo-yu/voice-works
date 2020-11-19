package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.entity.vo.ViewResult;
import com.yu.voiceworks.exception.SSYKException;
import com.yu.voiceworks.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j //TODO 记录日志
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViewResult<String>  handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        StringBuilder errBuilder = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(err->{
            errBuilder.append(err.getField()).append(":").append(err.getDefaultMessage());
            errBuilder.append("/n");
        });

        //构建返回结果对象
        return ResultVoUtil.getResultVo(HttpStatus.BAD_REQUEST.value(),errBuilder.toString());
    }
    @ExceptionHandler(SSYKException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViewResult<String> SSYKException(SSYKException e){
        //TODO 记录日志
        return ResultVoUtil.getResultVo(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViewResult<String> handleConstraintViolationException(ConstraintViolationException e) {


        return ResultVoUtil.getResultVo(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViewResult<String> handleDefaultException(Exception e) {

        return ResultVoUtil.getResultVo(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }
}

