package com.github.osphuhula.gitrepositorygenerator;

import java.io.IOException;
import java.util.Properties;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.github.osphuhula.gitrepositorygenerator.beans.GithubConnectionProperties;

public final class GithubConnectorDefault
	implements
	GithubConnector {

	private GithubConnectionProperties connectionProperties;

	public GithubConnectorDefault(
		GithubConnectionProperties connectionProperties) {
		super();
		this.connectionProperties = connectionProperties;
	}

	@Override
	public GitHub connect() {
		Properties properties = connectionProperties.asProperties();
		GitHubBuilder builder = GitHubBuilder
				.fromProperties(properties);
		try {
			return builder.build();
		} catch (IOException e) {
			String message = "Could not connect to github:" + connectionProperties.getEndpoint();
			throw new DefaultRuntimeException(message, e);
		}
	}
}
