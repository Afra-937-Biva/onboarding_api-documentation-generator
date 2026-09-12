package parser;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class DTOAnalyzer {



    public static DTOInfo analyze(File file) {


        try {


            CompilationUnit cu =
                    StaticJavaParser.parse(file);



            String dtoName =
                    file.getName()
                    .replace(".java","");



            List<String> fields =
                    new ArrayList<>();



            for(FieldDeclaration field :
                    cu.findAll(FieldDeclaration.class)) {



                String type =
                        field.getElementType()
                        .asString();



                field.getVariables()
                        .forEach(variable -> {


                            fields.add(
                                    variable.getNameAsString()
                                    + " : "
                                    + type
                            );


                        });


            }



            return new DTOInfo(
                    dtoName,
                    fields
            );


        }

        catch(Exception e) {


            e.printStackTrace();

        }


        return null;

    }


}