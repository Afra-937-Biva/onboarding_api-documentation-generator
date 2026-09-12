package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MarkdownDocumentationGenerator {

    private static final String OUTPUT_FILE = "API-DOCUMENTATION.md";

    public static void generate(
            List<ApiEndpoint> endpoints,
            Map<String, DTOInfo> dtoMap,
            Map<String, EntityInfo> entityMap
    ) {

        StringBuilder md = new StringBuilder();

        md.append("# Rental Website Backend API Documentation\n\n");

        md.append("## Overview\n\n");
        md.append("This document contains automatically generated API documentation ");
        md.append("for the Rental Website Spring Boot backend.\n\n");

        md.append("**Total Endpoints:** ")
                .append(endpoints.size())
                .append("\n\n");

        md.append("**Controllers:** ")
                .append(endpoints.stream()
                        .map(ApiEndpoint::getController)
                        .distinct()
                        .count())
                .append("\n\n");

        md.append("**DTOs:** ")
                .append(dtoMap.size())
                .append("\n\n");

        md.append("**Entities:** ")
                .append(entityMap.size())
                .append("\n\n");

        md.append("## API Endpoints\n\n");

        for (ApiEndpoint endpoint : endpoints) {

            md.append("### ")
                    .append(endpoint.getHttpMethod())
                    .append(" ")
                    .append(endpoint.getEndpoint())
                    .append("\n\n");

            md.append("| Property | Value |\n");
            md.append("|---|---|\n");

            md.append("| Controller | `")
                    .append(endpoint.getController())
                    .append("` |\n");

            md.append("| Method | `")
                    .append(endpoint.getMethodName())
                    .append("` |\n");

            md.append("| Description | ")
                    .append(endpoint.getDescription())
                    .append(" |\n");

            md.append("| Status | `")
                    .append(endpoint.getStatus())
                    .append("` |\n");

            if (endpoint.hasParameters()) {

                md.append("\n#### Parameters\n\n");

                md.append("| Name | Type | Location | Required |\n");
                md.append("|---|---|---|---|\n");

                for (ApiEndpoint.ApiParameter parameter :
                        endpoint.getParameters()) {

                    md.append("| `")
                            .append(parameter.getName())
                            .append("` | `")
                            .append(parameter.getType())
                            .append("` | ")
                            .append(parameter.getLocation())
                            .append(" | ")
                            .append(parameter.isRequired())
                            .append(" |\n");
                }
            }

            if (endpoint.hasRequestBody()) {

                md.append("\n#### Request Body\n\n");

                md.append("```json\n");
                md.append(generateRequestExample(endpoint, dtoMap));
                md.append("\n```\n");
            }

            md.append("\n#### Response\n\n");

            String returnType =
                    cleanReturnType(endpoint.getReturnType());

            String responseEntity =
                    findEntityName(returnType, entityMap);

            String responseDTO =
                    findDTOName(returnType, dtoMap);

            /*
             * ENTITY RESPONSE
             */
            if (responseEntity != null) {

                EntityInfo entity =
                        entityMap.get(responseEntity);

                md.append("**Response Entity:** `")
                        .append(responseEntity)
                        .append("`\n\n");

                md.append("**Response Type:** `")
                        .append(returnType)
                        .append("`\n\n");

                md.append("**Response Fields:**\n\n");

                md.append("| Field | Type |\n");
                md.append("|---|---|\n");

                for (String field : entity.getFields()) {

                    String[] parts =
                            field.split(" : ", 2);

                    String fieldName =
                            parts[0];

                    String fieldType =
                            parts.length > 1
                                    ? parts[1]
                                    : "unknown";

                    md.append("| `")
                            .append(fieldName)
                            .append("` | `")
                            .append(fieldType)
                            .append("` |\n");
                }

                md.append("\n");

                md.append("**Entity Reference:** `")
                        .append(responseEntity)
                        .append("`\n\n");

                md.append("```json\n");

                if (isCollectionType(returnType)) {

                    md.append("[\n");

                    md.append(
                            generateEntityJson(
                                    entity.getFields(),
                                    2,
                                    entityMap,
                                    0
                            )
                    );

                    md.append("\n]");

                } else {

                    md.append(
                            generateEntityJson(
                                    entity.getFields(),
                                    0,
                                    entityMap,
                                    0
                            )
                    );
                }

                md.append("\n```\n");

            /*
             * DTO RESPONSE
             */
            } else if (responseDTO != null) {

                DTOInfo dto =
                        dtoMap.get(responseDTO);

                md.append("**Response DTO:** `")
                        .append(responseDTO)
                        .append("`\n\n");

                md.append("**Response Type:** `")
                        .append(returnType)
                        .append("`\n\n");

                md.append("**DTO Fields:**\n\n");

                md.append("| Field | Type |\n");
                md.append("|---|---|\n");

                for (String field : dto.getFields()) {

                    String[] parts =
                            field.split(" : ", 2);

                    String fieldName =
                            parts[0];

                    String fieldType =
                            parts.length > 1
                                    ? parts[1]
                                    : "unknown";

                    md.append("| `")
                            .append(fieldName)
                            .append("` | `")
                            .append(fieldType)
                            .append("` |\n");
                }

                md.append("\n");

                md.append("**DTO Reference:** `")
                        .append(responseDTO)
                        .append("`\n\n");

                md.append("```json\n");

                if (isCollectionType(returnType)) {

                    md.append("[\n");

                    md.append(
                            generateDTOJson(
                                    dto.getFields(),
                                    2
                            )
                    );

                    md.append("\n]");

                } else {

                    md.append(
                            generateDTOJson(
                                    dto.getFields()
                            )
                    );
                }

                md.append("\n```\n");

            /*
             * UNKNOWN RESPONSE
             */
            } else {

                md.append("**Return Type:** `")
                        .append(returnType)
                        .append("`\n\n");

                md.append("```json\n");
                md.append("\"example\"\n");
                md.append("```\n");
            }

            md.append("\n---\n\n");
        }

        /*
         * DTO DOCUMENTATION
         */
        md.append("## DTO Documentation\n\n");

        if (dtoMap.isEmpty()) {

            md.append("No DTOs detected.\n\n");

        } else {

            for (DTOInfo dto : dtoMap.values()) {

                md.append("### ")
                        .append(dto.getName())
                        .append("\n\n");

                md.append("| Field | Type |\n");
                md.append("|---|---|\n");

                for (String field : dto.getFields()) {

                    String[] parts =
                            field.split(" : ", 2);

                    String fieldName =
                            parts[0];

                    String fieldType =
                            parts.length > 1
                                    ? parts[1]
                                    : "unknown";

                    md.append("| `")
                            .append(fieldName)
                            .append("` | `")
                            .append(fieldType)
                            .append("` |\n");
                }

                md.append("\n");
            }
        }

        /*
         * ENTITY DOCUMENTATION
         */
        md.append("## Entity Documentation\n\n");

        if (entityMap.isEmpty()) {

            md.append("No entities detected.\n\n");

        } else {

            for (EntityInfo entity : entityMap.values()) {

                md.append("### ")
                        .append(entity.getName())
                        .append("\n\n");

                md.append("| Field | Type |\n");
                md.append("|---|---|\n");

                for (String field : entity.getFields()) {

                    String[] parts =
                            field.split(" : ", 2);

                    String fieldName =
                            parts[0];

                    String fieldType =
                            parts.length > 1
                                    ? parts[1]
                                    : "unknown";

                    md.append("| `")
                            .append(fieldName)
                            .append("` | `")
                            .append(fieldType)
                            .append("` |\n");
                }

                md.append("\n");
            }
        }

        /*
         * ENTITY RELATIONSHIPS
         */
        md.append("## Entity Relationships\n\n");

        md.append("Entity relationships detected from JPA annotations.\n\n");

        for (EntityInfo entity : entityMap.values()) {

            for (String field : entity.getFields()) {

                String[] parts =
                        field.split(" : ", 2);

                if (parts.length < 2) {
                    continue;
                }

                String fieldName =
                        parts[0];

                String fieldType =
                        parts[1];

                String targetEntity =
                        extractEntityName(fieldType);

                if (entityMap.containsKey(targetEntity)) {

                    md.append("- `")
                            .append(entity.getName())
                            .append(".")
                            .append(fieldName)
                            .append("` → `")
                            .append(targetEntity)
                            .append("`\n");
                }
            }
        }

        md.append("\n");

        /*
         * FRONTEND INTEGRATION
         */
        md.append("## Frontend Integration\n\n");

        md.append("The generated API documentation can be consumed by a ");
        md.append("Vue 3 or other frontend application.\n\n");

        md.append("The JSON documentation file contains structured information ");
        md.append("about controllers, endpoints, DTOs, entities and relationships.\n\n");

        md.append("Generated JSON file:\n\n");

        md.append("`api-documentation.json`\n\n");

        /*
         * NOTES
         */
        md.append("## Notes\n\n");

        md.append("- Documentation is generated automatically from Java source files.\n");
        md.append("- Endpoint information is extracted from Spring MVC annotations.\n");
        md.append("- DTO information is extracted from DTO classes.\n");
        md.append("- Entity information is extracted from JPA entity classes.\n");
        md.append("- Entity relationships are detected from JPA relationship annotations.\n");
        md.append("- Response examples are generated from detected entity fields.\n");
        md.append("- Collection responses such as List<Entity> are generated as JSON arrays.\n");
        md.append("- Nested entity examples are limited to one level to avoid circular references.\n");

        try (FileWriter writer =
                     new FileWriter(OUTPUT_FILE)) {

            writer.write(md.toString());

            System.out.println(
                    "Markdown documentation generated successfully: "
                            + OUTPUT_FILE
            );

        } catch (IOException e) {

            System.out.println(
                    "Error writing Markdown documentation: "
                            + e.getMessage()
            );
        }
    }

    /*
     * REQUEST EXAMPLE
     */
    private static String generateRequestExample(
            ApiEndpoint endpoint,
            Map<String, DTOInfo> dtoMap
    ) {

        String requestBody =
                endpoint.getRequestBody();

        if (requestBody == null ||
                requestBody.isBlank()) {

            return "{}";
        }

        String cleanType =
                cleanGenericType(requestBody);

        DTOInfo dto =
                dtoMap.get(cleanType);

        if (dto != null) {

            return generateDTOJson(
                    dto.getFields()
            );
        }

        return "{\n  \"example\": \"value\"\n}";
    }

    /*
     * ENTITY JSON
     */
    private static String generateEntityJson(
            List<String> fields,
            int indent,
            Map<String, EntityInfo> entityMap,
            int depth
    ) {

        StringBuilder json =
                new StringBuilder();

        String spaces =
                " ".repeat(indent);

        String childSpaces =
                " ".repeat(indent + 2);

        json.append(spaces)
                .append("{\n");

        for (int i = 0; i < fields.size(); i++) {

            String field =
                    fields.get(i);

            String[] parts =
                    field.split(" : ", 2);

            String fieldName =
                    parts[0];

            String fieldType =
                    parts.length > 1
                            ? parts[1]
                            : "String";

            json.append(childSpaces)
                    .append("\"")
                    .append(fieldName)
                    .append("\": ");

            json.append(
                    getJsonExampleValue(
                            fieldType,
                            entityMap,
                            depth
                    )
            );

            if (i < fields.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append(spaces)
                .append("}");

        return json.toString();
    }

    /*
     * DTO JSON
     */
    private static String generateDTOJson(
            List<String> fields
    ) {

        return generateDTOJson(fields, 0);
    }

    private static String generateDTOJson(
            List<String> fields,
            int indent
    ) {

        StringBuilder json =
                new StringBuilder();

        String spaces =
                " ".repeat(indent);

        String childSpaces =
                " ".repeat(indent + 2);

        json.append(spaces)
                .append("{\n");

        for (int i = 0; i < fields.size(); i++) {

            String field =
                    fields.get(i);

            String[] parts =
                    field.split(" : ", 2);

            String fieldName =
                    parts[0];

            String fieldType =
                    parts.length > 1
                            ? parts[1]
                            : "String";

            json.append(childSpaces)
                    .append("\"")
                    .append(fieldName)
                    .append("\": ");

            json.append(
                    getBasicJsonValue(fieldType)
            );

            if (i < fields.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append(spaces)
                .append("}");

        return json.toString();
    }

    /*
     * JSON VALUE GENERATOR
     */
    private static String getJsonExampleValue(
            String fieldType,
            Map<String, EntityInfo> entityMap,
            int depth
    ) {

        String cleanType =
                fieldType.trim();

        if (cleanType.startsWith("List<")) {

            String entityName =
                    extractEntityName(cleanType);

            if (entityMap.containsKey(entityName)) {

                if (depth < 1) {

                    String nested =
                            generateEntityJson(
                                    entityMap
                                            .get(entityName)
                                            .getFields(),
                                    2,
                                    entityMap,
                                    depth + 1
                            );

                    return "[\n"
                            + nested
                            + "\n  ]";
                }

                return "[{\"id\": 1}]";
            }

            return "[]";
        }

        if (cleanType.startsWith("Set<")) {

            String entityName =
                    extractEntityName(cleanType);

            if (entityMap.containsKey(entityName)) {

                if (depth < 1) {

                    String nested =
                            generateEntityJson(
                                    entityMap
                                            .get(entityName)
                                            .getFields(),
                                    2,
                                    entityMap,
                                    depth + 1
                            );

                    return "[\n"
                            + nested
                            + "\n  ]";
                }

                return "[{\"id\": 1}]";
            }

            return "[]";
        }

        if (cleanType.startsWith("Collection<")) {

            String entityName =
                    extractEntityName(cleanType);

            if (entityMap.containsKey(entityName)) {

                if (depth < 1) {

                    String nested =
                            generateEntityJson(
                                    entityMap
                                            .get(entityName)
                                            .getFields(),
                                    2,
                                    entityMap,
                                    depth + 1
                            );

                    return "[\n"
                            + nested
                            + "\n  ]";
                }

                return "[{\"id\": 1}]";
            }

            return "[]";
        }

        if (entityMap.containsKey(cleanType)) {

            if (depth < 1) {

                return generateEntityJson(
                        entityMap
                                .get(cleanType)
                                .getFields(),
                        2,
                        entityMap,
                        depth + 1
                );
            }

            return "{\"id\": 1}";
        }

        return getBasicJsonValue(cleanType);
    }

    /*
     * BASIC JSON VALUE
     */
    private static String getBasicJsonValue(
            String fieldType
    ) {

        String type =
                fieldType.toLowerCase();

        if (type.contains("long") ||
                type.contains("int") ||
                type.contains("short") ||
                type.contains("byte")) {

            return "1";
        }

        if (type.contains("double") ||
                type.contains("float") ||
                type.contains("bigdecimal")) {

            return "25000.0";
        }

        if (type.contains("boolean")) {

            return "true";
        }

        if (type.contains("list") ||
                type.contains("set") ||
                type.contains("collection")) {

            return "[]";
        }

        if (type.contains("date") ||
                type.contains("time") ||
                type.contains("timestamp")) {

            return "\"2026-01-01T00:00:00\"";
        }

        return "\"example\"";
    }

    /*
     * FIND ENTITY
     */
    private static String findEntityName(
            String returnType,
            Map<String, EntityInfo> entityMap
    ) {

        if (returnType == null) {
            return null;
        }

        String clean =
                returnType.trim();

        if (entityMap.containsKey(clean)) {
            return clean;
        }

        if (isCollectionType(clean)) {

            String inner =
                    extractEntityName(clean);

            if (entityMap.containsKey(inner)) {
                return inner;
            }
        }

        return null;
    }

    /*
     * FIND DTO
     */
    private static String findDTOName(
            String returnType,
            Map<String, DTOInfo> dtoMap
    ) {

        if (returnType == null) {
            return null;
        }

        String clean =
                returnType.trim();

        if (dtoMap.containsKey(clean)) {
            return clean;
        }

        if (isCollectionType(clean)) {

            String inner =
                    extractEntityName(clean);

            if (dtoMap.containsKey(inner)) {
                return inner;
            }
        }

        return null;
    }

    /*
     * CLEAN RETURN TYPE
     *
     * Important:
     * This method removes only the outer ResponseEntity<>
     * and keeps List<Entity> intact.
     */
    private static String cleanReturnType(
            String returnType
    ) {

        if (returnType == null ||
                returnType.isBlank()) {

            return "void";
        }

        String result =
                returnType.trim();

        if (result.startsWith("ResponseEntity<") &&
                result.endsWith(">")) {

            result =
                    result.substring(
                            "ResponseEntity<".length(),
                            result.length() - 1
                    ).trim();
        }

        return result;
    }

    /*
     * CLEAN GENERIC TYPE
     */
    private static String cleanGenericType(
            String type
    ) {

        if (type == null) {
            return "";
        }

        String result =
                type.trim();

        while (
                result.startsWith("ResponseEntity<") ||
                result.startsWith("Optional<") ||
                result.startsWith("List<") ||
                result.startsWith("Set<")
        ) {

            int start =
                    result.indexOf("<");

            int end =
                    result.lastIndexOf(">");

            if (start == -1 ||
                    end == -1) {
                break;
            }

            result =
                    result.substring(
                            start + 1,
                            end
                    ).trim();
        }

        return result;
    }

    /*
     * CHECK COLLECTION TYPE
     */
    private static boolean isCollectionType(
            String type
    ) {

        if (type == null) {
            return false;
        }

        String clean =
                type.trim();

        return
                (clean.startsWith("List<") &&
                        clean.endsWith(">"))
                ||
                (clean.startsWith("Set<") &&
                        clean.endsWith(">"))
                ||
                (clean.startsWith("Collection<") &&
                        clean.endsWith(">"));
    }

    /*
     * EXTRACT ENTITY NAME
     */
    private static String extractEntityName(
            String fieldType
    ) {

        if (fieldType == null) {
            return "";
        }

        String type =
                fieldType.trim();

        if (type.startsWith("List<") ||
                type.startsWith("Set<") ||
                type.startsWith("Collection<")) {

            int start =
                    type.indexOf("<");

            int end =
                    type.lastIndexOf(">");

            if (start != -1 &&
                    end != -1) {

                return type.substring(
                        start + 1,
                        end
                ).trim();
            }
        }

        return type;
    }
}