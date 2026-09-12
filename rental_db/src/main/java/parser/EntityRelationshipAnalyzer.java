package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EntityRelationshipAnalyzer {

    public static List<EntityRelationship> analyze(File file) {

        List<EntityRelationship> relationships = new ArrayList<>();

        try {

            CompilationUnit cu =
                    StaticJavaParser.parse(file);

            String fromEntity =
                    file.getName().replace(".java", "");

            for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {

                String relationshipType = null;

                if (field.isAnnotationPresent(
                        "ManyToOne")) {

                    relationshipType = "ManyToOne";

                } else if (field.isAnnotationPresent(
                        "OneToOne")) {

                    relationshipType = "OneToOne";

                } else if (field.isAnnotationPresent(
                        "OneToMany")) {

                    relationshipType = "OneToMany";

                } else if (field.isAnnotationPresent(
                        "ManyToMany")) {

                    relationshipType = "ManyToMany";
                }

                if (relationshipType == null) {
                    continue;
                }

                for (VariableDeclarator variable :
                        field.getVariables()) {

                    String fieldName =
                            variable.getNameAsString();

                    String fieldType =
                            variable.getType().asString();

                    String toEntity =
                            extractEntityName(fieldType);

                    if (toEntity != null) {

                        EntityRelationship relationship =
                                new EntityRelationship(
                                        fromEntity,
                                        toEntity,
                                        relationshipType,
                                        fieldName
                                );

                        relationships.add(relationship);
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error analyzing relationships: "
                            + file.getName()
            );
        }

        return relationships;
    }

    private static String extractEntityName(String fieldType) {

        if (fieldType == null || fieldType.isEmpty()) {
            return null;
        }

        if (fieldType.startsWith("List<")) {

            return fieldType
                    .replace("List<", "")
                    .replace(">", "")
                    .trim();
        }

        if (fieldType.startsWith("Set<")) {

            return fieldType
                    .replace("Set<", "")
                    .replace(">", "")
                    .trim();
        }

        if (fieldType.startsWith("Collection<")) {

            return fieldType
                    .replace("Collection<", "")
                    .replace(">", "")
                    .trim();
        }

        return fieldType.trim();
    }
}