package ro.itschool.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class Request {
	private final org.eclipse.jetty.server.Request baseRequest;
	private final String path;

	Request(String path, org.eclipse.jetty.server.Request baseRequest) {
		this.baseRequest = baseRequest;
		this.path = path;
	}

	public InputStream getBody() {
		try {
			return this.baseRequest.getInputStream();
		} catch (IOException e) {
			throw new ServerException("Error in reading input from request.", e);
		}
	}

	public String getMethod() {
		return this.baseRequest.getMethod();
	}

	public Map<String, String> getHeaders() {
		final Map<String, String> result = new HashMap<>();
		Enumeration<String> headers = this.baseRequest.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = headers.nextElement();
			result.put(header, this.baseRequest.getHeader(header));
		}

		return result;
	}

	public Map<String, String> getParams() {
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();

		for (Entry<String, String[]> param : baseRequest.getParameterMap().entrySet()) {
			paramsMap.put(param.getKey(), String.join(",", param.getValue()));
		}

		return paramsMap;

	}

	public String getPath() {
		return path;
	}
}
