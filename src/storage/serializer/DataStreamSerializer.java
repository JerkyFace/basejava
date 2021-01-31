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
                            System.out.println(organizationListSize);
                            dos.writeUTF(String.valueOf(key));
                            System.out.println(key);
                            for (int i = 0; i < organizationListSize; i++) {
                                for (Organization organization : ((OrganizationSection) value).getOrganizations()) {
                                    dos.writeUTF(organization.getHomePage().getName());
                                    dos.writeUTF(organization.getHomePage().getHomePageUrl());
                                    dos.writeInt(organization.getPositions().size());
                                    for (Organization.Position position : organization.getPositions()) {
                                        dos.writeInt(position.getStartDate().getYear());
                                        dos.writeInt(position.getStartDate().getMonth().getValue());

                                        dos.writeInt(position.getEndDate().getYear());
                                        dos.writeInt(position.getEndDate().getMonth().getValue());

                                        dos.writeUTF(position.getPositionName());
                                        dos.writeUTF(position.getDescription());
                                    }
                                }
                            }
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


//            dos.writeInt(sections.size());
//            sections.forEach((key, value) -> {
//                try {
//                    dos.writeUTF(key.getTitle());
//                    if (value instanceof ListSection) {
//                        dos.writeInt(((ListSection) value).getList().size());
//                        for (String s : ((ListSection) value).getList()) {
//                            dos.writeUTF(s);
//                        }
//                    } else {
//                        dos.writeUTF(String.valueOf(value));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
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

            int achievementSize = dis.readInt();
            SectionType achievement = SectionType.valueOf(dis.readUTF());
            List<String> achievements = new ArrayList<>();
            for (int i = 0; i < achievementSize; i++) {
                achievements.add(dis.readUTF());
            }
            resume.addSection(achievement, new ListSection(achievements));

            int qualificationSize = dis.readInt();
            SectionType qualification = SectionType.valueOf(dis.readUTF());
            List<String> qualifications = new ArrayList<>();
            for (int i = 0; i < qualificationSize; i++) {
                qualifications.add(dis.readUTF());
            }
            resume.addSection(qualification, new ListSection(qualifications));

            int experienceSize = dis.readInt();
            System.out.println(experienceSize);
            SectionType experience = SectionType.valueOf(dis.readUTF());
            System.out.println(experience);
            List<Organization> workOrganizations = new ArrayList<>();
            for (int i = 0; i < experienceSize; i++) {
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
                workOrganizations.add(new Organization(new Link(organizationName, homepageUrl), positions));
            }
            resume.addSection(experience, new OrganizationSection(workOrganizations));

            int educationSize = dis.readInt();
            System.out.println(educationSize);
            SectionType education = SectionType.valueOf(dis.readUTF());
            System.out.println(education);
            List<Organization> educationOrganizations = new ArrayList<>();
            for (int i = 0; i < educationSize; i++) {
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
                educationOrganizations.add(new Organization(new Link(organizationName, homepageUrl), positions));
            }
            resume.addSection(education, new OrganizationSection(educationOrganizations));

            return resume;
        }
    }
}
