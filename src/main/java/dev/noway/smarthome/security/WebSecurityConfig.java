package dev.noway.smarthome.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.util.LinkedHashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String BASIC_AUTH_REALM_NAME = "smart_home";
    private static final String LOGIN_FORM_PATH = "/login";
    private static final String REST_PATH_PREFIX = "/rest/**";

    @Autowired
    private UserDetailsService userDS;
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDS);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDS);
        auth.authenticationProvider(authenticationProvider());
        auth.inMemoryAuthentication()
                .withUser("email")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/register", "/user/register", "/static/**").permitAll()
                .antMatchers("/", "/login", "/register", "/user/register", "/webjars/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .successHandler(successHandler)
                .and()
                .logout()
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(delegatingAuthenticationEntryPoint());

    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDS;
    }

    @Bean
    public BasicAuthenticationFilter basicAuthenticationFilter()
            throws Exception {
        return new BasicAuthenticationFilter(authenticationManager(),
                delegatingAuthenticationEntryPoint());
    }

    @Bean
    public AuthenticationEntryPoint delegatingAuthenticationEntryPoint() {
        DelegatingAuthenticationEntryPoint
                delegatingAuthenticationEntryPoint =
                new DelegatingAuthenticationEntryPoint(entryPoints());
        delegatingAuthenticationEntryPoint.setDefaultEntryPoint(
                loginUrlAuthenticationEntryPoint());
        return delegatingAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationEntryPoint basicAuthenticationEntryPoint() {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint =
                new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName(BASIC_AUTH_REALM_NAME);
        return basicAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(LOGIN_FORM_PATH);
    }

    @Bean
    public RequestMatcher basicAuthenticationRequestMatcher() {

        return new AntPathRequestMatcher(REST_PATH_PREFIX);
    }

    @Bean
    public LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints() {
        LinkedHashMap<RequestMatcher,
                AuthenticationEntryPoint> entryPoints =
                new LinkedHashMap<>();
        entryPoints.put(basicAuthenticationRequestMatcher(),
                basicAuthenticationEntryPoint());
        return entryPoints;
    }
}
