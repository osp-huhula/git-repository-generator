package com.github.osphuhula.gitrepositorygenerator.organization;

import java.io.File;

import com.github.osphuhula.gitrepositorygenerator.beans.Team;

public interface GHOrganizationRepositoryService {

	void createRepository(
		String organization,
		String repositoryName,
		String description);

	void checkTeam(
		Team team);

	void checkTeam(
		String organization,
		String team,
		String description);

	void addTeam(
		String organization,
		String repositoryName,
		String team);

	void addContent(
		String organization,
		String repositoryName,
		String fileName,
		File file);

	void addContent(
		final String organization,
		final String repository,
		final String branch,
		final String path,
		final String content);

	void protectBranch(
		String organization,
		String repository,
		String user,
		String team);
}
