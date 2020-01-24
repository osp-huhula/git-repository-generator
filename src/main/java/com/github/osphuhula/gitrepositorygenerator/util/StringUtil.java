package com.github.osphuhula.gitrepositorygenerator.util;

public final class StringUtil {

    private StringUtil() {
        super();
        throw new IllegalArgumentException(getClass().toString());
    }

    public static String normalize(
        final String value) {
        return value.replaceAll("\r|\n|\t", "");
    }

    public static String normalizeXML(
        final String value) {
        return value.replaceAll(">[\n\t\r ]+<", "><").replaceAll("[\n\r]+", "");
    }
}
