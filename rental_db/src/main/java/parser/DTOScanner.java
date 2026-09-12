package parser;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DTOScanner {


    public static Map<String, DTOInfo> scan(List<File> files) {


        Map<String, DTOInfo> dtoMap =
                new HashMap<>();



        for(File file : files) {



            if(file.getName()
                    .endsWith("DTO.java")) {



                DTOInfo info =
                        DTOAnalyzer.analyze(file);



                dtoMap.put(
                        info.getName(),
                        info
                );

            }

        }


        return dtoMap;

    }

}