package com.minibank.config;


import com.minibank.models.constants.Permission;
import com.minibank.models.constants.UserRole;
import com.minibank.security.JwtConfigure;
import com.minibank.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(2)
    public class ApiSecurityConfig extends WebSecurityConfigurerAdapter{
        private final JwtTokenProvider jwtTokenProvider;;

        public ApiSecurityConfig(JwtTokenProvider jwtTokenFilter) {
            this.jwtTokenProvider = jwtTokenFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/swagger-ui/**",
                            "/v2/**", "/v3/**", "/api/restLogin/**").permitAll()
                    .antMatchers("/api/restAdmin/**").hasAuthority("ADMIN")
                    .antMatchers("/api/restUser/**").hasAuthority("CLIENT")
                    .antMatchers("/openapi/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .apply(new JwtConfigure(jwtTokenProvider));
        }


        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    @Order(1)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        @Autowired
        public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/about", "/technology", "/contact", "/auth/login",
                            "/auth/registration", "/auth/success", "/account/create", "/account/ok").permitAll()
                    .antMatchers("/user/**").hasAnyAuthority("ADMIN", "CLIENT")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/account/myAccounts", "/trans/**").hasAuthority("CLIENT")
                    .antMatchers("/css/**", "/img/**", "/fonts/**", "/js/**").permitAll()
                    .anyRequest().hasAnyRole(UserRole.ADMIN.name(), UserRole.CLIENT.name())
                    .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/log")
                    .permitAll()
                    .defaultSuccessUrl("/user/home")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/auth/login")
                    .permitAll();
        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
    }
}