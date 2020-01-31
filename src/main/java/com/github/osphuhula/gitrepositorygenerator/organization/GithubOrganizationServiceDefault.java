package com.github.osphuhula.gitrepositorygenerator.organization;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTeam;
import org.kohsuke.github.GitHub;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;
import com.github.osphuhula.gitrepositorygenerator.GithubConnector;

public class GithubOrganizationServiceDefault
	implements
	GHOrganizationService {

	private final GithubConnector connector;

	public GithubOrganizationServiceDefault(
		GithubConnector connector) {
		super();
		this.connector = connector;
	}

	@Override
	public boolean hasOrganization(
		String name) {
		GitHub github = connector.connect();
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
		} catch (Exception e) {
			throw new DefaultRuntimeException("Could not verify organization: " + name, e);
		}
	}

	@Override
	public GHOrganization getOrganization(
		String name) {
		GitHub github = connector.connect();
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
		} catch (Exception e) {
			throw new DefaultRuntimeException("Could not verify organization: " + name, e);
		}
	}

	@Override
	public boolean hasRepository(
		String organization,
		String repository) {
		GHOrganization ghOrganization = getOrganization(organization);
		try {
			Map<String, GHRepository> repositories = ghOrganization.getRepositories();
			return !repositories.isEmpty() && repositories.keySet().contains(repository);
		} catch (Exception e) {
			String message = String.format("Could not verify %s/%s", organization, repository);
			throw new DefaultRuntimeException(message, e);
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
			.description(description)
			.autoInit(true);
		try {
			builder.create();
		} catch (Exception e) {
			String message = String.format("Could not verify repository %s/%s", organization, repository);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public GHRepository getRepository(
		String organization,
		String repository) {
		GHOrganization ghOrganization = getOrganization(organization);
		try {
			Map<String, GHRepository> repositories = ghOrganization.getRepositories();
			for (Entry<String, GHRepository> each : repositories.entrySet()) {
				String key = each.getKey();
				if(key.equals(repository)) {
					return each.getValue();
				}
			}
			String message = String.format("Could not verify repository %s/%s", organization, repository);
			throw new IllegalStateException(message);
		} catch (Exception e) {
			String message = String.format("Could not verify repository %s/%s", organization, repository);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public boolean hasTeam(
		String organization,
		String team) {
		GHOrganization ghOrganization = getOrganization(organization);
		try {
			Map<String, GHTeam> teams = ghOrganization.getTeams();
			return !teams.isEmpty() && teams.keySet().contains(team);
		} catch (Exception e) {
			String message = String.format("Could not verify team %s/%s", organization, team);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public void addTeam(
		String organization,
		String team,
		String description) {
		GHOrganization ghOrganization = getOrganization(organization);
		try {
			ghOrganization.createTeam(team, Collections.emptyList());
			GHTeam ghTeam = getTeam(organization, team);
			ghTeam.setDescription(description);
		} catch (Exception e) {
			String message = String.format("Could not verify team %s/%s", organization, team);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public boolean hasTeam(
		String organizationName,
		String repositoryName,
		String team) {
		GHRepository ghRepository = getRepository(organizationName, repositoryName);
		try {
			Set<GHTeam> ghTeams = ghRepository.getTeams();
			for (GHTeam ghTeam : ghTeams) {
				if(ghTeam.getName().equals(team)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			String message = String.format("Could not verify team %s in %s/%s", team, organizationName, repositoryName);
			throw new DefaultRuntimeException(message, e);
		}
	}

	private GHTeam getTeam(
		String organizationName,
		String team) {
		GHOrganization ghOrganization = getOrganization(organizationName);
		try {
			Map<String, GHTeam> ghTeams = ghOrganization.getTeams();
			for (Entry<String, GHTeam> each : ghTeams.entrySet()) {
				if(each.getKey().equals(team)) {
					return each.getValue();
				}
			}
			String message = String.format("Could not verify team[%s] in organization %s", team, organizationName);
			throw new IllegalStateException(message);
		} catch (Exception e) {
			String message = String.format("Could not verify team[%s] in organization %s", team, organizationName);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public Boolean addRepositoryTeam(
		String organizationName,
		String repositoryName,
		String team) {
		GHTeam ghTeam = getTeam(organizationName, team);
		GHRepository ghRepository = getRepository(organizationName, repositoryName);
		try {
			ghTeam.add(ghRepository, GHOrganization.Permission.PUSH);
			ghTeam.refresh();
			return true;
		} catch (Exception e) {
			String message = String.format("Could not verify team %s in %s/%s", team, organizationName, repositoryName);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public void addRepository(
		String organizationName,
		GHRepository repositoryName) {
		GHOrganization ghOrganization = getOrganization(organizationName);
		ghOrganization.createRepository(repositoryName.getName());
	}

	@Override
	public void deleteRepository(
		String organization,
		String repository) {
		try {
			GHRepository ghRepository = getRepository(organization, repository);
			ghRepository.delete();
		} catch (IOException e) {
			String message = String.format("Could not delete %s/%s", organization, repository);
			throw new DefaultRuntimeException(message, e);
		}
	}

	@Override
	public void deleteTeam(
		String organization,
		String team) {
		GHTeam ghTeam = getTeam(organization, team);
		try {
			ghTeam.delete();
		} catch (IOException e) {
			String message = String.format("Could not delete team %s/%s", organization, team);
			throw new DefaultRuntimeException(message, e);
		}
	}

}
