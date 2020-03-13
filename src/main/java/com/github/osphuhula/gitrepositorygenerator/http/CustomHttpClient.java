package com.github.osphuhula.gitrepositorygenerator.http;

import java.util.Map;

import org.apache.http.HttpResponse;

public interface CustomHttpClient {

	HttpResponse put(
		String url,
		String body,
		Map<String, String> header);

	HttpResponse get(
		String url,
		Map<String, String> header);
}
