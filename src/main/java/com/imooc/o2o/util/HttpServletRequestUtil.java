package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static String getString(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (value == null || "".equals(value)) {
            return null;
        }

        return value.trim();
    }

    public static long getLong(HttpServletRequest request, String key) {
        String parameter = request.getParameter(key);

        long l;
        try {
            l = Long.parseLong(parameter);
        } catch (NumberFormatException e) {
            l = -1;
        }
        return l;
    }
}
