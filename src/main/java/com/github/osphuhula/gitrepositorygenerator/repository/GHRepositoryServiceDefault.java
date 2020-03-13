package com.github.osphuhula.gitrepositorygenerator.repository;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;
import com.github.osphuhula.gitrepositorygenerator.GithubConnector;
import com.github.osphuhula.gitrepositorygenerator.beans.GithubConnectionProperties;
import com.github.osphuhula.gitrepositorygenerator.branch.GHBranchService;


public class GHRepositoryServiceDefault
	implements
	GHRepositoryService {

	private final GithubConnectionProperties connectionProperties;
	private final GithubConnector connector;
	private final GHBranchService branchService;

	public GHRepositoryServiceDefault(
		GithubConnectionProperties connectionProperties,
		GithubConnector connector,
		GHBranchService branchService) {
		super();
		this.connectionProperties = connectionProperties;
		this.connector = connector;
		this.branchService = branchService;
	}

	@Override
	public boolean hasRepository(
		String name) {
		GitHub github = connector.connect();
		try {
			Map<String, GHRepository> repositories = github.getMyself().getRepositories();
			return !repositories.isEmpty() && repositories.keySet().contains(name);
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could not verify repository: " + name, e);
		}
	}

	@Override
	public void createRepository(
		String repository,
		String description) {
		GitHub github = connector.connect();
		boolean hasRepository = hasRepository(repository);
		if(hasRepository) {
			throw new DefaultRuntimeException("Repositoy already exist: " + repository);
		}
		GHCreateRepositoryBuilder githubRepository = github
				.createRepository(repository)
				.description(description);
		try {
			githubRepository.create();
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could not create repository: " + repository, e);
		}
	}

	@Override
	public Map<String, GHRepository> getRepositories() {
		GitHub github = connector.connect();
		try {
			return github.getMyself().getRepositories();
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could get no repository", e);
		}
	}

	@Override
	public GHRepository getRepository(
		String repository) {
		GitHub github = connector.connect();
		boolean hasRepository = hasRepository(repository);
		if(!hasRepository) {
			throw new DefaultRuntimeException("No repositoy found: " + repository);
		}
		try {
			return github.getMyself().getRepositories().get(repository);
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could get repository: " + repository, e);
		}
	}

	@Override
	public void addContent(
		String repository,
		String fileName,
		File resource) {
		String login = connectionProperties.getAuthorizationU();
		branchService.addFile(login, repository, fileName, resource);
	}

}
