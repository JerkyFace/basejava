import model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\JerkFace\\Desktop\\baseJavaHW1\\basejava\\.gitignore");
        System.out.println(file.getCanonicalPath());
        Resume resume = (Resume) Files.readAllLines(file.toPath());
        System.out.println(resume);
    }
}
