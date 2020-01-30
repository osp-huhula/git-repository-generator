package com.github.osphuhula.gitrepositorygenerator.repository;

import org.kohsuke.github.GHRepository;

public interface GHRepositoryService {

	boolean hasRepository(
		String name);

	void createRepository(
		String name,
		String description);

	GHRepository getRepository(
		String name);
}
