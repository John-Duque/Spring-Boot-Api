package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//E caso queira ver os comandos SQL disparados no banco de dados, vai precisar
//adicionar as seguintes propriedades no arquivo application.properties:
//spring.jpa.show-sql=true
//spring.jpa.properties.hibernate.format_sql=true

@RestController // Falando pra o spring que essa classe e uma controller
@RequestMapping("medicos") // Para passar a URL dessa controller
public class MedicoController {
    //Para fazer com que o spring instancie minha interface Repository
    //ele fara tambem a injeção de dependências
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    //como ele e um metodo de escrita e vou fazer um insert na base
    //OBS:metodos de leitura nao precisam da anotacao Transactional
    @Transactional //Preciso usar essa anotação para ter um transação ativa com o banco de dados
    //Estou utilizando o Valid para ele se integrar com o Bean Validation e executar em cascata
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);

        //Fazendo a criacao da URI para ser utilizada no retorno
        URI uri = uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }

    @GetMapping
    //Usar o Pageable do Spring para auxiliar na paginacao
    //usar a anotação PageableDefault caso o usuario não passe nada na URL temos alguns valores padrao
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        //OBS:http://localhost:8080/medicos?size=1&page=2&sort=nome,desc utilizando o size voce consegue listar a quantidade
        //que voce deseja. Utilizando o page voce consegue puxar a pagina da sua preferencia.
        //Utilizando o sort voce pode fazer uma ordenacao pelo nome ou qualquer outro atributo.
        //Colocando a virgula na frente do atributo voce pode dizer se e desc ou asc.
        //nao e necessario usar desse jeito de baixo porque o page ja contem o map nele e o toList
        //repository.findAll(paginacao).stream().map(DadosListagemMedico::new).toList();
        Page<DadosListagemMedico> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);//OBS:Entre no metodo findAllByAtivoTrue
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional//Como estamos usando uma anotacao Transacional o JPA ja entende que estamos fazendo uma atualizacao
    //desses dados sem a necessidade de usar o repository
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return  ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("{id}")
    @Transactional
    //Usar a anotacao PathVariable para que o spring entenda que o valor que vir da url e do id
    public ResponseEntity excluir(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        medico.excluir();

        //Retorno status 204
        return ResponseEntity.noContent().build();
    }


    @GetMapping("{id}")
    //Usar a anotacao PathVariable para que o spring entenda que o valor que vir da url e do id
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        //Retorno status 204
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
