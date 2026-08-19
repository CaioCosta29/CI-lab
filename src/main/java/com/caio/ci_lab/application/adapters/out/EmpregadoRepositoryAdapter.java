package com.caio.ci_lab.application.adapters.out;

import com.caio.ci_lab.application.core.domain.Empregado;
import com.caio.ci_lab.application.ports.out.SalvarEmpregadoOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmpregadoRepositoryAdapter implements SalvarEmpregadoOutputPort {

    private final Map<String, Empregado> banco = new HashMap<>();

    @Override
    public Empregado salvar(Empregado empregado) {
        banco.put(empregado.getEmail(), empregado);
        return empregado;
    }

    @Override
    public boolean existePorEmail(String email) {
        return banco.containsKey(email);
    }
}
