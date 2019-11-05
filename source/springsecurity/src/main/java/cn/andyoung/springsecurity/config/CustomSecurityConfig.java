package cn.andyoung.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private MyAuthenticationSucessHandler authenticationSucessHandler;
  @Autowired private MyAuthenticationFailureHandler authenticationFailureHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin() // 表单方式
        // .loginPage("/login.html")
        .loginPage("/authentication/require") // 登录跳转 URL
        .loginProcessingUrl("/login")
        .successHandler(authenticationSucessHandler)
        .failureHandler(authenticationFailureHandler) // 处理登录失败
        .and()
        .authorizeRequests() // 授权配置
        .antMatchers("/authentication/require", "/login.html", "/code/image")
        .permitAll()
        .anyRequest() // 所有请求
        .authenticated() // 都需要认证
        .and()
        .csrf()
        .disable();
  }
}
