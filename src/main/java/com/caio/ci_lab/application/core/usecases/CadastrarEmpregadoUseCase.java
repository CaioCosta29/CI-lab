package com.caio.ci_lab.application.core.usecase;

import com.caio.ci_lab.application.core.domain.Empregado;
import com.caio.ci_lab.application.ports.out.SalvarEmpregadoOutputPort;
import com.caio.ci_lab.application.ports.out.SalvarEmpregadoOutputPort;
import org.springframework.stereotype.Service;

@Service
public class CadastrarEmpregadoUseCase {

    private final SalvarEmpregadoOutputPort salvarEmpregadoPort;

    public CadastrarEmpregadoUseCase(SalvarEmpregadoOutputPort salvarEmpregadoPort) {
        this.salvarEmpregadoPort = salvarEmpregadoPort;
    }

    public Empregado cadastrar(Empregado empregado) {
        if (salvarEmpregadoPort.existePorEmail(empregado.getEmail())) {
            throw new IllegalStateException("Já existe empregado com esse e-mail");
        }
        return salvarEmpregadoPort.salvar(empregado);
    }
}