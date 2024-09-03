package com.grepp.jpa.controller;


import com.grepp.jpa.model.dto.ChatDTO;
import com.grepp.jpa.model.dto.FileDTO;
import com.grepp.jpa.model.entity.ChatEntity;
import com.grepp.jpa.model.entity.FileEntity;
import com.grepp.jpa.model.service.ChatService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;


    @GetMapping("/{chatRoomNo}")
    public ModelAndView chatRoom(@PathVariable("chatRoomNo") Long chatRoomNo) throws SQLException {
        ModelAndView mav = new ModelAndView("view");
        mav.addObject("chatRoom", chatService.viewChatRoom(chatRoomNo));
        mav.addObject("chatList", chatService.chatList(chatRoomNo));

        return mav;
    }

    @PostMapping("/{roomId}")
    public String chatRoomPost(@PathVariable("roomId") Long roomId, @RequestParam("message") String message, HttpSession session, @RequestParam(value = "uploadFile", required = false) MultipartFile[] uploadFile) throws SQLException {
        Long userNo = (Long) session.getAttribute("loginNo");
        ChatEntity chat = ChatEntity.builder()
                .roomUser(chatService.findRoomUser(roomId,userNo))
                .chatText(message)
                .build();

        ChatEntity newChat = chatService.chatting(chat);

        try {
            List<FileEntity> saveFiles = saveFiles(uploadFile);
            chatService.saveFileInfos(saveFiles, newChat.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/chatting/"+roomId;
    }

    @GetMapping("/download")
    public void download(@RequestParam("fileNo") Long fileNo, HttpServletResponse resp) throws IOException {
        FileEntity fileEntity = chatService.getFileInfo(fileNo);

        String fileName = new String(fileEntity.getOriginalName().getBytes("UTF-8"),"ISO-8859-1");

        // response는 기본적으로 html 응답하는 헤더가 설정되어 있음
        // 하지만, 지금은 html을 응답하는게 아니라 파일 그 자체를 전송할거임. 응답 객체의 헤더 정보를 파일 전송 버전으로 변경하자
        resp.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        resp.setHeader("Content-Transfer-Encoding", "binary");

        FileInputStream fis = new FileInputStream(fileEntity.getSavedPath());
        OutputStream os = resp.getOutputStream(); // response로 응답하는 스트림 ( 문자열 단위 아니고 바이트 단위로 보내게 됨)
        FileCopyUtils.copy(fis, os);
    }

    private List<FileEntity> saveFiles(MultipartFile[] uploadFile) throws IOException {
        List<FileEntity> files = new ArrayList<>();
        if(uploadFile != null && uploadFile.length > 0){ // 확실히 있는 경우 저장 절차 진행
            String uploadPath = "C:\\Users\\minwo\\Desktop\\workspace\\uploadFiles";
            if(!new File(uploadPath).exists()){
                new File(uploadPath).mkdirs();
            }

            for(MultipartFile file : uploadFile){
                String saveName = file.getOriginalFilename();
                if(saveName == null || saveName.isEmpty()){
                    continue;
                }
                File savedFile = new File(uploadPath, saveName);

                file.transferTo(savedFile); // 클라이언트가 업로드한 파일을 서버 컴퓨터 폴더에 비어있는 파일에 저장시키는 메소드 **핵심**
                FileEntity fileEntity = FileEntity.builder()
                        .originalName(file.getOriginalFilename())
                        .savedPath(savedFile.getAbsolutePath())
                        .build();
                files.add(fileEntity);
            }
        }
        return files;
    }
}
