import exception.StorageException;
import test.ResumeTestData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class MainFile {
    // print all files recursively task
    public static void printCatalogContent(File catalog) {
        String dash = "--";
        String spaces = "  ";
        String dirs = "";
        String filez = "";
        boolean flag = false;

        File[] files = catalog.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                flag = true;
                System.out.println(file);
                printCatalogContent(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("./", "test.txt");
        File directory = new File("./");

        printCatalogContent(directory);

        System.out.println(file.getCanonicalPath());
        if (file.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(ResumeTestData
                    .initResume("uuid1", "Vasya Pupkin")
                    .toString()
                    .getBytes());
        }


        try {
            System.out.println(Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            throw new StorageException("Could not read the file", null, e);
        }
    }
}
