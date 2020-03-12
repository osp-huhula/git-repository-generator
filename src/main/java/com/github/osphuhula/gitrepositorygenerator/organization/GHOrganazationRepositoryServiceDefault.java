package com.github.osphuhula.gitrepositorygenerator.organization;

import java.io.File;

import org.kohsuke.github.GHRepository;

import com.github.osphuhula.gitrepositorygenerator.DefaultRuntimeException;
import com.github.osphuhula.gitrepositorygenerator.branch.GHBranchService;
import com.github.osphuhula.gitrepositorygenerator.io.FileContentReaderDefault;

public class GHOrganazationRepositoryServiceDefault {

	private final FileContentReaderDefault reader = new FileContentReaderDefault();
	private final GHOrganizationService organizationService;
	private final GHBranchService branchService;

	public GHOrganazationRepositoryServiceDefault(
		GHOrganizationService organizationService,
		GHBranchService branchService) {
		super();
		this.organizationService = organizationService;
		this.branchService = branchService;
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
		if (!organizationService.hasTeam(organization, team)) {
			organizationService.addTeam(organization, team, description);
		}
	}

	public void addTeam(
		String organization,
		String repositoryName,
		String team) {
		if (!organizationService.hasRepository(organization, repositoryName)) {
			GHRepository ghRepository = organizationService.getRepository(organization, repositoryName);
			organizationService.addRepository(organization, ghRepository);
		}
		if (!organizationService.hasTeam(organization, repositoryName, team)) {
			organizationService.addRepositoryTeam(organization, repositoryName, team);
		}
	}

	public void protectBranch(
		String organization,
		String repository,
		String user,
		String team) {
		branchService.protectBranch(organization, repository, user, team);
	}

	public void addContent(
		String organization,
		String repository,
		String path,
		File resource) {
		String content = reader.readContent(resource.getPath());
		branchService.addFile(organization, repository, path, content);
	}

	public void addContent(
		String organization,
		String repository,
		String branch,
		String path,
		String content) {
		branchService.addFile(organization, repository, branch, path, content);
	}

	public void addContent(
		String organization,
		String repository,
		String branch,
		String path,
		File resource) {
		String content = reader.readContent(resource.getPath());
		branchService.addFile(organization, repository, branch, path, content);
	}

}
