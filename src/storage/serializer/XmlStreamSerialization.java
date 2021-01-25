package storage.serializer;

import model.*;
import util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerialization implements Serialization {
    private final XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(Resume.class,
                Link.class,
                Organization.class,
                Organization.Position.class,
                PlainTextSection.class,
                ListSection.class,
                OrganizationSection.class);
    }

    @Override
    public boolean serialize(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
            return true;
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshal(reader);
        }
    }
}
