package com.github.osphuhula.gitrepositorygenerator.repository;

import java.io.IOException;
import java.util.Map;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;
import com.github.osphuhula.gitrepositorygenerator.GithubConnector;


public class GHRepositoryServiceDefault
	implements
	GHRepositoryService {

	private final GithubConnector connector;

	public GHRepositoryServiceDefault(
		GithubConnector connector) {
		super();
		this.connector = connector;
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

}
