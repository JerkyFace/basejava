package model;

import java.util.Map;

public class Contacts {
    private Map<ContactType, String> contacts;

    public Contacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contacts=" + contacts +
                "\n\n";
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }
}
