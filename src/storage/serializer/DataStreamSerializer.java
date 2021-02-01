package storage.serializer;

import model.*;
import util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void serialize(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            contacts.forEach((key, value) -> {
                try {
                    dos.writeUTF(String.valueOf(key));
                    dos.writeUTF(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            sections.forEach((key, value) -> {
                try {
                    switch (key) {
                        case PERSONAL:
                        case OBJECTIVE:
                            dos.writeUTF(String.valueOf(key));
                            dos.writeUTF(((TextSection) value).getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            int achievementListSize = ((ListSection) value).getList().size();
                            dos.writeInt(achievementListSize);
                            dos.writeUTF(String.valueOf(key));
                            for (String s : ((ListSection) value).getList()) {
                                dos.writeUTF(s);
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            int organizationListSize = ((OrganizationSection) value).getOrganizations().size();
                            dos.writeInt(organizationListSize);
                            dos.writeUTF(String.valueOf(key));
                            for (Organization organization : ((OrganizationSection) value).getOrganizations()) {
                                dos.writeUTF(organization.getHomePage().getName());
                                dos.writeUTF(organization.getHomePage().getHomePageUrl());
                                dos.writeInt(organization.getPositions().size());
                                for (Organization.Position position : organization.getPositions()) {
                                    writeLocalDate(dos, position.getStartDate());
                                    writeLocalDate(dos, position.getEndDate());

                                    dos.writeUTF(position.getPositionName());
                                    dos.writeUTF(position.getDescription());
                                }
                            }
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
        int sectionSize = dis.readInt();
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
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
}
