package rs.edu.raf.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import rs.edu.raf.userservice.service.CompanyService;
import rs.edu.raf.userservice.service.CustomDetailsService;
import rs.edu.raf.userservice.service.EmployeeService;
import rs.edu.raf.userservice.service.UserService;
import rs.edu.raf.userservice.util.jwt.JwtFilter;

@CrossOrigin("*")
@EnableWebSecurity
@EnableAsync
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final JwtFilter jwtFilter;

    public SpringSecurityConfig(UserService userService, EmployeeService employeeService, CompanyService companyService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomDetailsService customDetailsService = new CustomDetailsService(userService, employeeService, companyService);
        auth.userDetailsService(customDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/auth/**").permitAll()
                .antMatchers("/api/v1/user/setPassword/**").permitAll()
                .antMatchers("/api/v1/user/isUserActive/**").permitAll()
                .antMatchers("/api/v1/user/findEmailById/**").permitAll()
                .antMatchers("/api/v1/employee/auth/**").permitAll()
                .antMatchers("/api/v1/employee/setPassword/**").permitAll()
                .antMatchers("/api/v1/contact/**").permitAll()
                .antMatchers("/api/v1/company/auth/**").permitAll()
                .antMatchers("/api/v1/company/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers()
                .frameOptions().disable();

        httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
}
