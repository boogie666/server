package ro.itschool.server;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.log.Log;

public final class Server {

	private final org.eclipse.jetty.server.Server server;
	static {
		Log.setLog(new Logger());
	}

	public Server(@Name("port") int port, @Name("handler") RequestHandler handler) {
		this.server = new org.eclipse.jetty.server.Server(port);
		this.server.setHandler(new HandlerAdapter(handler));
	}

	public void start() {
		try {
			server.start();
		} catch (Exception e) {
			throw new ServerException("Error occured when starting server.", e);
		}
	}

	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			throw new ServerException("Error occured when starting server.", e);
		}
	}

	private static final class HandlerAdapter extends AbstractHandler {

		private final RequestHandler handler;

		public HandlerAdapter(RequestHandler handler) {
			this.handler = handler;
		}

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

			if (this.handler == null) {
				throw new ServerException("Can't handle request on " + target);
			}

			Response result = this.handler.handle(new ro.itschool.server.Request(target, baseRequest));
			if (result == null) {
				throw new ServerException("Can't handle request on " + target);
			}
			for (Entry<String, String> header : result.getHeaders().entrySet()) {
				response.addHeader(header.getKey(), header.getValue());
			}
			response.setStatus(result.getStatusCode());
			result.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}

	}

}
