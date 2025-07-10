package com.atendeia.userservice.service.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/teste");
    }

    @Test
    void deveTratarBusinessException() {
        BusinessException ex = new BusinessException("Mensagem de erro");
        ProblemDetail detail = handler.handleBusiness(ex, request);

        assertThat(detail.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(detail.getTitle()).isEqualTo("Violação de regra de negócio");
        assertThat(detail.getDetail()).isEqualTo("Mensagem de erro");
        assertThat(detail.getInstance()).isEqualTo(URI.create("/api/teste"));
    }

    @Test
    void deveTratarEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Não encontrado");
        ProblemDetail detail = handler.handleNotFound(ex, request);

        assertThat(detail.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(detail.getTitle()).isEqualTo("Recurso não encontrado");
        assertThat(detail.getDetail()).isEqualTo("Não encontrado");
        assertThat(detail.getInstance()).isEqualTo(URI.create("/api/teste"));
    }

    @Test
    void deveTratarMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("usuario", "email", "Email inválido");
        FieldError fieldError2 = new FieldError("usuario", "senha", "Senha é obrigatória");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ProblemDetail detail = handler.handleValidation(ex, request);

        assertThat(detail.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(detail.getTitle()).isEqualTo("Erro de validação");
        assertThat(detail.getDetail()).contains("Email inválido");
        assertThat(detail.getDetail()).contains("Senha é obrigatória");
        assertThat(detail.getInstance()).isEqualTo(URI.create("/api/teste"));
    }

    @Test
    void deveTratarDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Violação de integridade");
        ProblemDetail detail = handler.handleConstraint(ex, request);

        assertThat(detail.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(detail.getTitle()).isEqualTo("Violação de integridade");
        assertThat(detail.getDetail()).contains("Verifique se e-mail ou login já existem");
        assertThat(detail.getInstance()).isEqualTo(URI.create("/api/teste"));
    }

    @Test
    void deveTratarErroGenerico() {
        Exception ex = new RuntimeException("Erro inesperado");
        ProblemDetail detail = handler.handleGeneric(ex, request);

        assertThat(detail.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(detail.getTitle()).isEqualTo("Erro interno inesperado");
        assertThat(detail.getDetail()).contains("Ocorreu um erro inesperado");
        assertThat(detail.getInstance()).isEqualTo(URI.create("/api/teste"));
    }
}
