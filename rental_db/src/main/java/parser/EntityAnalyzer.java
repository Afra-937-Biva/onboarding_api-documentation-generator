package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.io.File;

public class EntityAnalyzer {

    public static EntityInfo analyze(File file) {

        try {

            CompilationUnit cu =
                    StaticJavaParser.parse(file);

            String entityName =
                    file.getName().replace(".java", "");

            EntityInfo entityInfo =
                    new EntityInfo(entityName);

            for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {

                for (VariableDeclarator variable : field.getVariables()) {

                    String fieldName = variable.getNameAsString();

                    String fieldType =
                            variable.getType().asString();

                    entityInfo.addField(
                            fieldName + " : " + fieldType
                    );
                }
            }

            return entityInfo;

        } catch (Exception e) {

            System.out.println(
                    "Error analyzing entity: " + file.getName()
            );

            return null;
        }
    }
}