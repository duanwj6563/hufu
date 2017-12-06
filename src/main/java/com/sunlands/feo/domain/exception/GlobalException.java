package com.sunlands.feo.domain.exception;

/**
 * Created by yangchao on 17/11/18.
 */
public abstract class GlobalException extends RuntimeException {

    public abstract Integer getStatus();
    public abstract String getError();
    public abstract String getMessage();

}
