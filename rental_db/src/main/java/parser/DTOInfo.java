package parser;

import java.util.List;


public class DTOInfo {


    private String name;

    private List<String> fields;



    public DTOInfo(String name, List<String> fields) {

        this.name = name;
        this.fields = fields;

    }



    public String getName() {

        return name;

    }



    public List<String> getFields() {

        return fields;

    }


}