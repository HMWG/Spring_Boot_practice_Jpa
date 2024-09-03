package com.grepp.jpa.controller;


import com.grepp.jpa.model.dto.RoomDTO;
import com.grepp.jpa.model.entity.RoomEntity;
import com.grepp.jpa.model.entity.RoomUserEntity;
import com.grepp.jpa.model.service.ChatRoomService;
import com.grepp.jpa.model.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1")int page, HttpSession session) throws SQLException {
        ModelAndView mav = new ModelAndView("list");

        mav.addObject("pageData", chatRoomService.getList(page, (Long) session.getAttribute("loginNo")));
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mav = new ModelAndView("create_form");
        mav.addObject("action", "create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView create(RoomDTO roomDTO, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        Long loginNo = (Long) session.getAttribute("loginNo");
        RoomEntity roomEntity = roomDTO.toEntity();
        System.out.println(roomDTO);
        System.out.println(roomEntity);

        if (chatRoomService.createChatRoom(roomEntity, loginNo) == 0){
            ModelAndView mav = new ModelAndView("redirect:list"); // /WEB-INF/views/list.jsp
            redirectAttributes.addFlashAttribute("msg", "write fail -> 이름을 작성해주세요");
            return mav;
        }
        ModelAndView mav = new ModelAndView("redirect:list"); // /WEB-INF/views/list.jsp
        redirectAttributes.addFlashAttribute("msg", "write success");
        return mav;
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("no")Long chatRoomNo, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        Long loginNo = (Long) session.getAttribute("loginNo");
        ModelAndView mav = new ModelAndView("redirect:list");
        redirectAttributes.addFlashAttribute("msg", "채팅방 삭제 완료");
        if(0==chatRoomService.deleteChatRoom(chatRoomNo, loginNo)){
            mav.setViewName("redirect:/chatting/"+chatRoomNo);
            redirectAttributes.addFlashAttribute("msg", "delete fail -> 권한이 없습니다.");
        }
        return mav;
    }

    @GetMapping("/update")
    public ModelAndView update(@RequestParam("no")Long chatRoomNo, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        Long loginNo = (Long) session.getAttribute("loginNo");
        ModelAndView mav = new ModelAndView("create_form");
        if(!chatRoomService.checkCreatedBy(chatRoomNo, loginNo)){
            redirectAttributes.addFlashAttribute("msg", "권한이 없습니다.");
            mav.setViewName("redirect:/chatting/"+chatRoomNo);
            return mav;
        }
        mav.addObject("action", "update");
        mav.addObject("no",chatRoomNo);
        return mav;
    }

    @PostMapping("/update")
    public ModelAndView update(RoomDTO roomDTO, HttpSession session, @RequestParam("no")Long chatRoomNo, RedirectAttributes redirectAttributes) throws SQLException {
        ModelAndView mav = new ModelAndView("redirect:list");
        Long loginNo = (Long) session.getAttribute("loginNo");

        roomDTO.setRoomId(chatRoomNo);
        if (chatRoomService.updateChatRoom(roomDTO.toEntity(), loginNo) == 0){
            redirectAttributes.addFlashAttribute("msg", "update fail -> 권한이 없습니다.");
        }
        return mav;
    }

    @GetMapping("/inviteForm")
    public ModelAndView inviteForm(@RequestParam("no")Long chatRoomNo) throws SQLException {
        ModelAndView mav = new ModelAndView("invite_form");
        mav.addObject("userList", userService.getUsers());
        mav.addObject("no",chatRoomNo);
        return mav;
    }

    @GetMapping("/invite")
    public ModelAndView invite(@RequestParam("chatRoomNo")Long chatRoomNo, @RequestParam("userNo")Long userNo, RedirectAttributes redirectAttributes) throws SQLException {
        ModelAndView mav = new ModelAndView("redirect:/chatting/"+chatRoomNo);
        redirectAttributes.addFlashAttribute("msg", "초대 완료");
        RoomUserEntity roomUser = chatRoomService.inviteChatRoom(chatRoomNo, userNo);
        if(roomUser==null || roomUser.getId() == null){
            redirectAttributes.addFlashAttribute("msg", "초대 실패");
        }
        return mav;
    }
}
