package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EntityScanner {

    public static List<EntityInfo> scan(List<File> javaFiles) {

        List<EntityInfo> entities = new ArrayList<>();

        for (File file : javaFiles) {

            if (!file.getName().endsWith(".java")) {
                continue;
            }

            try {

                CompilationUnit cu =
                        StaticJavaParser.parse(file);

                boolean isEntity = false;

                for (AnnotationExpr annotation :
                        cu.findAll(AnnotationExpr.class)) {

                    String annotationName =
                            annotation.getNameAsString();

                    if (annotationName.equals("Entity")) {
                        isEntity = true;
                        break;
                    }
                }

                if (isEntity) {

                    EntityInfo entity =
                            EntityAnalyzer.analyze(file);

                    if (entity != null) {
                        entities.add(entity);
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Error scanning entity: "
                                + file.getName()
                );
            }
        }

        return entities;
    }
}