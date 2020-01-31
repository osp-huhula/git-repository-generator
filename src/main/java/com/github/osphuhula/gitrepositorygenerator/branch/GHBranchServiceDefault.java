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
import com.github.osphuhula.gitrepositorygenerator.http.DefaultHttpClient;

public class GHBranchServiceDefault
	implements
	GHBranchService {

	private GithubConnectionProperties connectionProperties;

	public GHBranchServiceDefault(
		GithubConnectionProperties connectionProperties) {
		super();
		this.connectionProperties = connectionProperties;
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
		HttpResponse response = new DefaultHttpClient().put(url, body, header);

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
		String authorization = Base64.getEncoder().encodeToString(String.format("%s:%s", u, p).getBytes());
		header.put(HttpHeaders.AUTHORIZATION, "Basic " + authorization);
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
}
