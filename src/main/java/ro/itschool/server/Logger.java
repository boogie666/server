package ro.itschool.server;

import java.util.Objects;


final class Logger implements org.eclipse.jetty.util.log.Logger {

	private String[] asStrings(Object[] objs) {
		String[] strings = new String[objs.length];
		for(int i = 0; i < objs.length; i++) {
			strings[i] = Objects.toString(objs[i]);
		}
		return strings;
	}
	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void warn(String msg, Object... args) {
		System.out.println("WARNING: " +  msg + " " + String.join(" ", asStrings(args)));
	}

	@Override
	public void warn(Throwable thrown) {
		System.err.println("ERROR: An error was thrown");
		thrown.printStackTrace(System.err);
	}

	@Override
	public void warn(String msg, Throwable thrown) {
		System.err.println("ERROR: " + msg);
		thrown.printStackTrace(System.err);
	}

	@Override
	public void info(String msg, Object... args) {
		System.out.println(msg + " " + String.join(" ", asStrings(args)));
	}

	@Override
	public void info(Throwable thrown) {
		System.out.println("An error was thrown");
		thrown.printStackTrace(System.out);
	}

	@Override
	public void info(String msg, Throwable thrown) {
		System.out.println(msg);
		thrown.printStackTrace(System.out);
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void setDebugEnabled(boolean enabled) {
	}

	@Override
	public void debug(String msg, Object... args) {
		
	}

	@Override
	public void debug(String msg, long value) {
	}

	@Override
	public void debug(Throwable thrown) {
	}

	@Override
	public void debug(String msg, Throwable thrown) {
	}

	@Override
	public Logger getLogger(String name) {
		return this;
	}

	@Override
	public void ignore(Throwable ignored) {
	}

}
