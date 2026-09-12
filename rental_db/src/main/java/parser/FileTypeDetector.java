package parser;

import java.io.File;

public class FileTypeDetector {

    public static String detect(File file) {

        String name = file.getName();

        // Get the parent folder name
        File parentFolder = file.getParentFile();

        String folderName = "";

        if (parentFolder != null) {
            folderName = parentFolder.getName().toLowerCase();
        }

        /*
         * Detect based on folder/package structure.
         * This makes the generator reusable for different
         * Spring Boot projects.
         */

        // Controller
        if (folderName.equals("controller")
                || folderName.equals("controllers")) {
            return "Controller";
        }

        // Service
        if (folderName.equals("service")
                || folderName.equals("services")) {
            return "Service";
        }

        // Repository
        if (folderName.equals("repository")
                || folderName.equals("repositories")
                || folderName.equals("repo")) {
            return "Repository";
        }

        // DTO
        if (folderName.equals("dto")
                || folderName.equals("dtos")) {
            return "DTO";
        }

        // Entity / Model
        if (folderName.equals("entity")
                || folderName.equals("entities")
                || folderName.equals("model")
                || folderName.equals("models")) {
            return "Entity";
        }

        // Specification
        if (folderName.equals("specification")
                || folderName.equals("specifications")
                || folderName.equals("spec")) {
            return "Specification";
        }

        // Parser / Generator files
        if (folderName.equals("parser")
                || folderName.equals("parsers")) {
            return "Parser";
        }

        // Application class
        if (name.endsWith("Application.java")) {
            return "Application";
        }

        // Fallback based on class name
        if (name.endsWith("Controller.java")) {
            return "Controller";
        }

        if (name.endsWith("Service.java")) {
            return "Service";
        }

        if (name.endsWith("Repository.java")) {
            return "Repository";
        }

        if (name.endsWith("DTO.java")) {
            return "DTO";
        }

        if (name.endsWith("Specification.java")) {
            return "Specification";
        }

        // Unknown Java file
        return "Other";
    }
}