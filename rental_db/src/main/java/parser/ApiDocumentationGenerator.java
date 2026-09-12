package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiDocumentationGenerator {

    public static void main(String[] args) {

        String projectPath;

        if (args.length > 0 && !args[0].isBlank()) {
            projectPath = args[0];
        } else {
            projectPath = "src/main/java";
        }

        File projectDirectory = new File(projectPath);

        if (!projectDirectory.exists()) {

            System.out.println(
                    "ERROR: Project path does not exist!"
            );

            System.out.println(
                    "Path: "
                            + projectDirectory.getAbsolutePath()
            );

            return;
        }

        if (!projectDirectory.isDirectory()) {

            System.out.println(
                    "ERROR: The provided path is not a directory!"
            );

            System.out.println(
                    "Path: "
                            + projectDirectory.getAbsolutePath()
            );

            return;
        }

        System.out.println(
                "\n================================="
        );

        System.out.println(
                "API DOCUMENTATION GENERATOR"
        );

        System.out.println(
                "================================="
        );

        System.out.println(
                "\nScanning project:"
        );

        System.out.println(
                projectDirectory.getAbsolutePath()
        );

        ControllerParser.clearEndpoints();

        List<File> javaFiles =
                ProjectScanner.scanJavaFiles(
                        projectDirectory.getPath()
                );

        System.out.println(
                "\n========== PROJECT STRUCTURE ==========\n"
        );

        int controllerCount = 0;
        int dtoCount = 0;
        int entityCount = 0;
        int serviceCount = 0;
        int repositoryCount = 0;
        int parserCount = 0;

        for (File file : javaFiles) {

            String type =
                    FileTypeDetector.detect(file);

            System.out.printf(
                    "%-15s : %s%n",
                    type,
                    file.getName()
            );

            switch (type) {

                case "Controller":
                    controllerCount++;
                    break;

                case "DTO":
                    dtoCount++;
                    break;

                case "Entity":
                    entityCount++;
                    break;

                case "Service":
                    serviceCount++;
                    break;

                case "Repository":
                    repositoryCount++;
                    break;

                case "Parser":
                    parserCount++;
                    break;

                default:
                    break;
            }

            if (type.equals("Controller")) {

                ControllerParser.parse(file);
            }
        }

        // =========================================================
        // DTO SCAN
        // =========================================================

        Map<String, DTOInfo> dtoData =
                DTOScanner.scan(javaFiles);

        // =========================================================
        // ENTITY SCAN
        // =========================================================

        List<EntityInfo> entityData =
                EntityScanner.scan(javaFiles);

        // =========================================================
        // ENTITY MAP
        // =========================================================

        Map<String, EntityInfo> entityMap =
                new LinkedHashMap<>();

        for (EntityInfo entity : entityData) {

            entityMap.put(
                    entity.getName(),
                    entity
            );
        }

        // =========================================================
        // RELATIONSHIP SCAN
        // =========================================================

        List<EntityRelationship> relationshipData =
                new ArrayList<>();

        for (File file : javaFiles) {

            String type =
                    FileTypeDetector.detect(file);

            if (type.equals("Entity")) {

                List<EntityRelationship> relationships =
                        EntityRelationshipAnalyzer.analyze(file);

                relationshipData.addAll(
                        relationships
                );
            }
        }

        // =========================================================
        // GET ENDPOINTS
        // =========================================================

        List<ApiEndpoint> endpoints =
                ControllerParser.getEndpoints();

        // =========================================================
        // GENERATE MARKDOWN
        // =========================================================

        MarkdownDocumentationGenerator.generate(
                endpoints,
                dtoData,
                entityMap
        );

        // =========================================================
        // GENERATE JSON
        // =========================================================

        generateJsonDocumentation(
                endpoints,
                dtoData,
                entityData,
                entityMap,
                relationshipData,
                javaFiles.size(),
                controllerCount,
                dtoCount,
                entityCount,
                serviceCount,
                repositoryCount,
                parserCount
        );

        // =========================================================
        // COMPLETION MESSAGE
        // =========================================================

        System.out.println(
                "\n================================="
        );

        System.out.println(
                "DOCUMENTATION GENERATION COMPLETED"
        );

        System.out.println(
                "================================="
        );

        System.out.println(
                "\nProject Summary:"
        );

        System.out.println(
                "---------------------------------"
        );

        System.out.println(
                "Total Java Files : "
                        + javaFiles.size()
        );

        System.out.println(
                "Controllers      : "
                        + controllerCount
        );

        System.out.println(
                "DTOs             : "
                        + dtoCount
        );

        System.out.println(
                "Entities         : "
                        + entityCount
        );

        System.out.println(
                "Services         : "
                        + serviceCount
        );

        System.out.println(
                "Repositories     : "
                        + repositoryCount
        );

        System.out.println(
                "Parser Classes   : "
                        + parserCount
        );

        System.out.println(
                "Relationships    : "
                        + relationshipData.size()
        );

        System.out.println(
                "\nGenerated Files:"
        );

        System.out.println(
                "---------------------------------"
        );

        System.out.println(
                "1. api-documentation.json"
        );

        System.out.println(
                "2. API-DOCUMENTATION.md"
        );

        System.out.println(
                "\n================================="
        );

        System.out.println(
                "Documentation is ready!"
        );

        System.out.println(
                "=================================\n"
        );
    }

    // =========================================================
    // GENERATE JSON DOCUMENTATION
    // =========================================================

    private static void generateJsonDocumentation(
            List<ApiEndpoint> endpoints,
            Map<String, DTOInfo> dtoData,
            List<EntityInfo> entityData,
            Map<String, EntityInfo> entityMap,
            List<EntityRelationship> relationshipData,
            int javaFileCount,
            int controllerCount,
            int dtoCount,
            int entityCount,
            int serviceCount,
            int repositoryCount,
            int parserCount
    ) {

        try {

            Map<String, Object> documentation =
                    new LinkedHashMap<>();

            // =====================================================
            // PROJECT INFORMATION
            // =====================================================

            Map<String, Object> project =
                    new LinkedHashMap<>();

            project.put(
                    "name",
                    "Rental Website Backend API"
            );

            project.put(
                    "description",
                    "Automatically generated API documentation for the Rental Website Spring Boot backend."
            );

            project.put(
                    "baseUrl",
                    "http://localhost:8080"
            );

            documentation.put(
                    "project",
                    project
            );

            // =====================================================
            // STATISTICS
            // =====================================================

            Map<String, Object> statistics =
                    new LinkedHashMap<>();

            statistics.put(
                    "javaFiles",
                    javaFileCount
            );

            statistics.put(
                    "controllers",
                    controllerCount
            );

            statistics.put(
                    "dtos",
                    dtoCount
            );

            statistics.put(
                    "entities",
                    entityCount
            );

            statistics.put(
                    "services",
                    serviceCount
            );

            statistics.put(
                    "repositories",
                    repositoryCount
            );

            statistics.put(
                    "parserClasses",
                    parserCount
            );

            statistics.put(
                    "endpoints",
                    endpoints.size()
            );

            statistics.put(
                    "relationships",
                    relationshipData.size()
            );

            documentation.put(
                    "statistics",
                    statistics
            );

            // =====================================================
            // ENDPOINTS
            // =====================================================

            List<Map<String, Object>> apiList =
                    new ArrayList<>();

            for (ApiEndpoint endpoint : endpoints) {

                Map<String, Object> api =
                        new LinkedHashMap<>();

                api.put(
                        "httpMethod",
                        endpoint.getHttpMethod()
                );

                api.put(
                        "endpoint",
                        endpoint.getEndpoint()
                );

                api.put(
                        "controller",
                        endpoint.getController()
                );

                api.put(
                        "methodName",
                        endpoint.getMethodName()
                );

                api.put(
                        "description",
                        endpoint.getDescription()
                );

                String endpointType =
                        endpoint.getEndpoint()
                                .startsWith("/admin")
                                ? "Admin/Web"
                                : "REST";

                api.put(
                        "type",
                        endpointType
                );

                // =================================================
                // PARAMETERS
                // =================================================

                List<Map<String, Object>> parameters =
                        new ArrayList<>();

                if (endpoint.getParameters() != null) {

                    for (
                            ApiEndpoint.ApiParameter parameter
                            : endpoint.getParameters()
                    ) {

                        Map<String, Object> parameterData =
                                new LinkedHashMap<>();

                        parameterData.put(
                                "name",
                                parameter.getName()
                        );

                        parameterData.put(
                                "type",
                                parameter.getType()
                        );

                        parameterData.put(
                                "location",
                                parameter.getLocation()
                        );

                        parameterData.put(
                                "required",
                                parameter.isRequired()
                        );

                        parameters.add(
                                parameterData
                        );
                    }
                }

                api.put(
                        "parameters",
                        parameters
                );

                // =================================================
                // REQUEST BODY
                // =================================================

                if (endpoint.hasRequestBody()) {

                    Map<String, Object> requestBody =
                            new LinkedHashMap<>();

                    String requestBodyType =
                            endpoint.getRequestBody();

                    requestBody.put(
                            "type",
                            requestBodyType
                    );

                    requestBody.put(
                            "contentType",
                            "application/json"
                    );

                    DTOInfo dtoInfo =
                            dtoData.get(requestBodyType);

                    if (dtoInfo != null) {

                        requestBody.put(
                                "fields",
                                dtoInfo.getFields()
                        );

                    } else {

                        requestBody.put(
                                "fields",
                                new ArrayList<>()
                        );
                    }

                    api.put(
                            "requestBody",
                            requestBody
                    );

                } else {

                    api.put(
                            "requestBody",
                            null
                    );
                }

                // =================================================
                // RESPONSE
                // =================================================

                Map<String, Object> response =
                        new LinkedHashMap<>();

                String returnType =
                        endpoint.getReturnType();

                response.put(
                        "returnType",
                        returnType
                );

                response.put(
                        "contentType",
                        endpointType.equals("REST")
                                ? "application/json"
                                : "text/html"
                );

                int statusCode = 200;

                try {

                    statusCode =
                            Integer.parseInt(
                                    endpoint.getStatus()
                            );

                } catch (Exception ignored) {

                    statusCode = 200;
                }

                response.put(
                        "status",
                        statusCode
                );

                // =================================================
                // RESPONSE FIELDS
                // =================================================

                List<String> responseFields =
                        getResponseFields(
                                returnType,
                                entityMap
                        );

                if (!responseFields.isEmpty()) {

                    response.put(
                            "fields",
                            responseFields
                    );
                }

                api.put(
                        "response",
                        response
                );

                apiList.add(api);
            }

            documentation.put(
                    "endpoints",
                    apiList
            );

            // =====================================================
            // DTOs
            // =====================================================

            documentation.put(
                    "dtos",
                    dtoData
            );

            // =====================================================
            // ENTITIES
            // =====================================================

            documentation.put(
                    "entities",
                    entityData
            );

            // =====================================================
            // RELATIONSHIPS
            // =====================================================

            List<Map<String, Object>> relationships =
                    new ArrayList<>();

            for (
                    EntityRelationship relationship
                    : relationshipData
            ) {

                Map<String, Object> relationshipMap =
                        new LinkedHashMap<>();

                relationshipMap.put(
                        "fromEntity",
                        relationship.getFromEntity()
                );

                relationshipMap.put(
                        "toEntity",
                        relationship.getToEntity()
                );

                relationshipMap.put(
                        "relationshipType",
                        relationship.getRelationshipType()
                );

                relationshipMap.put(
                        "fieldName",
                        relationship.getFieldName()
                );

                relationships.add(
                        relationshipMap
                );
            }

            documentation.put(
                    "relationships",
                    relationships
            );

            // =====================================================
            // GENERATOR INFORMATION
            // =====================================================

            Map<String, Object> generator =
                    new LinkedHashMap<>();

            generator.put(
                    "name",
                    "Rental Website API Documentation Generator"
            );

            generator.put(
                    "format",
                    "JSON"
            );

            generator.put(
                    "automaticallyGenerated",
                    true
            );

            documentation.put(
                    "generator",
                    generator
            );

            // =====================================================
            // WRITE JSON FILE
            // =====================================================

            ObjectMapper mapper =
                    new ObjectMapper();

            mapper.enable(
                    SerializationFeature.INDENT_OUTPUT
            );

            File outputFile =
                    new File(
                            "api-documentation.json"
                    );

            mapper.writeValue(
                    outputFile,
                    documentation
            );

            System.out.println(
                    "\nJSON documentation generated successfully:"
            );

            System.out.println(
                    outputFile.getAbsolutePath()
            );

        } catch (Exception e) {

            System.out.println(
                    "\nERROR: Failed to generate JSON documentation."
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // GET RESPONSE FIELDS
    // =========================================================

    private static List<String> getResponseFields(
            String returnType,
            Map<String, EntityInfo> entityMap
    ) {

        List<String> fields =
                new ArrayList<>();

        if (returnType == null
                || returnType.isBlank()) {

            return fields;
        }

        String entityName =
                extractEntityName(returnType);

        if (entityName == null
                || entityName.isBlank()) {

            return fields;
        }

        EntityInfo entity =
                entityMap.get(entityName);

        if (entity != null) {

            fields.addAll(
                    entity.getFields()
            );
        }

        return fields;
    }

    // =========================================================
    // EXTRACT ENTITY NAME
    // =========================================================

    private static String extractEntityName(
            String returnType) {

        if (returnType == null
                || returnType.isBlank()) {

            return null;
        }

        String value =
                returnType.trim();

        if (value.startsWith("List<")
                && value.endsWith(">")) {

            value =
                    value.substring(
                            5,
                            value.length() - 1
                    );
        }

        else if (value.startsWith("Set<")
                && value.endsWith(">")) {

            value =
                    value.substring(
                            4,
                            value.length() - 1
                    );
        }

        else if (value.startsWith("Collection<")
                && value.endsWith(">")) {

            value =
                    value.substring(
                            11,
                            value.length() - 1
                    );
        }

        return value.trim();
    }
}