package com.github.osphuhula.gitrepositorygenerator;

import org.kohsuke.github.GHRepository;

public class GithubRepositoryService {

	private GithubConnector connector;

	public GithubRepositoryService(
		GithubConnector connector) {
		super();
		this.connector = connector;
	}

	public void createRepository(
		String repositoryName,
		String description) {
		GithubOperation github = connector.connect();
		boolean hasRepository = github.hasRepository(repositoryName);
		if (hasRepository) {
			GHRepository rep = github.getRepository(repositoryName);
			throw new DefaultRuntimeException(rep.getFullName() + " already exist");
		} else {
			github.createRepository(repositoryName, description);
		}
	}

	public void createRepository(
		String organization,
		String repository,
		String description) {
		GithubOperation github = connector.connect();
		boolean hasOrganization = github.hasOrganization(organization);
		if (!hasOrganization) {
			throw new DefaultRuntimeException("Could not find organization : " + organization);
		}
		boolean hasRepository = github.hasRepository(organization, repository);
		if (hasRepository) {
			GHRepository rep = github.getRepository(repository);
			throw new DefaultRuntimeException(rep.getFullName() + " already exist");
		} else {
			github.createRepository(organization, repository, description);
		}
	}
}
