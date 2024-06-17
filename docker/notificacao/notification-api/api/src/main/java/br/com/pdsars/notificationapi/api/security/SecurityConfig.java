package br.com.pdsars.notificationapi.api.security;

//
//@KeycloakConfiguration
//public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
//    private final KeycloakClientRequestFactory keycloakClientRequestFactory;
//
//    public SecurityConfig(KeycloakClientRequestFactory keycloakClientRequestFactory) {
//        this.keycloakClientRequestFactory = keycloakClientRequestFactory;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        // Rotas e suas configurações de acesso
//        // Padronizar rotas com hiffen. Exemplo list-disabled-users
//        http.cors()
//            .and()
//            .csrf()
//            .disable()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.GET, "/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/**").permitAll()
//            .antMatchers(HttpMethod.PUT, "/**").permitAll()
//            .antMatchers(HttpMethod.DELETE, "/**").permitAll()
//            .anyRequest()
//            .authenticated();
//    }
//
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) {
//        final var keycloakAuthenticationProvider
//                = keycloakAuthenticationProvider();
//
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
//                new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }
//
//    @Bean
//    public KeycloakConfigResolver KeycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new NullAuthenticatedSessionStrategy();
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public KeycloakRestTemplate keycloakRestTemplate() {
//        return new KeycloakRestTemplate(keycloakClientRequestFactory);
//    }
//}
//
