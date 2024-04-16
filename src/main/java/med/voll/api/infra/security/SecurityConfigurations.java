package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration// Falando para o spring que essa classe e de configuracao
@EnableWebSecurity//Falando para o spring que iremos personalizar as configuracoes de seguranca
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean//Serve para eu expor o retorno desse metodo para o spring ou um objeto que eu possar injetar em algum controller e service
    public SecurityFilterChain securityFilterChain(HttpSecurity  http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    //req.requestMatchers(HttpMethod.POST, "login").permitAll()
                    req.requestMatchers("login").permitAll();//Permitindo que essa rota não precise usar autenticação
                    req.anyRequest().authenticated();//Todas as outras rodas precisa de autenticação
                })
                //Falando para o spring que meu filtro de autenticação vem primeiro que o dele
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    //Fazendo a injecao de dependencia do AuthenticationManager para que ele possa ser usado na minha controller
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    //Fazendo a configuracao da criptografia que esta sendo utilizada para salvar as senhas no banco
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
