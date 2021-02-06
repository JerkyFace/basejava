package storage.serializer;

import model.*;
import util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void serialize(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(String.valueOf(entry.getKey()));
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, resume.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(String.valueOf(sectionType));
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(dos, ((ListSection) entry.getValue()).getList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(dos, ((OrganizationSection) entry.getValue()).getOrganizations(), organization -> {
                            writeLink(dos, organization.getHomePage());
                            writeWithException(dos, organization.getPositions(), position -> {
                                writeLocalDate(dos, position.getStartDate());
                                writeLocalDate(dos, position.getEndDate());

                                dos.writeUTF(position.getPositionName());
                                dos.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> read(dis, resume));

            return resume;
        }
    }

    private void read(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.addSection(sectionType, new ListSection(readListWithException(dis, dis::readUTF)));
                break;
            case EXPERIENCE:
            case EDUCATION:
                resume.addSection(sectionType, new OrganizationSection(readListWithException(dis, () ->
                        new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                new ArrayList<>(readListWithException(dis, () ->
                                        new Organization.Position(readLocalDate(dis),
                                                readLocalDate(dis),
                                                dis.readUTF(),
                                                dis.readUTF())))))));
                break;
        }
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getHomePageUrl());
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void readWithException(DataInputStream dis, Reader reader) throws IOException {
        int sectionSize = dis.readInt();
        for (int i = 0; i < sectionSize; i++) {
            reader.read();
        }
    }

    private <T> List<T> readListWithException(DataInputStream dis, TypedReader<T> reader) throws IOException {
        int sectionSize = dis.readInt();
        List<T> sectionList = new ArrayList<>();
        for (int i = 0; i < sectionSize; i++) {
            sectionList.add(reader.read());
        }
        return sectionList;
    }

    @FunctionalInterface
    interface Writer<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    interface Reader {
        void read() throws IOException;
    }

    @FunctionalInterface
    interface TypedReader<T> {
        T read() throws IOException;
    }
}
