package com.bitium.jira.config;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.bitium.saml.X509Utils;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class ConfigureAction extends JiraWebActionSupport {
	private static final long serialVersionUID = 1L;

	private String loginUrl;
	private String logoutUrl;
	private String entityId;
	private String uidAttribute;
	private String transformToLogin;
	private String autoCreateUser;
	private String defaultAutoCreateUserGroup;
	private String x509Certificate;
	private String idpRequired;
	private String maxAuthenticationAge;
	private String success = "";
	private String submitAction;
	private ArrayList<String> existingGroups;

	private SAMLJiraConfig saml2Config;

	public void setSaml2Config(SAMLJiraConfig saml2Config) {
		this.saml2Config = saml2Config;
	}

	public ConfigureAction() {
	}

	public String getMaxAuthenticationAge() {
		return maxAuthenticationAge;
	}

	public void setMaxAuthenticationAge(String maxAuthenticationAge) {
		this.maxAuthenticationAge = maxAuthenticationAge;
	}

	public String getIdpRequired() {
		return idpRequired;
	}

	public void setIdpRequired(String idpRequired) {
		this.idpRequired = idpRequired;
	}

	public String getX509Certificate() {
		return x509Certificate;
	}

	public void setX509Certificate(String x509Certificate) {
		this.x509Certificate = x509Certificate;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getUidAttribute() {
		return uidAttribute;
	}

	public void setUidAttribute(String uidAttribute) {
		this.uidAttribute = uidAttribute;
	}

	public String getTransformToLogin() {
		return transformToLogin;
	}

	public void setTransformToLogin(String transformToLogin) {
		this.transformToLogin = transformToLogin;
	}

	public String getAutoCreateUser() {
		return autoCreateUser;
	}

	public void setAutoCreateUser(String autoCreateUser) {
		this.autoCreateUser = autoCreateUser;
	}

	public String getDefaultAutoCreateUserGroup() {
		return defaultAutoCreateUserGroup;
	}

	public void setDefaultAutoCreateUserGroup(String defaultAutoCreateUserGroup) {
		this.defaultAutoCreateUserGroup = defaultAutoCreateUserGroup;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public ArrayList<String> getExistingGroups() {
		GroupManager groupManager = ComponentAccessor.getGroupManager();
		Collection<Group> groupObjects = groupManager.getAllGroups();
		existingGroups = new ArrayList<String>();
		for (Group groupObject : groupObjects) {
			existingGroups.add(groupObject.getName());
		}
		setExistingGroups(existingGroups);
		return existingGroups;
	}

	public void setExistingGroups(ArrayList<String> existingGroups) {
		this.existingGroups = existingGroups;
	}

	public String getSubmitAction() {
		return submitAction;
	}

	public void setSubmitAction(String submitAction) {
		this.submitAction = submitAction;
	}

	public void doValidation() {
		setSuccess("");
		if (getSubmitAction() == null || getSubmitAction().equals("")) {
			return;
		}
		if (!isSystemAdministrator()) {
			addErrorMessage(getText("saml2Plugin.admin.notAdministrator"));
		}
		if (StringUtils.isBlank(getLoginUrl())) {
			addErrorMessage(getText("saml2Plugin.admin.loginUrlEmpty"));
		} else {
			try {
				new URL(getLoginUrl());
			} catch (MalformedURLException e) {
				addErrorMessage(getText("saml2Plugin.admin.loginUrlInvalid"));
			}
		}
		if (StringUtils.isBlank(getLogoutUrl())) {
			//addActionError(getText("saml2Plugin.admin.logoutUrlEmpty"));
		} else {
			try {
				new URL(getLogoutUrl());
			} catch (MalformedURLException e) {
				addErrorMessage(getText("saml2Plugin.admin.logoutUrlInvalid"));
			}
		}
		if (StringUtils.isBlank(getEntityId())) {
			addErrorMessage(getText("saml2Plugin.admin.entityIdEmpty"));
		}
		if (StringUtils.isBlank(getUidAttribute())) {
			addErrorMessage(getText("saml2Plugin.admin.uidAttributeEmpty"));
		}
		if (StringUtils.isBlank(getX509Certificate())) {
			addErrorMessage(getText("saml2Plugin.admin.x509CertificateEmpty"));
		} else {
			try {
				X509Utils.generateX509Certificate(getX509Certificate());
			} catch (Exception e) {
				addErrorMessage(getText("saml2Plugin.admin.x509CertificateInvalid"));
			}
		}
		if (StringUtils.isBlank(getIdpRequired())) {
			setIdpRequired("false");
		} else {
			setIdpRequired("true");
		}
		if (StringUtils.isBlank(getAutoCreateUser())) {
			setAutoCreateUser("false");
		} else {
			setAutoCreateUser("true");
		}
		if (StringUtils.isBlank(getTransformToLogin())) {
			setTransformToLogin("false");
		} else {
			setTransformToLogin("true");
		}

		if(!StringUtils.isNumeric(getMaxAuthenticationAge()) || StringUtils.isBlank(getMaxAuthenticationAge())){
			addErrorMessage(getText("saml2Plugin.admin.maxAuthenticationAgeInvalid"));
		}

	}


	public String doExecute() throws Exception {
		if (getSubmitAction() == null || getSubmitAction().equals("")) {
			setLoginUrl(saml2Config.getLoginUrl());
			setLogoutUrl(saml2Config.getLogoutUrl());
			setEntityId(saml2Config.getIdpEntityId());
			setUidAttribute(saml2Config.getUidAttribute());
			setX509Certificate(saml2Config.getX509Certificate());
			String idpRequired = saml2Config.getIdpRequired();
			if (idpRequired != null) {
				setIdpRequired(idpRequired);
			} else {
				setIdpRequired("false");
			}
			String autoCreateUser = saml2Config.getAutoCreateUser();
			if (autoCreateUser != null) {
				setAutoCreateUser(autoCreateUser);
			} else {
				setAutoCreateUser("false");
			}
			String transformToLogin = saml2Config.getTransformToLogin();
			if(transformToLogin !=null){
				setTransformToLogin(transformToLogin);
			}else {
				setTransformToLogin("false");
			}

			String defaultAutocreateUserGroup = saml2Config.getAutoCreateUserDefaultGroup();
			if (defaultAutocreateUserGroup.isEmpty()) {
				// NOTE: Set the default to "jira-users".
				// This is used when configuring the plugin for the first time and no default was set
				defaultAutocreateUserGroup = SAMLJiraConfig.DEFAULT_AUTOCREATE_USER_GROUP;
			}
			setDefaultAutoCreateUserGroup(defaultAutocreateUserGroup);
			setMaxAuthenticationAge(String.valueOf(saml2Config.getMaxAuthenticationAge()));
			return "success";
		}
		saml2Config.setLoginUrl(getLoginUrl());
		saml2Config.setLogoutUrl(getLogoutUrl());
		saml2Config.setEntityId(getEntityId());
		saml2Config.setTransformToLogin(getTransformToLogin());
		saml2Config.setUidAttribute(getUidAttribute());
		saml2Config.setX509Certificate(getX509Certificate());
		saml2Config.setIdpRequired(getIdpRequired());
		saml2Config.setAutoCreateUser(getAutoCreateUser());
		saml2Config.setAutoCreateUserDefaultGroup(getDefaultAutoCreateUserGroup());
		saml2Config.setMaxAuthenticationAge(Integer.parseInt(getMaxAuthenticationAge()));

		setSuccess("success");
		return "success";
	}

}
