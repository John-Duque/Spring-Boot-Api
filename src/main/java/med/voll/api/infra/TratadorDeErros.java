package med.voll.api.infra;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice//anotacao para que o spring carregue essa classe como tratadora de erro
public class TratadorDeErros {
    //Usando essa anotacao para falar pro spring quando esse metodo deve ser chamado
    @ExceptionHandler(EntityNotFoundException.class)//Dentro do ExceptionHandler passar a exception espefica do erro
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    //Quando for erro de argumento tanto o spring quanto o Bean validation lança MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        //getFieldErrors devolve uma lista de erro que aconteceu em cada campo
        List<FieldError> errors = exception.getFieldErrors();
        //nao e necessario usar o build() no final quando se usa o body() que ja devolve o object ResponseEntity
        return ResponseEntity.badRequest().body(errors.stream().map(DadosErroValidacao::new).toList());
    }

    //fazendo a criacao de um dto para retonar os erros personalizados
    private record DadosErroValidacao(String campo, String mensagem){
        //criando uma subcarga do construtor que recebera o FieldError
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }

    }

}
