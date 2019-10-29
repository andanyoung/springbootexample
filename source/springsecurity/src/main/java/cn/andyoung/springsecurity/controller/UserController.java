package cn.andyoung.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
  @GetMapping("addUser")
  public String addUser() {
    return "添加用户成功：" + System.currentTimeMillis();
  }

  @GetMapping("deleteUser/{uid}")
  public String deleteUser(@PathVariable(name = "uid") Integer uid) {
    return "删除用户成功：uid=" + uid;
  }

  @GetMapping("updateUser")
  public String updateUser() {
    return "修改用户成功：" + System.currentTimeMillis();
  }

  @GetMapping("findAllUsers")
  public String findAllUsers() {
    return "查询用户成功：" + System.currentTimeMillis();
  }
}
