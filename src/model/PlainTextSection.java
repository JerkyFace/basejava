package model;

public class PlainTextSection extends ResumeSection {
    private String content;

    public PlainTextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PlainTextSection{" +
                "content='" + content + '\'' +
                "}\n\n";
    }
}
