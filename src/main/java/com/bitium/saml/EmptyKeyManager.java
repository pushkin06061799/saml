package com.bitium.saml;

import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Set;

import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.springframework.security.saml.key.KeyManager;

public class EmptyKeyManager implements KeyManager {

	@Override
	public Iterable<Credential> resolve(CriteriaSet arg0)
			throws SecurityException {
		return null;
	}

	@Override
	public Credential resolveSingle(CriteriaSet arg0) throws SecurityException {
		return null;
	}

	@Override
	public Credential getCredential(String keyName) {
		return null;
	}

	@Override
	public Credential getDefaultCredential() {
		return null;
	}

	@Override
	public String getDefaultCredentialName() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getAvailableCredentials() {
		return Collections.EMPTY_SET;
	}

	@Override
	public X509Certificate getCertificate(String alias) {
		return null;
	}
}
