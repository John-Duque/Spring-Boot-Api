package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
//Para que o spring security use essa classe nossa precisamos usar essa interface UserDetails e implementar seus metodos
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //OBS:Caso no seu projeto tenha um controle de permisao detalhe melhor senao use como esta abaixo
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        //OBS: se for fazer algo detalhado nao retorne tudo true
        return true; // Por padrao ele vem com retorno false
    }

    @Override
    public boolean isAccountNonLocked() {
        //OBS: se for fazer algo detalhado nao retorne tudo true
        return true; // Por padrao ele vem com retorno false
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //OBS: se for fazer algo detalhado nao retorne tudo true
        return true; // Por padrao ele vem com retorno false
    }

    @Override
    public boolean isEnabled() {
        //OBS: se for fazer algo detalhado nao retorne tudo true
        return true; // Por padrao ele vem com retorno false
    }
}
