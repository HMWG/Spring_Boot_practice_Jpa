package com.grepp.jpa.controller;


import com.grepp.jpa.model.dto.UserDTO;
import com.grepp.jpa.model.entity.UserEntity;
import com.grepp.jpa.model.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView loginForm(HttpSession session, HttpServletRequest request) {

        Map<String, String> map = userService.checkLogin(session);
        ModelAndView mav = new ModelAndView(map.get("view"));
        mav.addObject("msg", map.get("msg"));
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView loginForm(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpSession session, HttpServletResponse resp, HttpServletRequest req, @RequestParam(value = "remLogin", required = false) String remLogin, @RequestParam(value = "remId", required = false) String remId) throws SQLException {

        Map<String, Object> map = userService.login(username, password, session, remLogin, remId);
        ModelAndView mav = new ModelAndView("main");
        mav.addObject("msg", map.get("msg"));
        List<Cookie> cookies = (List<Cookie>) map.get("cookies");
        if (cookies!=null) {
            for(Cookie cookie : cookies) {
                resp.addCookie(cookie);
            }
        }
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("main");

        session.invalidate();
        Cookie cookie = new Cookie("remLogin", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        mav.addObject("msg", "logout");
        return mav;
    }

    @GetMapping("/join")
    public ModelAndView join() {
        ModelAndView mav = new ModelAndView("join_form");
        return mav;
    }

    @PostMapping("/join")
    public ModelAndView joinUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView mav = new ModelAndView("main");
        mav.addObject("msg", "회원가입 완료");
        UserEntity user = UserEntity.builder()
                .username(username)
                .password(password).build();

        if(userService.joinUser(user) == 0){
            mav.addObject("msg", "회원가입 실패 -> 아이디가 중복됐거나 회원가입할 수 없습니다");
        }
        return mav;
    }

}
