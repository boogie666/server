package ro.itschool.server;

import org.eclipse.jetty.util.log.Log;

public final class Server {

	private final org.eclipse.jetty.server.Server server;
	static {
		Log.setLog(new Logger());
	}

	public Server(int port, RequestHandler handler) {
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

}
