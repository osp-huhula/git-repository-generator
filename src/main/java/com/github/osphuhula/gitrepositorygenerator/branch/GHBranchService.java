package com.github.osphuhula.gitrepositorygenerator.branch;

import java.io.File;

import com.github.osphuhula.gitrepositorygenerator.beans.BranchFileContent;

public interface GHBranchService {

	void protectBranch(
		String organization,
		String repository,
		String user,
		String team);

	void addFile(
		String organization,
		String repository,
		String fileName,
		File resource);

	void addFile(
		String organization,
		String repository,
		String branch,
		String fileName,
		File resource);

	void addFile(
		String organization,
		String repository,
		String branch,
		String fileName,
		BranchFileContent content);

	void addFile(
		String organization,
		String repository,
		String fileName,
		BranchFileContent content);
}
