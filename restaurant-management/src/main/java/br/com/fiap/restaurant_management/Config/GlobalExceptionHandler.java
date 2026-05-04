package br.com.fiap.restaurant_management.Config;

import br.com.fiap.restaurant_management.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.exception.InvalidCredentialsException;
import br.com.fiap.restaurant_management.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BASE_URL = "https://api.restaurant-management.com/errors";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {

        log.warn("Recurso não encontrado: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setType(URI.create(BASE_URL + "/resource-not-found"));
        problemDetail.setTitle("Recurso não encontrado");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false).replace("uri=", ""));

        if (ex.getFieldName() != null) {
            problemDetail.setProperty("fieldName", ex.getFieldName());
            problemDetail.setProperty("fieldValue", ex.getFieldValue());
        }

        return problemDetail;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(InvalidCredentialsException ex, WebRequest request) {

        log.warn("Credenciais inválidas: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());

        problemDetail.setType(URI.create(BASE_URL + "/invalid-credentials"));
        problemDetail.setTitle("Credenciais inválidas");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false).replace("uri=", ""));

        return problemDetail;
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ProblemDetail handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {

        log.warn("Violação de regra de negócio [{}]: {}", ex.getCode(), ex.getMessage());

        // Email duplicado retorna 409, outras violações retornam 400
//        HttpStatus status = ex.getCode().contains("EMAIL") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        problemDetail.setType(URI.create(BASE_URL + "/business-rule-violation"));
        problemDetail.setTitle("Violação de regra de negócio");
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false).replace("uri=", ""));

        return problemDetail;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {

        log.warn("Registro duplicado: {}", ex.getMessage());


        HttpStatus status = HttpStatus.CONFLICT ;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        problemDetail.setType(URI.create(BASE_URL + "/resource-already-exists"));
        problemDetail.setTitle("Registro duplicado");
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false).replace("uri=", ""));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex, WebRequest request) {

        log.error("Erro interno não tratado", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Um erro inesperado ocorreu no processamento da sua solicitação");

        problemDetail.setType(URI.create(BASE_URL + "/internal-server-error"));
        problemDetail.setTitle("Erro interno do servidor");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false).replace("uri=", ""));
        problemDetail.setProperty("errorId", ex.getClass().getSimpleName());

        return problemDetail;
    }
}

