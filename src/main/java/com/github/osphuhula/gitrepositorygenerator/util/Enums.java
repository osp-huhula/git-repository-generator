package com.github.osphuhula.gitrepositorygenerator.util;

public final class Enums {

    private Enums() {
        super();
        throw new IllegalArgumentException();
    }

    public static boolean has(
        final Enum<?>[] enumms,
        final String packing) {
        for (Enum<?> each : enumms) {
            if (each.name().equals(packing)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<E>> Enum<E> type(
        final Enum<E>[] enumms,
        final String name) {
        for (Enum<E> each : enumms) {
            if (each.name().equals(name)) {
                return each;
            }
        }
        throw new IllegalStateException(name);
    }
}
