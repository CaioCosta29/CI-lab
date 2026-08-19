package com.caio.ci_lab.application.adapters.in.controller;

import com.caio.ci_lab.application.core.domain.Empregado;
import com.caio.ci_lab.application.core.usecase.CadastrarEmpregadoUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/empregados")
public class EmpregadoController {

    private final CadastrarEmpregadoUseCase cadastrarEmpregadoUseCase;

    public EmpregadoController(CadastrarEmpregadoUseCase cadastrarEmpregadoUseCase) {
        this.cadastrarEmpregadoUseCase = cadastrarEmpregadoUseCase;
    }

    @PostMapping
    public ResponseEntity<EmpregadoResponse> cadastrar(@Valid @RequestBody EmpregadoRequest request) {
        Empregado empregado = cadastrarEmpregadoUseCase.cadastrar(
                new Empregado(request.nome(), request.email(), request.salario())
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new EmpregadoResponse(empregado.getNome(), empregado.getEmail(), empregado.getSalario()));
    }

    public record EmpregadoRequest(
            @NotBlank String nome,
            @Email @NotBlank String email,
            @Positive BigDecimal salario
    ) {}

    public record EmpregadoResponse(String nome, String email, BigDecimal salario) {}
}
