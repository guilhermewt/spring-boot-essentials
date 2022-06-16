package academy.devdojo.springboot2.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//todas urls vao ter que passar por uma autenticacao basica poderia ser formLogin
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //colocar esse apenas no final da aula o disable e se tiver em producao tirar este disable tambem
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and() //quando o csrf estiver ativador as aplicacaoes frontend nao poderao  os dados, para pegar vao ter que informar o cookie
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		log.info("password encoded {}", passwordEncoder.encode("test"));
		auth.inMemoryAuthentication()
		.withUser("guilherme")
		.password(passwordEncoder.encode("academy"))
		.roles("USER","ADMIN")
		.and()
		.withUser("devDojo")
		.password(passwordEncoder.encode("academy"))
		.roles("USER");
	}

}
