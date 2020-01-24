package com.github.osphuhula.gitrepositorygenerator;

public class DefaultException
	extends
	Exception {

	public DefaultException(
		String message,
		Throwable cause) {
		super(message, cause);
	}

	public DefaultException(
		String message) {
		super(message);
	}

	public DefaultException(
		Throwable cause) {
		super(cause);
	}
}
