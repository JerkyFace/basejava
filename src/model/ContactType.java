package model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("e-mail"),
    LINKEDIN("linkedIn"),
    GITHUB("github"),
    STACKOVERFLOW("stackoverflow"),
    WEBSITE("website");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
