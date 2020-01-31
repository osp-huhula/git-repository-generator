package com.github.osphuhula.gitrepositorygenerator.http;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DefaultHttpClient {

	public HttpResponse put(
		String url,
		String body,
		Map<String, String> header) {
		HttpPut post = new HttpPut(url);
		for (Entry<String, String> entry : header.entrySet()) {
			post.addHeader(entry.getKey(), entry.getValue());
		}
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			post.setEntity(new StringEntity(body));
			CloseableHttpResponse response = httpClient.execute(post);
			return response;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
