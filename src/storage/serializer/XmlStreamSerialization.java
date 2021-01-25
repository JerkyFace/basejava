package storage.serializer;

import model.*;
import util.XmlParser;

import java.io.*;

public class XmlStreamSerialization implements Serialization {
    private final XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(Resume.class, Link.class,
                Organization.class, Organization.Position.class, PlainTextSection.class, ListSection.class);
    }

    @Override
    public boolean serialize(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os)) {
            xmlParser.marshall(resume, writer);
            return true;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return xmlParser.unmarshal(reader);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
