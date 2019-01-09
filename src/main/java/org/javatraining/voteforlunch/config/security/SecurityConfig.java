package org.javatraining.voteforlunch.config.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Configuration
//@EnableWebSecurity
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    /*@Autowired
    @Qualifier(value = "userServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }*/

    /* //пробовал и так
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER");

    }*/
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        *//*http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/register/**", "/resource/**", "/public").permitAll()
                .antMatchers("/vote/**").hasRole("USER")
                .antMatchers("/admin/**", "/service/*").hasAuthority("ADMIN")
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*//*
    }*/

    /*@Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }*/
}


