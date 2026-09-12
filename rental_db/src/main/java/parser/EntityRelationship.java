package parser;

public class EntityRelationship {

    private String fromEntity;
    private String toEntity;
    private String relationshipType;
    private String fieldName;

    public EntityRelationship(
            String fromEntity,
            String toEntity,
            String relationshipType,
            String fieldName) {

        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        this.relationshipType = relationshipType;
        this.fieldName = fieldName;
    }

    public String getFromEntity() {
        return fromEntity;
    }

    public String getToEntity() {
        return toEntity;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {

        return fromEntity
                + " --[" + relationshipType + "]--> "
                + toEntity
                + " (" + fieldName + ")";
    }
}