package com.github.osphuhula.gitrepositorygenerator.branch;


public interface GHBranchService {

	void protectBranch(
		String organization,
		String repository,
		String user,
		String team);

	void addFile(
		String organization,
		String repository,
		String path,
		String content);

	void addFile(
		String organization,
		String repository,
		String branch,
		String path,
		String content);
}
