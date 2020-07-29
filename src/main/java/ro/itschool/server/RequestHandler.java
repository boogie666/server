package ro.itschool.server;

import org.eclipse.jetty.util.annotation.Name;

@FunctionalInterface
public interface RequestHandler {
	Response handle(@Name("req") Request req);
}
