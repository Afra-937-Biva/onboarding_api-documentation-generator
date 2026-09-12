package parser;

import java.util.ArrayList;
import java.util.List;

public class EntityInfo {

    private String name;
    private List<String> fields = new ArrayList<>();

    public EntityInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getFields() {
        return fields;
    }

    public void addField(String field) {
        fields.add(field);
    }

    @Override
    public String toString() {
        return "EntityInfo{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}