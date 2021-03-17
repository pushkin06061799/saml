package com.bitium.saml;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.bitium.saml.config.SAMLConfig;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.Configuration;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataMemoryProvider;

public class SpMetadataGenerator {
	public MetadataProvider generate(SAMLConfig configuration) throws ServletException, MetadataProviderException {
	
    MetadataGenerator generator = new MetadataGenerator();
    generator.setKeyManager(new EmptyKeyManager());

    // Defaults
    String alias = configuration.getAlias();
    String baseURL = configuration.getBaseUrl();

    generator.setEntityBaseURL(baseURL);
    List<String> ssoBindings = new ArrayList<String>();
    ssoBindings.add("post");
    generator.setBindingsSSO(ssoBindings);

    // Use default entityID if not set
    if (generator.getEntityId() == null) {
        generator.setEntityId(configuration.getSpEntityId());
    }
    
    Configuration.getGlobalSecurityConfiguration().getKeyInfoGeneratorManager().getManager("MetadataKeyInfoGenerator");
    
    EntityDescriptor descriptor = generator.generateMetadata();
    ExtendedMetadata extendedMetadata = generator.generateExtendedMetadata();
    extendedMetadata.setRequireLogoutRequestSigned(false);
    extendedMetadata.setSignMetadata(false);
    extendedMetadata.setAlias(alias);

    MetadataMemoryProvider memoryProvider = new MetadataMemoryProvider(descriptor);
    memoryProvider.initialize();
    MetadataProvider spMetadataProvider = new ExtendedMetadataDelegate(memoryProvider, extendedMetadata);
    
    return spMetadataProvider;
        
	}
}
