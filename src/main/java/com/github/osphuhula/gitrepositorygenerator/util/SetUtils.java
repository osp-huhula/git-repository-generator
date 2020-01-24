package com.github.osphuhula.gitrepositorygenerator.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class SetUtils {

    private SetUtils() {
        super();
        throw new IllegalStateException(getClass().getSimpleName());
    }

    public static <T> Set<T> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    public static <T> SortedSet<T> newTreeSet() {
        return new TreeSet<>();
    }
}
