package com.github.osphuhula.gitrepositorygenerator.beans;

import java.io.IOException;
import java.util.Properties;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;

public final class GithubConnectionPropertiesDefault
	implements
	GithubConnectionProperties {

	private static final String APPLICATION_PROPERTIES = "/application.properties";
	private final Properties properties;

	public GithubConnectionPropertiesDefault() {
		super();
		this.properties = new Properties();
		try {
			this.properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES));
		} catch (IOException e) {
			throw new DefaultRuntimeException(
				"Could load properties from file: " + APPLICATION_PROPERTIES,
				e);
		}
	}

	@Override
	public Properties asProperties() {
		return properties;
	}

	@Override
	public String getEndpoint() {
		return properties.getProperty("endpoint");
	}
}
