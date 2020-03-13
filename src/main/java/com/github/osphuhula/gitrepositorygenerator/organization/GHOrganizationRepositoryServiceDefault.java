package com.github.osphuhula.gitrepositorygenerator.organization;

import java.io.File;

import org.kohsuke.github.GHRepository;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;
import com.github.osphuhula.gitrepositorygenerator.beans.Team;
import com.github.osphuhula.gitrepositorygenerator.branch.GHBranchService;

public class GHOrganizationRepositoryServiceDefault
	implements
	GHOrganizationRepositoryService {

	private final GHOrganizationService organizationService;
	private final GHBranchService branchService;

	public GHOrganizationRepositoryServiceDefault(
		final GHOrganizationService organizationService,
		final GHBranchService branchService) {
		super();
		this.organizationService = organizationService;
		this.branchService = branchService;
	}

	@Override
	public void createRepository(
		final String organization,
		final String repository,
		final String description) {
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

	@Override
	public void checkTeam(
		Team team) {
		String organization = team.getOrganization();
		String name = team.getName();
		String description = team.getDescription();
		checkTeam(organization, name, description);
	}


	@Override
	public void checkTeam(
		final String organization,
		final String team,
		final String description) {
		if (!organizationService.hasTeam(organization, team)) {
			organizationService.addTeam(organization, team, description);
		}
	}

	@Override
	public void addTeam(
		final String organization,
		final String repositoryName,
		final String team) {
		if (!organizationService.hasRepository(organization, repositoryName)) {
			GHRepository ghRepository = organizationService.getRepository(organization, repositoryName);
			organizationService.addRepository(organization, ghRepository);
		}
		if (!organizationService.hasTeam(organization, repositoryName, team)) {
			organizationService.addRepositoryTeam(organization, repositoryName, team);
		}
	}

	@Override
	public void protectBranch(
		final String organization,
		final String repository,
		final String user,
		final String team) {
		branchService.protectBranch(organization, repository, user, team);
	}

	@Override
	public void addContent(
		final String organization,
		final String repository,
		final String path,
		final File resource) {
		branchService.addFile(organization, repository, path, resource);
	}

	@Override
	public void addContent(
		final String organization,
		final String repository,
		final String branch,
		final String path,
		final String content) {
		branchService.addFile(organization, repository, branch, path, () -> content);
	}

	public void addContent(
		final String organization,
		final String repository,
		final String branch,
		final String path,
		final File resource) {
		branchService.addFile(organization, repository, branch, path, resource);
	}


}
