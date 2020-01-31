package com.github.osphuhula.gitrepositorygenerator.branch;


public interface GHBranchService {

	void protectBranch(
		String organization,
		String repository,
		String user,
		String team);
}
