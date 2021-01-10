package model;

import java.util.Map;

public class ResumeContent {
    private Contacts contacts;
    private Map<SectionType, ResumeSection> sections;

    public ResumeContent() {
    }

    public ResumeContent(Contacts contacts, Map<SectionType, ResumeSection> sections) {
        this.contacts = contacts;
        this.sections = sections;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
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
                '}';
    }
}
