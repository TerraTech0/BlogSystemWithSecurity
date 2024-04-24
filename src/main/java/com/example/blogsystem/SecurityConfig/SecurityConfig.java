package com.example.blogsystem.SecurityConfig;

import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //csrf << cross
        http.csrf().disable()
                .sessionManagement()//created when i login
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/register", "/api/v1/user/login/{username}/{password}", "/api/v1/user/logout").permitAll()
                .requestMatchers("/api/v1/user/get-user-by-id/{userId}","/api/v1/user/delete/{userId}","/api/v1/blog/get-all-blogs-by-username/{username}","/api/v1/blog/get-blog-by-title/{title}","/api/v1/blog/get-blog-by-id/{blogId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/update/{userId}", "/api/v1/user/add", "/api/v1/blog/delete/{blogId}","/api/v1/blog/update/{blogId}", "/api/v1/blog/add").hasAuthority("USER")
                .anyRequest().permitAll() //<< saving my system from any attack with this line!
                .and()
                .logout().logoutUrl("/api/v1/user/logout")//in logout the session will be invalid and delete cookies!
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return http.build();

    }

}
