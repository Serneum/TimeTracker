package com.example.domain.tasks;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class BaseServlet extends HttpServlet {

    protected String getSanitizedValue(HttpServletRequest req, String param) {
        String val = req.getParameter(param);
        return StringUtils.trimToEmpty(val);
    }

    protected boolean getBoolean(HttpServletRequest req, String param) {
        String val = getSanitizedValue(req, param);
        return val.equals("1") || val.equals("yes") || val.equals("on") || Boolean.valueOf(val);
    }
}
