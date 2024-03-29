package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

/*você também deve adicionar a seguinte propriedade no arquivo application.properties, para evitar que a stacktrace
da exception seja devolvida no corpo da resposta: server.error.include-stacktrace=never*/

public record DadosCadastroMedico(
        //Utilizando anotaçoes do bean validation
        @NotBlank(message = "Nome é obrigatório")// Verifica se não esta vazio e null
        //NotBlank e somente para campos String
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato do email é inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido")
        String crm,

        @NotNull(message = "Especialidade é obrigatória")
        Especialidade especialidade,

        @NotNull(message = "Dados do endereço são obrigatórios")
        @Valid//Essa anotação serve para ele validar o que esta dentro do meu DTO DadosEndereco que tera anotaçoes do Bean validation
        DadosEndereco endereco) {}
