package resumeapp;

import resumeapp.exception.StorageException;
import resumeapp.test.ResumeTestData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class MainFile {
    // print all files recursively task
    public static void printCatalogContent(File catalog, int level) {
        File[] files = catalog.listFiles();
        if (files != null) {
            for (File file : files) {
                for (int i = 0; i < level; i++) {
                    System.out.print("__");
                }
                if (file.isDirectory()) {
                    System.out.println(file.getName() + "▼");
                    printCatalogContent(file, level + 1);
                } else {
                    System.out.println(file.getName());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("./", "resumeapp.test.txt");
        File directory = new File("./");

        printCatalogContent(directory, 0);

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
