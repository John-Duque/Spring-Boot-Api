package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(
        //Utilizando anotaçoes do bean validation
        @NotBlank // Verifica se não esta vazio e null
        //NotBlank e somente para campos String
        String nome,
        @NotBlank
        @Email
        String email,
        @NotNull
        String telefone,
        @NotNull
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid//Essa anotação serve para ele validar o que esta dentro do meu DTO DadosEndereco que tera anotaçoes
        //do Bean validation
        DadosEndereco endereco) {
}
