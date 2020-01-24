package com.github.osphuhula.gitrepositorygenerator;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;

public interface GithubOperation {

	void createRepository(
		String name,
		String description);

	void createRepository(
		String organization,
		String repository,
		String description);

	boolean hasRepository(
		String name);

	GHRepository getRepository(
		String name);

	boolean hasRepository(
		String organization,
		String repository);

	boolean hasOrganization(
		String name);

	GHOrganization getOrganization(
		String name);
}
