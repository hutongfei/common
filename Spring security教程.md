​		**Spring security**



```java
/**
 * 安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MvcConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("passwordEncoder().encode(\"123456\")    "+passwordEncoder().encode("123456"));
//        //$2a$10$9Y1A.5Cx1mu36xh0XbuGbuEJeH.mrU4Fv6CQ.bsyMQ80Bg5NTAwzK
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER");
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    /**
     * 配置拦截器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    @Bean
    public  UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * 配置认证器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService());
    }
}
```

1，安全配置

​	继承至 WebSecurityConfigurerAdapter 

```java
/**
 * 配置认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class Auth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
//    /**
//     * 基于内存版本
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        System.out.println("passwordEncoder.encode(\"secret\")***************"+passwordEncoder.encode("secret"));
//        clients.inMemory()      //内存设置
//                .withClient("client")
//                .secret(passwordEncoder.encode("secret"))
//                .authorizedGrantTypes("authorization_code")
//                .scopes("app")
//                .redirectUris("http://www.baidu.com");
//    }
    /**
     * 基于jdbc 版本
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public TokenStore tokenStore() {
        // 令牌存到数据库中
        return new JdbcTokenStore(dataSource());
    }

    @Bean
    public ClientDetailsService clientDetails() {
        // 基于jdbc 配置数据库中客户端
        return new JdbcClientDetailsService(dataSource());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端
        clients.withClientDetails(clientDetails());
    }
}

```

![image-20200912224433105](D:\整理\image-20200912224433105.png)



![image-20200912224554389](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200912224554389.png)

