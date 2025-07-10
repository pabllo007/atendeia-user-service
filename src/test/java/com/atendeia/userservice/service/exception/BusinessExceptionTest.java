package com.atendeia.userservice.service.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessExceptionTest {

    @Test
    void deveCriarBusinessException_comMensagem() {
        String mensagem = "Erro de negócio";
        BusinessException exception = new BusinessException(mensagem);

        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo(mensagem);
    }

    @Test
    void deveLancarBusinessException() {
        String mensagem = "Operação inválida";

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> { throw new BusinessException(mensagem); }
        );

        assertThat(exception.getMessage()).isEqualTo(mensagem);
    }
}
