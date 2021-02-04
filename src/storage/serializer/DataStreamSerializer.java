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

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            SectionType personal = SectionType.valueOf(dis.readUTF());
            resume.addSection(personal, new TextSection(dis.readUTF()));
            SectionType objective = SectionType.valueOf(dis.readUTF());
            resume.addSection(objective, new TextSection(dis.readUTF()));

            for (int i = 0; i < 4; i++) {
                read(dis, resume);
            }

            return resume;
        }
    }

    private void read(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        int sectionSize = dis.readInt();
        List<String> sectionList = new ArrayList<>();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                for (int i = 0; i < sectionSize; i++) {
                    sectionList.add(dis.readUTF());
                }
                resume.addSection(sectionType, new ListSection(sectionList));
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationList = new ArrayList<>();
                for (int i = 0; i < sectionSize; i++) {
                    String organizationName = dis.readUTF();
                    String homepageUrl = dis.readUTF();
                    int positionsSize = dis.readInt();
                    List<Organization.Position> positions = new ArrayList<>();
                    for (int j = 0; j < positionsSize; j++) {
                        LocalDate startDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                        LocalDate endDate = DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
                        String positionName = dis.readUTF();
                        String positionDescription = dis.readUTF();
                        positions.add(new Organization.Position(startDate, endDate, positionName, positionDescription));
                    }
                    organizationList.add(new Organization(new Link(organizationName, homepageUrl), positions));
                }

                resume.addSection(sectionType, new OrganizationSection(organizationList));
                break;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getHomePageUrl());
    }

    @FunctionalInterface
    interface Writer<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }
}
