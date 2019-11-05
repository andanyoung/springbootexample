package cn.andyoung.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 自定义登录成功逻辑 */
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {
  private RequestCache requestCache = new HttpSessionRequestCache();
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  @Autowired private ObjectMapper mapper;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication)
      throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(mapper.writeValueAsString(authentication));
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    //    response.setContentType("application/json;charset=utf-8");
    //    response.getWriter().write(mapper.writeValueAsString(authentication));

    SavedRequest savedRequest = requestCache.getRequest(request, response);
    redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
    // 。如果想指定跳转的页面，比如跳转到/index
    // redirectStrategy.sendRedirect(request, response, "/index");
  }
}
