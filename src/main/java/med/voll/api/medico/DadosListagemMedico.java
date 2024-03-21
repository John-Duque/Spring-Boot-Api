package med.voll.api.medico;

import med.voll.api.paciente.Paciente;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade,
                                  Boolean ativo) {
    public DadosListagemMedico(Medico medico) {
        //chamando o construtor principal
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getAtivo());
    }
}
