package com.bitium.jira.config;

import com.bitium.saml.config.SAMLConfig;

import java.io.Serializable;

public class SAMLJiraConfig extends SAMLConfig implements Serializable {

	public static final String DEFAULT_AUTOCREATE_USER_GROUP = "jira-users";
    private static final long serialVersionUID = -4397705615929039406L;

    public String getAlias() {
		return "confluenceSAML";//jiraSAML";
	}
}
