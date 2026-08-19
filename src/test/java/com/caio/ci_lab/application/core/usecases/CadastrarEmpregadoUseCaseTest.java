package com.caio.ci_lab.application.core.usecases;

import com.caio.ci_lab.application.core.domain.Empregado;
import com.caio.ci_lab.application.ports.out.SalvarEmpregadoOutputPort;
import com.caio.ci_lab.application.core.usecase.CadastrarEmpregadoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarEmpregadoUseCaseTest {

    @Mock
    private SalvarEmpregadoOutputPort salvarEmpregadoPort;

    @InjectMocks
    private CadastrarEmpregadoUseCase useCase;

    @Test
    @DisplayName("Deve cadastrar empregado quando e-mail não existe")
    void deveCadastrarQuandoEmailNaoExiste() {
        Empregado empregado = new Empregado("Caio", "caio@teste.com", new BigDecimal("5000"));
        when(salvarEmpregadoPort.existePorEmail("caio@teste.com")).thenReturn(false);
        when(salvarEmpregadoPort.salvar(any(Empregado.class))).thenReturn(empregado);

        Empregado resultado = useCase.cadastrar(empregado);

        assertThat(resultado.getNome()).isEqualTo("Caio");
        verify(salvarEmpregadoPort).salvar(empregado);
    }

    @Test
    @DisplayName("Deve lançar exceção quando e-mail já cadastrado")
    void deveFalharQuandoEmailJaExiste() {
        Empregado empregado = new Empregado("Caio", "caio@teste.com", new BigDecimal("5000"));
        when(salvarEmpregadoPort.existePorEmail("caio@teste.com")).thenReturn(true);

        assertThatThrownBy(() -> useCase.cadastrar(empregado))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Já existe empregado");

        verify(salvarEmpregadoPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve rejeitar nome em branco")
    void deveRejeitarNomeEmBranco() {
        assertThatThrownBy(() -> new Empregado("  ", "a@b.com", new BigDecimal("100")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nome é obrigatório");
    }

    @Test
    @DisplayName("Deve rejeitar salário zero ou negativo")
    void deveRejeitarSalarioInvalido() {
        assertThatThrownBy(() -> new Empregado("Caio", "a@b.com", BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Salário deve ser positivo");
    }

    @Test
    @DisplayName("Deve identificar salário acima da média")
    void deveIdentificarSalarioAcimaDaMedia() {
        Empregado empregado = new Empregado("Caio", "a@b.com", new BigDecimal("5000"));

        assertThat(empregado.isSalarioAcimaDaMedia(new BigDecimal("3000"))).isTrue();
        assertThat(empregado.isSalarioAcimaDaMedia(new BigDecimal("7000"))).isFalse();
    }


}