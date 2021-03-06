package support.onehundredacrewood.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import support.onehundredacrewood.app.services.UserDetailsLoader;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/font/**", "/img/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().access("not(hasRole('ROLE_USER'))")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .and()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/posts/create",
                        "/profile",
                        "/profile/**",
                        "/posts/{postId}/update",
                        "/messaging",
                        "/messages",
                        "/messages/**",
                        "/posts/myposts",
                        "/posts/follow",
                        "/posts/comment",
                        "/posts/report",
                        "/posts/clear",
                        "/posts/lock-toggle",
                        "/posts/delete",
                        "/posts/comment/report",
                        "/posts/comment/clear",
                        "/posts/commemt/delete",
                        "/admin"
                )
                .authenticated()
        ;
    }
}
