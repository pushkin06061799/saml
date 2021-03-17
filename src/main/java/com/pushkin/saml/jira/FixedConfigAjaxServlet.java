package com.pushkin.saml.jira;

import com.bitium.saml.servlet.ConfigAjaxServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Denis Migol
 */
public class FixedConfigAjaxServlet extends ConfigAjaxServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("param");
        if (parameter != null) {
            if (parameter.equals("idpRequired")) {
                if (SessionUtils.getSAMLCredentialFromSession(request) != null
                        || SessionUtils.getPluginErrorFromSession(request) != null) {
                    response.getOutputStream().write("false".getBytes());
                    return;
                }
            }
        }
        super.doGet(request, response);
    }
}
