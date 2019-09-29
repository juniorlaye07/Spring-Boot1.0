package com.devweb.rh.config;

import com.devweb.rh.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity //gere la securité du projet
@EnableGlobalMethodSecurity(prePostEnabled = true) //
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired //injection d'une refernece objet
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsServiceImpl jwtUserDetailsService; //
    @Autowired
    private JwtRequestFilter jwtRequestFilter;//
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//configurez AuthenticationManager pour qu'il sache où charger
//utilisateur pour les informations d'identification correspondantes
//Utilisation BCryptPasswordEncoder

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //Nous n'avons pas besoin de CSRF pour cet exemple

        httpSecurity.csrf().disable()

        //ne pas authentifier cette demande particulière

        .authorizeRequests().antMatchers("/authenticate","/login").permitAll().

        //toutes les autres demandes doivent être authentifiées

        anyRequest().authenticated().and().

        //assurez-vous que nous utilisons une session sans état; session ne sera pas utilisé pour
        //enregistrer l'état de l'utilisateur.

        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Ajouter un filtre pour valider les jetons à chaque requête

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
