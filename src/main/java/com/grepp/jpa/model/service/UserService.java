package com.grepp.jpa.model.service;


import com.grepp.jpa.model.dto.UserDTO;
import com.grepp.jpa.model.entity.UserEntity;
import com.grepp.jpa.model.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Map<String,Object> login(String username, String password, HttpSession session, String remLogin, String remId) throws SQLException {
        Optional<UserEntity> user = userRepository.findByUsernameAndPassword(username,password);

        Map<String,Object> map = new HashMap<>();

        if(user.isPresent()) {
            session.setAttribute("loginNo", user.get().getId());
            session.setAttribute("loginUsername", user.get().getUsername());
            List<Cookie> cookies = new ArrayList<>();

            if ("on".equals(remLogin)) {
                cookies.add(new Cookie("remLogin", user.get().getUsername()));
            }
            if ("on".equals(remId)) {
                cookies.add(new Cookie("remId", user.get().getUsername()));
            } else {
                Cookie cookie = new Cookie("remId", "");
                cookie.setMaxAge(0);
                cookies.add(cookie);
            }
            map.put("cookies", cookies);

            map.put("msg", "로그인 완료되었습니다. 반갑습니다. " + username + "님");
        } else {
            map.put("msg", "로그인 실패입니다. 아이디나 패스워드를 확인 해 주세요.");
        }
        return map;
    }

    public Map<String,String> checkLogin(HttpSession session){
        Long loginId = (Long) session.getAttribute("loginNo");
        Map<String,String> map = new HashMap<>();
        if(loginId == null) {
            map.put("view", "login_form");
        } else{ // 너 이미 로그인 되어있어!
            map.put("msg", "이미 로그인 내역이 있습니다.");
            map.put("view", "main");
        }
        return map;
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public int joinUser(UserEntity user) {
        UserEntity newUser = userRepository.save(user);
        if(newUser.getId()==null){
            return 0;
        }
        return 1;
    }
}
