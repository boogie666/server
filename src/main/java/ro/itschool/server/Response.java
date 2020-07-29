package ro.itschool.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jetty.util.IO;

public final class Response {
	private final ByteArrayOutputStream out;
	private final Map<String, String> headers;
	private int statusCode = 200;

	public Response() {
		this.out = new ByteArrayOutputStream();
		this.headers = new LinkedHashMap<String, String>();
	}

	public Response sendHeader(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	Map<String, String> getHeaders() {
		return headers;
	}

	public Response sendStatus(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public Response send(File f) {
		try {
			return this.send(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			throw new ServerException("Can't send file " + f, e);
		}
	}

	public Response send(InputStream in) {
		try {
			IO.copy(in, out);
		} catch (IOException e) {
			throw new ServerException("Can't send " + in, e);
		}

		return this;
	}

	public Response send(byte b) {
		this.out.write(b);
		return this;
	}

	public Response send(String content) {
		try {
			this.out.write(content.getBytes());
		} catch (IOException e) {
			throw new ServerException("Can't write string " + content, e);
		}
		return this;
	}

	public Response send(Object content) {
		return this.send(content.toString());
	}

	int getStatusCode() {
		return statusCode;
	}

	void write(OutputStream out) {
		try {
			out.write(this.out.toByteArray());
			this.out.close();
		} catch (IOException e) {
			throw new ServerException("Can't send response", e);
		}
	}
	
	
}
