package com.caio.ci_lab.application.ports.out;

import com.caio.ci_lab.application.core.domain.Empregado;

public interface SalvarEmpregadoOutputPort {
    Empregado salvar(Empregado empregado);
    boolean existePorEmail(String email);
}
