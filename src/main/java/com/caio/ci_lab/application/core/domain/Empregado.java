package com.caio.ci_lab.application.core.domain;

import java.math.BigDecimal;

public class Empregado {

    private final String nome;
    private final String email;
    private final BigDecimal salario;

    public Empregado(String nome, String email, BigDecimal salario) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salário deve ser positivo");
        }
        this.nome = nome;
        this.email = email;
        this.salario = salario;
    }

    public boolean isSalarioAcimaDaMedia(BigDecimal media) {
        return salario.compareTo(media) > 0;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public BigDecimal getSalario() { return salario; }
}