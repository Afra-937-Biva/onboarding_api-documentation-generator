package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectScanner {

    public static List<File> scanJavaFiles(String projectPath) {

        List<File> javaFiles = new ArrayList<>();

        scan(new File(projectPath), javaFiles);

        return javaFiles;
    }

    private static void scan(File folder, List<File> javaFiles) {

        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {

            if (file.isDirectory()) {

                scan(file, javaFiles);

            } else if (file.getName().endsWith(".java")) {

                javaFiles.add(file);
            }
        }
    }
}