package com.github.osphuhula.gitrepositorygenerator;

public class DefaultRuntimeException
	extends
	RuntimeException {

	public DefaultRuntimeException(
		String message,
		Throwable cause) {
		super(message, cause);
	}

	public DefaultRuntimeException(
		String message) {
		super(message);
	}

	public DefaultRuntimeException(
		Throwable cause) {
		super(cause);
	}
}
