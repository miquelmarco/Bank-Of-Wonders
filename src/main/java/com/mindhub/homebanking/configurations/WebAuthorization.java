package com.mindhub.homebanking.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/clients", "/api/logout").permitAll()
                .antMatchers("/web/assets/**", "/web/index.html").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cards/payments").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers(HttpMethod.POST,"/api/pdfGenerator").permitAll()
                .antMatchers("/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/manager.html").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans/new").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transactions/generate-pdf").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/loans").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers("/api/accounts/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/clientLoans/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/accounts").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/**").hasAuthority("CLIENT")
                .antMatchers("/api/loans").hasAuthority("CLIENT")
                .antMatchers("/api/cards/renew").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clientLoan/payments").hasAuthority("CLIENT")
                .antMatchers("/web/pages/loanPayment.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/loan-application.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/myProfile.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/account.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/aboutUs.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/transfers.html").hasAuthority("CLIENT")
                .antMatchers("/web/pages/create-cards.html").hasAuthority("CLIENT")
                .anyRequest().denyAll();
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout()
                .logoutUrl("/api/logout").deleteCookies("JSESSIONID");
        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        http.cors();
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://127.0.0.1:5501") // Reemplaza la URL con la del front-end
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*");
//    }
}