package com.github.osphuhula.gitrepositorygenerator.beans;

import java.util.Properties;

public interface GithubConnectionProperties {

	Properties asProperties();

	String getEndpoint();

	String getAuthorizationU();

	String getAuthorizationP();
}
