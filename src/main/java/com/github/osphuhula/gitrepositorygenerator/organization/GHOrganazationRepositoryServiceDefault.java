package com.github.osphuhula.gitrepositorygenerator.organization;

import java.io.IOException;
import java.util.Map.Entry;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTeam;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;

public class GHOrganazationRepositoryServiceDefault {

	private final GHOrganizationService organizationService;

	public GHOrganazationRepositoryServiceDefault(
		GHOrganizationService organizationService) {
		super();
		this.organizationService = organizationService;
	}

	public void createRepository(
		String organization,
		String repository,
		String description) {
		boolean hasOrganization = organizationService.hasOrganization(organization);
		if (!hasOrganization) {
			throw new DefaultRuntimeException("Could not find organization : " + organization);
		}
		boolean hasRepository = organizationService.hasRepository(organization, repository);
		if (hasRepository) {
			GHRepository rep = organizationService.getRepository(organization, repository);
			throw new DefaultRuntimeException(rep.getFullName() + " already exist");
		} else {
			organizationService.createRepository(organization, repository, description);
		}
	}

	public void checkTeam(
		String organization,
		String team,
		String description) {
		if(!organizationService.hasTeam(organization, team)) {
			organizationService.addTeam(organization, team, description);
		}
	}

	public void addTeam(
		String organization,
		String repositoryName,
		String team) {
		if(!organizationService.hasRepository(organization, repositoryName)) {
			GHRepository r = organizationService.getRepository(organization, repositoryName);
			organizationService.addRepository(organization, r);
		}
		if(!organizationService.hasTeam(organization, repositoryName, team)) {
			organizationService.addRepositoryTeam(organization, repositoryName, team);
		}
	}

	public void deleteAll(String organization) {
		deleteAllRepository(organization);
		deleteAllTeam(organization);

	}

	private void deleteAllTeam(
		String organization) {
		GHOrganization ghOrganization = organizationService.getOrganization(organization);
		try {
			for (Entry<String, GHTeam> entry : ghOrganization.getTeams().entrySet()) {
				if(entry.getKey().startsWith("team")) {
					organizationService.deleteTeam(organization, entry.getValue().getName());
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void deleteAllRepository(
		String organization) {
		GHOrganization ghOrganization = organizationService.getOrganization(organization);
		try {
			for (Entry<String, GHRepository> entry : ghOrganization.getRepositories().entrySet()) {
				String key = entry.getKey();
				if(key.startsWith("git-example")) {
					String name = entry.getValue().getName();
					if(organizationService.hasRepository(organization, name)) {
						organizationService.deleteRepository(organization, name);
					}
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
