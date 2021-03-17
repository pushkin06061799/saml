package com.pushkin.saml.servlet;

import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServlet;

public abstract class BaseHttpServlet extends HttpServlet {
    protected final Logger log = Logger.getLogger(this.getClass());

    protected void logPluginError(@Nullable Exception exception) {
        if (exception != null) {
            log.error("SAML plugin error: " + exception.getMessage(), exception);
        }
    }
}
