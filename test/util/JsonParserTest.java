package util;

import org.junit.jupiter.api.Test;
import resumeapp.model.AbstractSection;
import resumeapp.model.Resume;
import resumeapp.model.TextSection;
import resumeapp.test.ResumeTestData;
import resumeapp.util.JsonParser;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParserTest {
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = ResumeTestData.initResume(UUID_1, "fullName1");

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection section = new TextSection("TextSection 1");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        assertEquals(section, section2);
    }
}
