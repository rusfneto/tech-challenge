package br.com.fiap.postech.techchallenge.handlers;

import br.com.fiap.postech.techchallenge.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.fiap.postech.techchallenge.exceptions.ResourceNotFoundException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateKey(DuplicateKeyException ex, HttpServletRequest request) {
        Throwable root = ex.getRootCause();
        String specific = (root != null && root.getMessage() != null) ? root.getMessage() : "";

        String detail = (specific.contains("uk_usuarios_email") || specific.contains("usuarios.uk_usuarios_email"))
                ? "O email indicado ja tem cadastro"
                : "Registro duplicado";

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setType(URI.create("urn:techchallenge:problem:duplicate-email"));
        pd.setTitle("Email already registered");
        pd.setDetail(detail);
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("urn:techchallenge:problem:validation-error"));
        pd.setTitle("Validation error");
        pd.setDetail("One or more fields are invalid");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        pd.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("urn:techchallenge:problem:invalid-json"));
        pd.setTitle("Invalid JSON");
        pd.setDetail("JSON invalido ou mal formatado");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create("urn:techchallenge:problem:internal-error"));
        pd.setTitle("Internal server error");
        pd.setDetail("Erro interno no servidor");
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("urn:techchallenge:problem:invalid-parameter"));
        pd.setTitle("Invalid parameter");
        pd.setDetail(ex.getMessage());
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("urn:techchallenge:problem:not-found"));
        pd.setTitle("Not Found");
        pd.setDetail(ex.getMessage());
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setType(URI.create("urn:techchallenge:problem:unauthorized"));
        pd.setTitle("Unauthorized");
        pd.setDetail(ex.getMessage()); // "Login ou senha invalidos"
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(pd);
    }

}
