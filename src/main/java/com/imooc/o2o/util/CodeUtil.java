package com.imooc.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean compare(HttpServletRequest request) {
        String realCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        String code = request.getParameter("verifyCodeActual");

        return code != null && code.equalsIgnoreCase(realCode);
    }
}
