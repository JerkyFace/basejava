package storage.serializer;

import model.*;

import java.io.*;
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
            dos.writeInt(sections.size());
            sections.forEach((key, value) -> {
                try {
                    dos.writeUTF(key.getTitle());
                    if (value instanceof ListSection) {
                        dos.writeInt(((ListSection) value).getList().size());
                        for (String s : ((ListSection) value).getList()) {
                            dos.writeUTF(s);
                        }
                    } else {
                        dos.writeUTF(value.toString());
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
            int sectionsSize = dis.readInt();
            //todo
            return resume;
        }
    }
}
