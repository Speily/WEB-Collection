package com.kaishengit.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 访问没有权限的数据，抛出次异常HTTP 403
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UnableException extends RuntimeException {
}
