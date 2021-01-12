package model;

import java.util.Map;

public class ResumeContent {
    private Map<SectionType, ResumeSection> sections;
    private Map<ContactType, String> contacts;

    public ResumeContent() {
    }

    public ResumeContent(Map<ContactType, String> contacts, Map<SectionType, ResumeSection> sections) {
        this.contacts = contacts;
        this.sections = sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, ResumeSection> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, ResumeSection> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return "ResumeContent{" +
                "contacts=" + contacts +
                ", sections=" + sections +
                "\n\n";
    }
}
