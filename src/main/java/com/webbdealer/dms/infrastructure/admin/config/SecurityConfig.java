package com.webbdealer.dms.infrastructure.admin.config;

import com.webbdealer.dms.infrastructure.admin.persistence.services.DmsUserDetailsService;
import com.webbdealer.dms.infrastructure.admin.persistence.repositories.DmsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private DmsUserRepository dmsUserRepository;

    @Autowired
    public SecurityConfig(DmsUserRepository dmsUserRepository) {
        this.dmsUserRepository = dmsUserRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        System.out.println("Running Http Security");
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/", "/api/user", "/api/users", "/api/users/{id}").permitAll()
            //.antMatchers("/api/admin/**").hasRole("ADMIN")
                //.antMatchers("/api/admin/user").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        System.out.println("Running authenticationProvider");
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(new DirectoryUserDetailsService(userRepository));
//        return provider;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        System.out.println("Running AuthenticationManagerBuilder (User Details Service)");
        authenticationManagerBuilder.userDetailsService(
                new DmsUserDetailsService(dmsUserRepository))
        .passwordEncoder(passwordEncoder());
        // This is commented because if a customer Authentication Provider is used, then the User Details Service is not called
        //authenticationManagerBuilder.authenticationProvider(new BasicHttpAuthenticationProvider(userRepository));
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception
//    {
//        auth.inMemoryAuthentication()
//                .withUser("tony")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER");
//    }
}