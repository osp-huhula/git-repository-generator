package com.github.osphuhula.gitrepositorygenerator.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public final class FileReader {

    public String getContent(
        final File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
