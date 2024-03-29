package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//OBS:Caso tenha algum problema com a migrations ou erro na hora de escrever a query digite essa comando primeiro
//delete from flyway_schema_history where success = 0; antes de rodar uma nova migrations

//Para conseguir utilizar o JpaRepository e necessario passar a classe Principal e o tipo da chave primaria
//OBS:Antes era necessario criar uma classe DAO
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    //Colocando o metodo no padrao de assinatura do spring ele ira fazer uma busca nos ativos que estao igual a true
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
