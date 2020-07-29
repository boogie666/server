package ro.itschool.server;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

class HandlerAdapter extends AbstractHandler {

	private final RequestHandler handler;

	HandlerAdapter(RequestHandler handler) {
		this.handler = handler;
		expectNotNull(handler, "Request handler can't be null");

	}

	private void expectNotNull(Object o, String message) {
		if (o == null) {
			throw new ServerException(message);
		}
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Response result = this.handler.handle(new ro.itschool.server.Request(target, baseRequest));
		expectNotNull(result, "Can't handle request on " + target);
		writeResponse(response, result);
	}

	private void writeResponse(HttpServletResponse response, Response result) throws IOException {
		writeHeadersAndStatus(response, result);
		result.write(response.getOutputStream());
		closeResponse(response);
	}

	private void closeResponse(HttpServletResponse response) throws IOException {
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	private void writeHeadersAndStatus(HttpServletResponse response, Response result) {
		for (Entry<String, String> header : result.getHeaders().entrySet()) {
			response.addHeader(header.getKey(), header.getValue());
		}
		response.setStatus(result.getStatusCode());
	}

}
