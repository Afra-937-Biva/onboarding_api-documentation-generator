package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControllerParser {

    private static final List<ApiEndpoint> endpoints =
            new ArrayList<>();

    // =========================================================
    // PARSE CONTROLLER
    // =========================================================

    public static void parse(File file) {

        try {

            CompilationUnit cu =
                    StaticJavaParser.parse(file);

            String baseUrl = "";

            ClassOrInterfaceDeclaration controllerClass =
                    cu.findFirst(
                            ClassOrInterfaceDeclaration.class
                    ).orElse(null);

            if (controllerClass == null) {
                return;
            }

            // =====================================================
            // CLASS LEVEL @RequestMapping
            // =====================================================

            for (AnnotationExpr annotation :
                    controllerClass.getAnnotations()) {

                if (annotation.getNameAsString()
                        .equals("RequestMapping")) {

                    baseUrl =
                            getAnnotationValue(annotation);

                    break;
                }
            }

            // =====================================================
            // CONTROLLER INFORMATION
            // =====================================================

            System.out.println();

            System.out.println(
                    "===================================="
            );

            System.out.println(
                    "Controller : "
                            + file.getName()
            );

            System.out.println(
                    "Base URL : "
                            + baseUrl
            );

            System.out.println(
                    "===================================="
            );

            // =====================================================
            // METHODS
            // =====================================================

            for (MethodDeclaration method :
                    cu.findAll(
                            MethodDeclaration.class
                    )) {

                String httpMethod = "";
                String endpoint = "";

                // =================================================
                // FIND HTTP MAPPING
                // =================================================

                for (AnnotationExpr annotation :
                        method.getAnnotations()) {

                    String annotationName =
                            annotation
                                    .getNameAsString();

                    if (annotationName.equals("GetMapping")) {

                        httpMethod = "GET";

                    } else if (
                            annotationName.equals("PostMapping")) {

                        httpMethod = "POST";

                    } else if (
                            annotationName.equals("PutMapping")) {

                        httpMethod = "PUT";

                    } else if (
                            annotationName.equals("DeleteMapping")) {

                        httpMethod = "DELETE";
                    }

                    if (!httpMethod.isEmpty()) {

                        endpoint =
                                getAnnotationValue(annotation);

                        break;
                    }
                }

                // No REST mapping found
                if (httpMethod.isEmpty()) {
                    continue;
                }

                // =================================================
                // PARAMETERS
                // =================================================

                List<ApiEndpoint.ApiParameter> parameters =
                        new ArrayList<>();

                String requestBody = "";

                for (Parameter parameter :
                        method.getParameters()) {

                    // ---------------------------------------------
                    // @PathVariable
                    // ---------------------------------------------

                    AnnotationExpr pathVariable =
                            findAnnotation(
                                    parameter,
                                    "PathVariable"
                            );

                    if (pathVariable != null) {

                        String parameterName =
                                getParameterName(
                                        parameter,
                                        pathVariable
                                );

                        String parameterType =
                                parameter
                                        .getType()
                                        .asString();

                        parameters.add(
                                new ApiEndpoint.ApiParameter(
                                        parameterName,
                                        parameterType,
                                        "Path",
                                        true
                                )
                        );
                    }

                    // ---------------------------------------------
                    // @RequestParam
                    // ---------------------------------------------

                    AnnotationExpr requestParam =
                            findAnnotation(
                                    parameter,
                                    "RequestParam"
                            );

                    if (requestParam != null) {

                        String parameterName =
                                getParameterName(
                                        parameter,
                                        requestParam
                                );

                        String parameterType =
                                parameter
                                        .getType()
                                        .asString();

                        boolean required =
                                getRequiredValue(
                                        requestParam
                                );

                        parameters.add(
                                new ApiEndpoint.ApiParameter(
                                        parameterName,
                                        parameterType,
                                        "Query",
                                        required
                                )
                        );
                    }

                    // ---------------------------------------------
                    // @RequestBody
                    // ---------------------------------------------

                    AnnotationExpr requestBodyAnnotation =
                            findAnnotation(
                                    parameter,
                                    "RequestBody"
                            );

                    if (requestBodyAnnotation != null) {

                        requestBody =
                                parameter
                                        .getType()
                                        .asString();
                    }
                }

                // =================================================
                // RETURN TYPE
                // =================================================

                String returnType =
                        method
                                .getType()
                                .asString();

                // =================================================
                // CONTROLLER NAME
                // =================================================

                String controllerName =
                        file
                                .getName()
                                .replace(
                                        ".java",
                                        ""
                                );

                // =================================================
                // DESCRIPTION
                // =================================================

                String description =
                        generateDescription(
                                method.getNameAsString()
                        );

                // =================================================
                // STATUS CODE
                // =================================================

                String status =
                        getStatusCode(method);

                // =================================================
                // FULL ENDPOINT
                // =================================================

                String fullEndpoint =
                        combinePaths(
                                baseUrl,
                                endpoint
                        );

                // =================================================
                // CREATE API ENDPOINT
                // =================================================

                ApiEndpoint api =
                        new ApiEndpoint(
                                httpMethod,
                                fullEndpoint,
                                controllerName,
                                method.getNameAsString(),
                                description,
                                status,
                                parameters,
                                requestBody,
                                returnType
                        );

                endpoints.add(api);

                System.out.println(api);
            }

        } catch (Exception e) {

            System.out.println(
                    "ERROR while parsing: "
                            + file.getName()
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // GET STATUS CODE
    // =========================================================

    private static String getStatusCode(
            MethodDeclaration method) {

        // ---------------------------------------------------------
        // Check explicit @ResponseStatus
        // ---------------------------------------------------------

        for (AnnotationExpr annotation :
                method.getAnnotations()) {

            if (annotation.getNameAsString()
                    .equals("ResponseStatus")) {

                String status =
                        extractResponseStatus(
                                annotation
                        );

                if (!status.isBlank()) {

                    return convertStatusNameToCode(
                            status
                    );
                }
            }
        }

        // ---------------------------------------------------------
        // No explicit @ResponseStatus
        // Default Spring success response = 200
        // ---------------------------------------------------------

        return "200";
    }

    // =========================================================
    // EXTRACT @ResponseStatus VALUE
    // =========================================================

    private static String extractResponseStatus(
            AnnotationExpr annotation) {

        if (annotation == null) {
            return "";
        }

        // @ResponseStatus(HttpStatus.CREATED)
        if (annotation.isSingleMemberAnnotationExpr()) {

            SingleMemberAnnotationExpr single =
                    annotation
                            .asSingleMemberAnnotationExpr();

            return cleanStatusValue(
                    single
                            .getMemberValue()
                            .toString()
            );
        }

        // @ResponseStatus(
        //     code = HttpStatus.CREATED
        // )
        //
        // OR
        //
        // @ResponseStatus(
        //     value = HttpStatus.CREATED
        // )

        if (annotation.isNormalAnnotationExpr()) {

            NormalAnnotationExpr normal =
                    annotation
                            .asNormalAnnotationExpr();

            for (MemberValuePair pair :
                    normal.getPairs()) {

                String name =
                        pair.getNameAsString();

                if (name.equals("code")
                        || name.equals("value")) {

                    return cleanStatusValue(
                            pair
                                    .getValue()
                                    .toString()
                    );
                }
            }
        }

        return "";
    }

    // =========================================================
    // CONVERT STATUS NAME TO CODE
    // =========================================================

    private static String convertStatusNameToCode(
            String status) {

        if (status == null
                || status.isBlank()) {

            return "200";
        }

        String value =
                status
                        .trim()
                        .toUpperCase();

        // Direct numeric status
        if (value.matches("\\d{3}")) {
            return value;
        }

        if (value.contains("OK")) {
            return "200";
        }

        if (value.contains("CREATED")) {
            return "201";
        }

        if (value.contains("ACCEPTED")) {
            return "202";
        }

        if (value.contains("NO_CONTENT")) {
            return "204";
        }

        if (value.contains("BAD_REQUEST")) {
            return "400";
        }

        if (value.contains("UNAUTHORIZED")) {
            return "401";
        }

        if (value.contains("FORBIDDEN")) {
            return "403";
        }

        if (value.contains("NOT_FOUND")) {
            return "404";
        }

        if (value.contains("METHOD_NOT_ALLOWED")) {
            return "405";
        }

        if (value.contains("CONFLICT")) {
            return "409";
        }

        if (value.contains("INTERNAL_SERVER_ERROR")) {
            return "500";
        }

        return "200";
    }

    // =========================================================
    // CLEAN STATUS VALUE
    // =========================================================

    private static String cleanStatusValue(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .trim()
                .replace("\"", "")
                .replace("HttpStatus.", "")
                .replace("HttpStatusCode.", "");
    }

    // =========================================================
    // GENERATE DESCRIPTION
    // =========================================================

    private static String generateDescription(
            String methodName) {

        if (methodName == null
                || methodName.isBlank()) {

            return "";
        }

        String description =
                methodName
                        .replaceAll(
                                "([a-z])([A-Z])",
                                "$1 $2"
                        )
                        .replace("_", " ")
                        .replace("-", " ")
                        .trim();

        if (description.equalsIgnoreCase("create")) {
            return "Create";
        }

        if (description.equalsIgnoreCase("getAll")) {
            return "Get all";
        }

        if (description.equalsIgnoreCase("getById")) {
            return "Get by ID";
        }

        if (description.equalsIgnoreCase("update")) {
            return "Update";
        }

        if (description.equalsIgnoreCase("delete")) {
            return "Delete";
        }

        String[] words =
                description.split("\\s+");

        StringBuilder result =
                new StringBuilder();

        for (String word : words) {

            if (word.isBlank()) {
                continue;
            }

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );

            if (word.length() > 1) {

                result.append(
                        word.substring(1)
                                .toLowerCase()
                );
            }
        }

        return result.toString();
    }

    // =========================================================
    // FIND ANNOTATION
    // =========================================================

    private static AnnotationExpr findAnnotation(
            Parameter parameter,
            String annotationName) {

        for (AnnotationExpr annotation :
                parameter.getAnnotations()) {

            if (annotation
                    .getNameAsString()
                    .equals(annotationName)) {

                return annotation;
            }
        }

        return null;
    }

    // =========================================================
    // GET ANNOTATION VALUE
    // =========================================================

    private static String getAnnotationValue(
            AnnotationExpr annotation) {

        if (annotation == null) {
            return "";
        }

        // @GetMapping("/test")
        if (annotation.isSingleMemberAnnotationExpr()) {

            SingleMemberAnnotationExpr single =
                    annotation
                            .asSingleMemberAnnotationExpr();

            return cleanAnnotationValue(
                    single
                            .getMemberValue()
                            .toString()
            );
        }

        // @GetMapping
        // @GetMapping(value = "/test")
        // @GetMapping(path = "/test")

        if (annotation.isNormalAnnotationExpr()) {

            NormalAnnotationExpr normal =
                    annotation
                            .asNormalAnnotationExpr();

            for (MemberValuePair pair :
                    normal.getPairs()) {

                String name =
                        pair
                                .getNameAsString();

                if (name.equals("value")
                        || name.equals("path")) {

                    return cleanAnnotationValue(
                            pair
                                    .getValue()
                                    .toString()
                    );
                }
            }
        }

        return "";
    }

    // =========================================================
    // GET PARAMETER NAME
    // =========================================================

    private static String getParameterName(
            Parameter parameter,
            AnnotationExpr annotation) {

        if (annotation == null) {
            return parameter.getNameAsString();
        }

        // @RequestParam("location")

        if (annotation.isSingleMemberAnnotationExpr()) {

            return cleanAnnotationValue(
                    annotation
                            .asSingleMemberAnnotationExpr()
                            .getMemberValue()
                            .toString()
            );
        }

        // @RequestParam(
        //      value = "location",
        //      required = false
        // )

        if (annotation.isNormalAnnotationExpr()) {

            NormalAnnotationExpr normal =
                    annotation
                            .asNormalAnnotationExpr();

            for (MemberValuePair pair :
                    normal.getPairs()) {

                String name =
                        pair
                                .getNameAsString();

                if (name.equals("value")
                        || name.equals("name")) {

                    String value =
                            cleanAnnotationValue(
                                    pair
                                            .getValue()
                                            .toString()
                            );

                    if (!value.isBlank()) {
                        return value;
                    }
                }
            }
        }

        return parameter.getNameAsString();
    }

    // =========================================================
    // GET REQUIRED VALUE
    // =========================================================

    private static boolean getRequiredValue(
            AnnotationExpr annotation) {

        // @RequestParam
        // Default value is true.

        if (annotation == null) {
            return true;
        }

        if (annotation.isNormalAnnotationExpr()) {

            NormalAnnotationExpr normal =
                    annotation
                            .asNormalAnnotationExpr();

            for (MemberValuePair pair :
                    normal.getPairs()) {

                if (pair
                        .getNameAsString()
                        .equals("required")) {

                    String value =
                            pair
                                    .getValue()
                                    .toString()
                                    .trim();

                    return !value.equalsIgnoreCase("false");
                }
            }
        }

        return true;
    }

    // =========================================================
    // CLEAN ANNOTATION VALUE
    // =========================================================

    private static String cleanAnnotationValue(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .trim()
                .replace("\"", "");
    }

    // =========================================================
    // COMBINE URL PATHS
    // =========================================================

    private static String combinePaths(
            String baseUrl,
            String endpoint) {

        if (baseUrl == null
                || baseUrl.isBlank()) {

            if (endpoint == null
                    || endpoint.isBlank()) {

                return "/";
            }

            return endpoint.startsWith("/")
                    ? endpoint
                    : "/" + endpoint;
        }

        if (endpoint == null
                || endpoint.isBlank()) {

            return baseUrl.startsWith("/")
                    ? baseUrl
                    : "/" + baseUrl;
        }

        String cleanBase =
                baseUrl.startsWith("/")
                        ? baseUrl
                        : "/" + baseUrl;

        String cleanEndpoint =
                endpoint.startsWith("/")
                        ? endpoint
                        : "/" + endpoint;

        return cleanBase
                + cleanEndpoint;
    }

    // =========================================================
    // GET ENDPOINTS
    // =========================================================

    public static List<ApiEndpoint> getEndpoints() {

        return endpoints;
    }

    // =========================================================
    // CLEAR ENDPOINTS
    // =========================================================

    public static void clearEndpoints() {

        endpoints.clear();
    }
}