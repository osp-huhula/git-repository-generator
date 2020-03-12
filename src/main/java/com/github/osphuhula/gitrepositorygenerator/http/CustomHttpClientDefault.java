package com.github.osphuhula.gitrepositorygenerator.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class CustomHttpClientDefault implements CustomHttpClient {

	@Override
	public HttpResponse put(
		String url,
		String body,
		Map<String, String> header) {
		HttpPut post = new HttpPut(url);
		for (Entry<String, String> entry : header.entrySet()) {
			post.addHeader(entry.getKey(), entry.getValue());
		}
		try (CloseableHttpClient httpClient = newHttpClient();) {
			post.setEntity(new StringEntity(body));
			return httpClient.execute(post);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private CloseableHttpClient newHttpClient() {
		boolean b = true;
		if(b) {
			try {
				SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
				return HttpClients
						.custom()
						.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
						.setSSLSocketFactory(new SSLConnectionSocketFactory(context))
						.build();
			} catch (KeyManagementException
				| NoSuchAlgorithmException
				| KeyStoreException e) {
				throw new IllegalStateException(e);
			}
		} else {
			return HttpClients.createDefault();
		}
	}
}
