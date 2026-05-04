package br.com.fiap.restaurant_management.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    private final String code;

    public ResourceAlreadyExistsException(String message) {
        super(message);
        this.code = "RESOURCE_ALREADY_EXISTS";
    }

    public String getCode() {
        return code;
    }
}
