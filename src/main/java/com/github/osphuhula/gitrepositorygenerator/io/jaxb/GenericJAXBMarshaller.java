package com.github.osphuhula.gitrepositorygenerator.io.jaxb;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;

import com.github.osphuhula.gitrepositorygenerator.io.GenericFileMarshaller;

public final class GenericJAXBMarshaller
    implements
    GenericFileMarshaller {

    @Override
    public <O> File marshal(
        final Class<O> clazz,
        final O o,
        final File file) {
        try {
            if (!file.exists()) {
                FileUtils.writeStringToFile(file, "");
            }
            ResultMarshaller<File> resultMarshaller = (
                marshaller,
                object) -> {
                marshaller.marshal(object, file);
                return file;
            };
            return marshal(clazz, o, resultMarshaller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <O> String marshal(
        final Class<O> clazz,
        final O o) {
        ResultMarshaller<String> resultMarshaller = (
            marshaller,
            object) -> {
            Writer writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        };
        return marshal(clazz, o, resultMarshaller);
    }

    private <E, R> R marshal(
        final Class<E> clazz,
        final E o,
        final ResultMarshaller<R> resultMarshaller) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return resultMarshaller.getResult(marshaller, o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <O> O unmarshal(
        final Class<O> clazz,
        final File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            O result = (O) jaxbUnmarshaller.unmarshal(file);
            return result;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}

interface ResultMarshaller<O> {

    O getResult(
        Marshaller marshaller,
        Object o)
        throws Exception;
}
