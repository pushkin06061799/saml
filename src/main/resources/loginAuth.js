AJS.$(function () {
    setTimeout(function () {
        if (AJS.$("#login-form").length) {
            loadCorpLogin(AJS.$("#login-form"));
        } else if (AJS.$("#loginform").length) {
            loadCorpLogin(AJS.$("#loginform"));
        } else {
            AJS.$("iframe").ready(function () {
                var iframe = AJS.$("#gadget-0");
                iframe.load(function() {
                    loadCorpLogin(AJS.$("#" + iframe[0].id).contents().find("#loginform"));
                });
            });
        } // Your code here
    }, 200);

    function loadCorpLogin(loginForm) {
        if (loginForm.length == 1) {
            var loginFormId = loginForm[0].id;
            loginForm.hide();

            if (loginFormId == "login-form" || loginFormId == "loginform") {
                AJS.$('<div class="field-group"><a class="aui-button aui-style aui-button-primary" href="' + AJS.contextPath() + '/plugins/servlet/saml/auth" style="align:center;">Use Corporate login</a></div><h2 style="margin-top:10px"></h2>').insertBefore(AJS.$("#" + loginFormId + " .field-group:first-child"));
            } else {
                AJS.$('<div class="field-group"><a class="aui-button aui-style aui-button-primary" href="' + AJS.contextPath() + '/plugins/servlet/saml/auth" style="margin-left:100px;margin-top:5px;">Use Corporate login</a></div>').insertBefore(AJS.$("#gadget-0"));
            }

            var query = location.search.substr(1);
            query.split("&").forEach(function(part) {
                var item = part.split("=");
                if (item.length == 2 && item[0] == "samlerror") {
                    var errorKeys = {};
                    errorKeys["general"] = "General SAML configuration error";
                    errorKeys["user_not_found"] = "You are not authorized. Please submit ServiceDesk ticket, provide your Financial Business Unit and manager`s approval (if you are not FBU owner)."; //"User was not found";
                    errorKeys["plugin_exception"] = "SAML plugin internal error";
                    loginForm.show();
                    var message = '<div class="aui-message closeable error">' + errorKeys[item[1]] + '</div>';
                    AJS.$(message).insertBefore(loginForm);
                }
            });

            if (location.search == '?logout=true') {
                $.ajax({
                    url: AJS.contextPath() + "/plugins/servlet/saml/getajaxconfig?param=logoutUrl",
                    type: "GET",
                    error: function() {},
                    success: function(response) {
                        if (response != "") {
                            AJS.$('<p>Please wait while we redirect you to your company log out page</p>').insertBefore(loginForm);
                            window.location.href = response;
                            return;
                        }
                    }
                });
                return;
            }

            if (document.referrer && document.referrer.indexOf(location.protocol + '//' + location.host + location.port + AJS.contextPath() + '/secure/Logout!default.jspa') == 0) {
                loginForm.show();
                return;
            }
            if (loginForm.find("div .error").length === 1 || loginForm.prev().is("div .error")) {
                loginForm.show();
                return;
            }

            AJS.$.ajax({
                url: AJS.contextPath() + "/plugins/servlet/saml/getajaxconfig?param=idpRequired",
                type: "GET",
                error: function() {},
                success: function(response) {
                    if (response == "true") {
                        // AJS.$('<img src="download/resources/com.bitium.confluence.SAML2Plugin/images/progress.png"/>').insertBefore(AJS.$(".aui.login-form-container"));
                        AJS.$('<p>Please wait while we redirect you to your company log in page</p>').insertBefore(loginForm);
                        window.location.href = AJS.contextPath() + '/plugins/servlet/saml/auth';
                    } else {
                        loginForm.show();
                    }
                }
            });

        }
    }

});