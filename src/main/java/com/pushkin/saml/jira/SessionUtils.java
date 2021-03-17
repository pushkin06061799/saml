package com.pushkin.saml.jira;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.security.saml.SAMLCredential;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final Logger LOGGER = Logger.getLogger(SessionUtils.class);

    private static final String PLUGIN_ERROR_ATTRIBUTE_NAME = "saml.plugin.error";
    private static final String OS_DESTINATION_ATTRIBUTE_NAME = "os_destination";
    private static final String SAML_CREDENTIAL_ATTRIBUTE_NAME = "SAMLCredential";

    //
    // Plugin error
    //

    public static void storePluginErrorInSession(HttpServletRequest request, Exception e) {
        storeAttributeInSession(request, PLUGIN_ERROR_ATTRIBUTE_NAME, e);
    }

    @Nullable
    public static Exception getPluginErrorFromSession(HttpServletRequest request) {
        return (Exception) getAttributeFromSession(request, PLUGIN_ERROR_ATTRIBUTE_NAME);
    }

    public static void removePluginErrorFromSession(HttpServletRequest request) {
        removeAttributeFromSession(request, PLUGIN_ERROR_ATTRIBUTE_NAME);
    }

    //
    // SAMLCredential
    //

    public static void storeSAMLCredentialInSession(HttpServletRequest request, SAMLCredential samlCredential) {
        storeAttributeInSession(request, SAML_CREDENTIAL_ATTRIBUTE_NAME, samlCredential);
    }

    @Nullable
    public static SAMLCredential getSAMLCredentialFromSession(HttpServletRequest request) {
        return (SAMLCredential) getAttributeFromSession(request, SAML_CREDENTIAL_ATTRIBUTE_NAME);
    }

    //
    // os_destination
    //

    public static void storeOsDestinationInSession(HttpServletRequest request, String osDestination) {
        storeAttributeInSession(request, OS_DESTINATION_ATTRIBUTE_NAME, osDestination);
    }

    @Nullable
    public static String getOsDestinationFromSession(HttpServletRequest request) {
        Object osDestination = getAttributeFromSession(request, OS_DESTINATION_ATTRIBUTE_NAME);
        return ObjectUtils.toString(osDestination, null);
    }

    //
    // Internal methods
    //

    private static void storeAttributeInSession(@Nonnull HttpServletRequest request, @Nonnull String name, @Nullable Object value) {
        final HttpSession session = request.getSession();
        if (session != null) {
            try {
                session.setAttribute(name, value);
            } catch (Exception e) {
                LOGGER.error(String.format("Error during saving session attribute (%s,%s): %s", name, value, e.getMessage()), e);
            }
        }
    }

    @Nullable
    private static Object getAttributeFromSession(@Nonnull HttpServletRequest request, @Nonnull String name) {
        final HttpSession session = request.getSession();
        if (session != null) {
            try {
                return session.getAttribute(name);
            } catch (Exception e) {
                LOGGER.error(String.format("Error during getting session attribute (%s): %s", name, e.getMessage()), e);
            }
        }
        return null;
    }

    private static void removeAttributeFromSession(@Nonnull HttpServletRequest request, @Nonnull String name) {
        final HttpSession session = request.getSession();
        if (session != null) {
            try {
                session.removeAttribute(name);
            } catch (Exception e) {
                LOGGER.error(String.format("Error during removing session attribute (%s): %s", name, e.getMessage()), e);
            }
        }
    }
}
