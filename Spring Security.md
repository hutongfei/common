​					**Spring Security** 

1 httpBasic 登录模式

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * httpBasic模式
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()//开启httpbasic认证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();//所有请求都需要登录认证才能访问
    }
}
```

可配置用户名密码applications.properties

```java
spring.security.user.name=admin
spring.security.user.password=admin
```

2 formLogin登录模式

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //禁用跨站csrf攻击防御，后面的章节会专门讲解
                .formLogin()
                .loginPage("/login.html")//用户未登录时，访问任何资源都转跳到该路径，即登录页面
                .loginProcessingUrl("/login")//登录表单form中action的地址，也就是处理认证请求的路径
                .defaultSuccessUrl("/index")//登录认证成功后默认转跳的路径
//                .usernameParameter("username")///登录表单form中用户名输入框input的name名，不修改的话默认是username
//                .passwordParameter("password")//form中密码输入框input的name名，不修改的话默认是password
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "/login").permitAll()//不需要登录可访问
                .antMatchers("/biz1", "/biz2").hasAnyAuthority("ROLE_user", "ROLE_admin")  //前面是资源的访问路径、后面是资源的名称或者叫资源ID
                .antMatchers("/sysLog", "/sysUser").hasAnyAuthority("ROLE_admin")
                .anyRequest().authenticated();
    }

    /**
     * 配置用户权限
     *
     * @param auth
     * @throws Exception
     */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("user")
                .and()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("admin")
                .and()
                .passwordEncoder(passwordEncoder());//配置BCrypt加密
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```





