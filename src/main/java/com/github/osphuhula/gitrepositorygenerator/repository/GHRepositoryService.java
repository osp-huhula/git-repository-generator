package com.github.osphuhula.gitrepositorygenerator.repository;

import java.io.File;
import java.util.Map;

import org.kohsuke.github.GHRepository;

public interface GHRepositoryService {

	boolean hasRepository(
		String name);

	void createRepository(
		String name,
		String description);

	GHRepository getRepository(
		String name);

	Map<String, GHRepository> getRepositories();

	void addContent(
		String repository,
		String fileName,
		File content);
}
