package com.github.osphuhula.gitrepositorygenerator;

import java.io.IOException;
import java.util.Map;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public final class GithubOperationDefault
	implements
	GithubOperation {

	private GitHub github;

	public GithubOperationDefault(
		GitHub github) {
		this.github = github;
	}

	@Override
	public boolean hasOrganization(
		String name) {
		try {
			GHPersonSet<GHOrganization> organizations = github.getMyself().getOrganizations();
			if(organizations.isEmpty()) {
				return false;
			}
			for (GHOrganization organization : organizations) {
				if (organization.getLogin().equals(name)) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could not verify organization: " + name, e);
		}
	}

	@Override
	public GHOrganization getOrganization(
		String name) {
		boolean hasOrganization = hasOrganization(name);
		if (!hasOrganization) {
			throw new DefaultRuntimeException("Could not find organization : " + name);
		}
		try {
			GHPersonSet<GHOrganization> organizations = github.getMyself().getOrganizations();
			for (GHOrganization organization : organizations) {
				if (organization.getLogin().equals(name)) {
					return organization;
				}
			}
			throw new DefaultRuntimeException("Could not find organization : " + name);
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could not verify organization: " + name, e);
		}
	}

	@Override
	public boolean hasRepository(
		String name) {
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
	public void createRepository(
		String organization,
		String repository,
		String description) {
		GHOrganization ghOrganization = getOrganization(organization);
		GHCreateRepositoryBuilder builder = ghOrganization
			.createRepository(repository)
			.description(description);
		try {
			builder.create();
		} catch (IOException e) {
			throw new DefaultRuntimeException("Could not create repository: " + repository, e);
		}
	}

	@Override
	public GHRepository getRepository(
		String repository) {
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
	public boolean hasRepository(
		String organization,
		String repository) {
		GHOrganization ghOrganization = getOrganization(organization);
		try {
			Map<String, GHRepository> repositories = ghOrganization.getRepositories();
			return repositories.isEmpty() && repositories.keySet().contains(repository);
		} catch (IOException e) {
			String message = String.format("Could not verify %s/%s", organization, repository);
			throw new DefaultRuntimeException(message, e);
		}
	}

}
