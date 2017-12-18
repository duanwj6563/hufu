package com.feo.common;

import com.feo.domain.exception.UserDefinedException;
import com.feo.domain.exception.ServerStatus;
import org.springframework.validation.BindingResult;

/**
 * Created by yangchao on 17/11/7.
 */
public class AssertUtil {

    public static boolean isNotNull(Object obj) {
        if (StrUtil.isNull(obj))
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY);

        return true;
    }

    public static boolean isNotNull(Object obj, String msg) {
        if (StrUtil.isNull(obj))
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY, msg);

        return true;
    }

    public static void bindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION, bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
