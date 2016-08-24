package com.bres.siodme.config;

/**
 * Created by Adam on 2016-08-02.
 */

import com.bres.siodme.web.security.MySimpleUrlAuthenticationSuccessHandler;
import com.bres.siodme.web.security.MyUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = com.bres.siodme.web.security.UserDetailsServiceImpl.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private UserDetailsService userDetailsService;

    @Autowired public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name="authenticationManager")
    @Override public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean(name="bCryptPasswordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder;
    }

    @Bean(name="mySimpleUrlAuthenticationSuccessHandler")
    public MySimpleUrlAuthenticationSuccessHandler successHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean(name = "myUsernamePasswordAuthenticationFilter")
    public MyUsernamePasswordAuthenticationFilter authFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter authFilter
                = new MyUsernamePasswordAuthenticationFilter();
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setAuthenticationSuccessHandler(successHandler());
        authFilter.setUsernameParameter("username");
        authFilter.setPasswordParameter("password");

        return authFilter;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/welcome").hasRole("USER")
                .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("username").passwordParameter("password")
                    .successHandler(successHandler())
                    .failureUrl("/login?error")
                .and().logout()
                    .logoutSuccessUrl("/login?logout")
                .and().csrf()
                .and().rememberMe()
                    .tokenValiditySeconds(86400) // user data stored in a cookie for 1 day
                    .key("loginKey")
                ;
    }

}
