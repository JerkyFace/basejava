package model;

import java.util.Objects;

public class PlainTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String content;

    public PlainTextSection() {
    }

    public PlainTextSection(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainTextSection that = (PlainTextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
