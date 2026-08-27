package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.Admin;
import com.huade.mapper.AdminMapper;
import com.huade.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminMapper adminMapper;

    /** 登录：POST /api/auth/login，body 传 {username, password} */
    @PostMapping("/login")
    public Result<Void> login(@RequestBody Map<String, String> body, HttpSession session) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        Admin admin = adminMapper.findByUsername(username.trim());
        if (admin == null || !PasswordUtil.verify(password, admin.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        session.setAttribute("admin", admin.getUsername());
        return Result.success("登录成功", null);
    }

    /** 退出登录 */
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success("已退出登录", null);
    }

    /** 当前登录状态（未登录时由拦截器返回 401） */
    @GetMapping("/current")
    public Result<String> current(HttpSession session) {
        String username = (String) session.getAttribute("admin");
        if (username == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(username);
    }
}
