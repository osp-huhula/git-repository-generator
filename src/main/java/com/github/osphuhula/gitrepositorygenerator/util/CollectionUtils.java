package com.github.osphuhula.gitrepositorygenerator.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class CollectionUtils {

	private CollectionUtils() {
		super();
		throw new IllegalStateException(getClass().getSimpleName());
	}

	public static <T> Collection<T> newLinkedList() {
		return new LinkedList<>();
	}

	public static boolean notEmpty(
		Collection<?> collection) {
		return !isEmpty(collection);
	}

	private static boolean isEmpty(
		Collection<?> collection) {
		return Objects.isNull(collection) || collection.isEmpty();
	}
}
