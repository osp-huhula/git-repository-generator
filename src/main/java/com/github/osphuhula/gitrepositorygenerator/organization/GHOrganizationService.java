package com.github.osphuhula.gitrepositorygenerator.organization;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;

public interface GHOrganizationService {

	boolean hasOrganization(
		String name);

	GHOrganization getOrganization(
		String name);

	boolean hasRepository(
		String organization,
		String repository);

	GHRepository getRepository(
		String organization,
		String repository);

	void createRepository(
		String organization,
		String repository,
		String description);

	boolean hasTeam(
		String organization,
		String team);
	void addTeam(
		String organization,
		String team,
		String description);

	boolean hasTeam(
		String organization,
		String repositoryName,
		String team);

	Boolean addRepositoryTeam(
		String organization,
		String repositoryName,
		String team);

	void addRepository(
		String organization,
		GHRepository repositoryName);

	void deleteRepository(
		String organization,
		String repository);

	void deleteTeam(
		String organization,
		String team);


}
