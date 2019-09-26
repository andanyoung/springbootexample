package cn.andyoung.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class UserController {
  /**
   * 全部基于 Spring Boot给 Thymeleaf的默认配置 所以下面会跳转到 classpath:/templates/home.html 页面
   *
   * @param paramMap
   * @return
   */
  @RequestMapping("home")
  public String goHome(Map<String, Object> paramMap) {
    /** 默认Map的内容会放大请求域中，页面可以直接使用Thymeleaf取值 */
    paramMap.put("name", "张三");
    paramMap.put("age", 35);
    return "home";
  }
}
