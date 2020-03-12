package com.github.osphuhula.gitrepositorygenerator.branch;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import com.github.osphuhula.gitrepositorygenerator.beans.GithubConnectionProperties;
import com.github.osphuhula.gitrepositorygenerator.http.CustomHttpClient;

public class GHBranchServiceDefault
	implements
	GHBranchService {

	private final CustomHttpClient httpClient;
	private final GithubConnectionProperties connectionProperties;

	public GHBranchServiceDefault(
		CustomHttpClient httpClient,
		GithubConnectionProperties connectionProperties) {
		super();
		this.httpClient = httpClient;
		this.connectionProperties = connectionProperties;
	}

	@Override
	public void addFile(
		String organization,
		String repository,
		String branch,
		String path,
		String content) {
		String encodedContent = encode(content);
		String body = "{ \"message\": \"automatic commit\", \"content\": \"" + encodedContent + "\", \"branch\": \"" + branch + "\" }";
		_addFile(organization, repository, path, body);
	}

	@Override
	public void addFile(
		String organization,
		String repository,
		String path,
		String content
		) {
		String encodedContent = encode(content);
		String body = "{ \"message\": \"automatic commit\", \"content\": \"" + encodedContent + "\" }";

		_addFile(organization, repository, path, body);
	}

	private void _addFile(
		String organization,
		String repository,
		String path,
		String body) {
		String host = connectionProperties.getEndpoint();
		String pattternURL = "%s/repos/%s/%s/contents/%s";
		String url = String.format(pattternURL, host, organization, repository, path);
		HttpResponse response = httpClient.put(url, body, header());
		StatusLine statusLine = response.getStatusLine();
		if (HttpStatus.SC_CREATED != statusLine.getStatusCode()) {
			try {
				throw new IllegalArgumentException(response.toString());
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	@Override
	public void protectBranch(
		String organization,
		String repository,
		String user,
		String team) {

		String host = connectionProperties.getEndpoint();
		String branch = "master";
		String pattternURL = "%s/repos/%s/%s/branches/%s/protection";
		String url = String.format(pattternURL, host, organization, repository, branch);

		Map<String, String> header = header();
		String body = body(user, team);
		HttpResponse response = httpClient.put(url, body, header);

		StatusLine statusLine = response.getStatusLine();
		if (HttpStatus.SC_OK != statusLine.getStatusCode()) {
			HttpEntity responseEntity = response.getEntity();
			try {
				throw new IllegalArgumentException(EntityUtils.toString(responseEntity));
			} catch (ParseException
				| IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private Map<String, String> header() {
		Map<String, String> header = new HashMap<>();
		header.put("charset", "utf-8");
		header.put(HttpHeaders.CONTENT_TYPE, "application/json");
		header.put(HttpHeaders.ACCEPT, "application/vnd.github.luke-cage-preview+json");
		String u = connectionProperties.getAuthorizationU();
		String p = connectionProperties.getgetAuthorizationP();
		String authorization = String.format("%s:%s", u, p);
		String encoding = encode(authorization);
		header.put(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
		return header;
	}

	private String body(
		String user,
		String team) {
		StringBuilder builder = new StringBuilder();
		builder.append("{ \"required_status_checks\": { \"strict\": true, \"contexts\": [] }, \"enforce_admins\": true, \"required_pull_request_reviews\": { \"dismissal_restrictions\": { \"users\": [ \"");
		builder.append(user);
		builder.append("\" ], \"teams\": [ \"");
		builder.append(team);
		builder.append("\" ] }, \"dismiss_stale_reviews\": false, \"require_code_owner_reviews\": false, \"required_approving_review_count\": 2 }, \"restrictions\": { \"users\": [ \"");
		builder.append(user);
		builder.append("\" ], \"teams\": [ \"");
		builder.append(team);
		builder.append("\" ] } }");
		return builder.toString();
	}

	private String encode(
		String authorization) {
		return Base64.getEncoder().encodeToString(authorization.getBytes());
	}

}
