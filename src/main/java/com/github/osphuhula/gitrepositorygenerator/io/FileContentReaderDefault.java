package com.github.osphuhula.gitrepositorygenerator.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public final class FileContentReaderDefault implements FileContentReader {

	@Override
    public String readContent(
        final String resource) {
    	try(InputStream stream = getClass().getClassLoader().getResourceAsStream(resource)){
    		return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
