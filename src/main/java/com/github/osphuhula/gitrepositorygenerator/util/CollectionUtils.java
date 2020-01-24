package com.github.osphuhula.gitrepositorygenerator.util;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionUtils {

    private CollectionUtils() {
        super();
        throw new IllegalStateException(getClass().getSimpleName());
    }

    public static <T> Collection<T> newLinkedList() {
        return new LinkedList<>();
    }
}
