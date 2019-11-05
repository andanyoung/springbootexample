// package cn.andyoung.springsecurity.config;
//
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
//// @EnableWebSecurity // 表示开启 Spring Security 安全认证与授权
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  /**
//   * 定义用户认证规则
//   *
//   * @param auth
//   * @throws Exception
//   */
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    /**
//     *
// inMemoryAuthentication()：添加内存用户认证(InMemoryUserDetailsManagerConfigurer)，这些账号密码都会存储在内存中，而不是数据库，所以同理还有-
//     * jdbcAuthentication()：JdbcUserDetailsManagerConfigurer 数据库用户认证
// passwordEncoder(PasswordEncoder
//     * passwordEncoder)：密码编码，Spring Security 高版本必须进行密码编码，否则报错 UserDetailsBuilder withUser(String
//     * username)：添加用户 UserDetailsBuilder password(String password)：为用户添加密码，不能为 null
//     * UserDetailsBuilder roles(String... roles)：为用户添加角色 C and()：返回对象，方便链式编程，也可以分开写
//     */
//    auth.inMemoryAuthentication()
//        .passwordEncoder(new MyPasswordEncoder())
//        .withUser("ZhangSan")
//        .password("123456")
//        .roles("topLevel")
//        .and()
//        .withUser("guoJing")
//        .password("123456")
//        .roles("senior")
//        .and()
//        .withUser("yangGuo")
//        .password("123456")
//        .roles("middleRank", "primary")
//        .and()
//        .withUser("weiXiaoBao")
//        .password("123456")
//        .roles("primary");
//  }
//
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    /**
//     * authorizeRequests：表示验证请求 antMatchers(String... antPatterns)：使用
//     * org.springframework.security.web.util.matcher.AntPathRequestMatcher 的匹配规则
//     * permitAll()：允许一切用户访问，底层也是调用 access("permitAll") hasRole(String role)：url请求允许访问的角色
//     * hasAnyRole(String... roles) : url请求允许访问的多个角色 access(String
//     * attribute)：允许访问的角色，permitAll、hasRole、hasAnyRole 底层都是调用 access 方法 access("permitAll") 等价于
//     * permitAll()
//     */
//    http.authorizeRequests().antMatchers("/").permitAll(); // "/"：应用首页所以用户都可以访问
//    http.authorizeRequests()
//        .antMatchers("/user/addUser")
//        .hasRole("topLevel") // 首斜杠"/"表示应用上下文,/user/addUser 请求允许 topLevel 角色访问
//        .antMatchers("/user/deleteUser/**")
//        .hasAnyRole(
//            "topLevel", "senior") // "/user/deleteUser/**"允许 "topLevel", "senior" 角色访问，/**匹配任意
//        .antMatchers("/user/updateUser")
//        .hasAnyRole("topLevel", "senior", "middleRank") // 除了这种链式编程，也可以分开写
//        .antMatchers("/user/findAllUsers")
//        .access("permitAll");
//
//    http.authorizeRequests().antMatchers("/home.html").permitAll();
//    /**
//     * http.authorizeRequests().anyRequest().hasRole("senior")： 表示上面约定以外的所有请求，都需要有 senior 角色才可以访问
//     * http.authorizeRequests().anyRequest().authenticated()：
//     * 表示上面约定以外的所有请求，必须要经过认证才能访问，但是认证的可以是任意角色，即只要认证就行，与角色的权限无关
//     */
//    //        http.authorizeRequests().anyRequest().hasRole("senior");
//    http.authorizeRequests().anyRequest().authenticated();
//
//    /**
//     * formLogin：指定支持基于表单的身份验证 未使用 FormLoginConfigurer#loginPage(String)
//     * 指定登录页时，将自动生成一个登录页面，亲测此页面引用的是联网的 bootstarp 的样式，所以断网时，样式会有点怪 当用户没有登录、没有权限时就会自动跳转到登录页面(默认
//     * /login) 当登录失败时，默认跳转到 /login?error 登录成功时会放行
//     */
//    http.formLogin();
//    /**
//     * logout：开启自动配置注销功能 默认以 "/logout" 路劲表示用户注销，即页面请求 "/logout" 时，就会自动注销登录，同时自动清除 session
//     * 注销成功后，默认跳转到 "/login?logout" 登录页面
//     */
//    /**
//     * logout：开启自动配置注销功能 默认以 "/logout" 路劲表示用户注销，即页面请求 "/logout" 时，就会自动注销登录，同时自动清除 session
//     * 注销成功后，默认跳转到 "/login?logout" 登录页面 deleteCookies("remove")：注销后删除一个key叫 "remove" 的 cookie
//     * invalidateHttpSession(false)：注销后仍然保留 HttpSession（视实际需求而定，通常是要清除的）
//     * logoutSuccessUrl("/home.html")：注销后不再进入默认的 "/login?logout" 登录页面,而是进入 /home.html 页面
//     */
//    http.logout().invalidateHttpSession(true).logoutSuccessUrl("/home.html");
//  }
//
//  class MyPasswordEncoder implements PasswordEncoder {
//    @Override
//    public String encode(CharSequence charSequence) {
//      return charSequence.toString();
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String s) {
//      return s.equals(charSequence.toString());
//    }
//  }
// }
