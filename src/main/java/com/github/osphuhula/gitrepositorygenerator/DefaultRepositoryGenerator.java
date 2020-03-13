package com.github.osphuhula.gitrepositorygenerator;

import java.io.File;
import java.util.Collection;
import java.util.List;

import com.github.osphuhula.gitrepositorygenerator.beans.Team;
import com.github.osphuhula.gitrepositorygenerator.organization.GHOrganizationRepositoryService;
import com.github.osphuhula.gitrepositorygenerator.repository.GHRepositoryService;
import com.github.osphuhula.gitrepositorygenerator.util.CollectionUtils;

public final class DefaultRepositoryGenerator {

	public interface RepositoryFileContent {

		String getFileName();

		File getContent();
	}

	public interface ParameterRepositoryGenerator {

		String getOrganization();

		String getRepositoryName();

		String getDescription();

		Collection<Team> getTeams();

		Collection<RepositoryFileContent> getContent();
	}

	private final GHOrganizationRepositoryService organizationRepositoryService;
	private final GHRepositoryService repositoryService;

	public DefaultRepositoryGenerator(
		GHOrganizationRepositoryService organizationRepositoryService,
		GHRepositoryService repositoryService) {
		this.organizationRepositoryService = organizationRepositoryService;
		this.repositoryService = repositoryService;
	}

	public void withOrganization(
		ParameterRepositoryGenerator parameter) {
		String organization = parameter.getOrganization();
		String repositoryName = parameter.getRepositoryName();
		String description = parameter.getDescription();
		Collection<Team> teams = parameter.getTeams();
		Collection<RepositoryFileContent> files = parameter.getContent();
		organizationRepositoryService.createRepository(organization, repositoryName, description);
		if (CollectionUtils.notEmpty(teams)) {
			teams.forEach(each -> addTeam(organization, repositoryName, each));
		}
		if (CollectionUtils.notEmpty(files)) {
			files.forEach(each -> {
				String fileName = each.getFileName();
				File content = each.getContent();
				organizationRepositoryService.addContent(organization, repositoryName, fileName, content);
			});
		}
	}

	public void noOrganization(
		String repository,
		String description,
		List<RepositoryFileContent> contents) {
		repositoryService.createRepository(repository, description);
		if (CollectionUtils.notEmpty(contents)) {
			contents.forEach(each -> {
				String fileName = each.getFileName();
				File content = each.getContent();
				repositoryService.addContent(repository, fileName, content);
			});
		}
	}

	private void addTeam(
		String organization,
		String repositoryName,
		Team team) {
		organizationRepositoryService.checkTeam(team);
		organizationRepositoryService.addTeam(organization, repositoryName, team.getName());
	}

}
