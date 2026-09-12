package parser;

import java.util.List;

public class ApiEndpoint {

    private final String httpMethod;
    private final String endpoint;
    private final String controller;
    private final String methodName;
    private final String description;
    private final String status;

    private final List<ApiParameter> parameters;

    private final String requestBody;
    private final String returnType;

    public ApiEndpoint(
            String httpMethod,
            String endpoint,
            String controller,
            String methodName,
            String description,
            String status,
            List<ApiParameter> parameters,
            String requestBody,
            String returnType
    ) {

        this.httpMethod = httpMethod;
        this.endpoint = endpoint;
        this.controller = controller;
        this.methodName = methodName;
        this.description = description;
        this.status = status;
        this.parameters = parameters;
        this.requestBody = requestBody;
        this.returnType = returnType;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getController() {
        return controller;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<ApiParameter> getParameters() {
        return parameters;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getReturnType() {
        return returnType;
    }

    public boolean hasParameters() {
        return parameters != null
                && !parameters.isEmpty();
    }

    public boolean hasRequestBody() {
        return requestBody != null
                && !requestBody.isBlank();
    }

    @Override
    public String toString() {

        return String.format(
                "%s -> %s | Controller: %s | Method: %s | Description: %s | Status: %s",
                httpMethod,
                endpoint,
                controller,
                methodName,
                description,
                status
        );
    }

    // =========================================================
    // API PARAMETER
    // =========================================================

    public static class ApiParameter {

        private final String name;
        private final String type;
        private final String location;
        private final boolean required;

        public ApiParameter(
                String name,
                String type,
                String location,
                boolean required
        ) {

            this.name = name;
            this.type = type;
            this.location = location;
            this.required = required;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getLocation() {
            return location;
        }

        public boolean isRequired() {
            return required;
        }
    }
}