<%--
  Created by IntelliJ IDEA.
  User: 관리자
  Date: 2024-08-20
  Time: 오전 9:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="com.grepp.jpa.model.entity.RoomEntity" %>
<%@ page import="com.grepp.jpa.model.dto.ChatDTO" %>
<%@ page import="com.grepp.jpa.model.entity.ChatEntity" %>
<%@ page import="com.grepp.jpa.model.dto.FileDTO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 상세화면</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        .chat-history {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            margin-top: 20px;
            max-height: 400px;
            overflow-y: auto;
        }
        .chat-message {
            border-bottom: 1px solid #eee;
            padding: 10px;
        }
        .chat-message:last-child {
            border-bottom: none;
        }
        .chat-message .author {
            font-weight: bold;
            color: #4CAF50;
        }
        .chat-message .content {
            margin-top: 5px;
        }
        .chat-message .files {
            margin-top: 5px;
            font-size: 0.9em;
            color: #888;
        }
        .form-container {
            margin-top: 20px;
        }
        .form-container input[type="text"] {
            width: 80%;
            padding: 10px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .form-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container button {
            padding: 10px 20px;
            background-color: #e7e7e7;
            color: #333;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        .form-container button:hover {
            background-color: #ccc;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
            margin-right: 10px;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <%@ include file="common/header.jsp"%>
    <%@ include file="common/alert.jsp"%>

    <%
        RoomEntity roomDTO = (RoomEntity) request.getAttribute("chatRoom");
        List<ChatDTO> chatList = (List<ChatDTO>) request.getAttribute("chatList");
    %>

    <table>
        <tr>
            <td>채팅방 번호 :</td>
            <td><%=roomDTO.getId()%></td>
        </tr>
        <tr>
            <td>채팅방 이름 :</td>
            <td><%=roomDTO.getName()%></td>
        </tr>
        <tr>
            <td>채팅방 설명 :</td>
            <td><%=roomDTO.getDescription()%></td>
        </tr>
        <tr>
            <td>생성일시 :</td>
            <td><%=roomDTO.getCreatedAt()%></td>
        </tr>
    </table>

    <div class="chat-history" id="chatHistory">
        <h3>채팅 내역</h3>
        <%
            if (chatList != null && !chatList.isEmpty()) {
                for (ChatDTO c : chatList) {
        %>
        <div class="chat-message">
            <div class="author">작성자: <%=c.getUserId()%></div>
            <div class="content">내용: <%=c.getChatText()%></div>
            <div class="files">
                <%
                    if (c.getFileDTOList() != null && !c.getFileDTOList().isEmpty()) {
                        for (FileDTO fileDTO : c.getFileDTOList()) {
                %>
                <a href="<%=request.getContextPath()%>/chatting/download?fileNo=<%=fileDTO.getFileId()%>">첨부파일: <%=fileDTO.getOriginalName()%></a><br>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>

    <div class="form-container">
        <form action="<%=request.getContextPath()%>/chatting/<%=roomDTO.getId()%>" method="post" enctype="multipart/form-data">
            채팅하기 <input type="text" name="message"/>
            <input type="submit" value="전송"><br>
            <button id="btnAddFile">파일 추가</button>
            <div id="divFiles"></div>
        </form>
    </div>

    <a href="<%=request.getContextPath()%>/chat/delete?no=<%=roomDTO.getId()%>">채팅방 삭제하기</a>
    <a href="<%=request.getContextPath()%>/chat/update?no=<%=roomDTO.getId()%>">채팅방 수정하기</a><br>
    <a href="<%=request.getContextPath()%>/chat/list">게시판 목록으로</a>
    <a href="<%=request.getContextPath()%>/chat/inviteForm?no=<%=roomDTO.getId()%>">초대하기</a>
</div>

<script>
    // 채팅 내역을 페이지 로드 시 자동으로 가장 아래로 스크롤
    window.onload = function() {
        var chatHistory = document.getElementById('chatHistory');
        chatHistory.scrollTop = chatHistory.scrollHeight;
    };

    document.getElementById('btnAddFile').onclick = function (){
        let div = document.createElement('div');
        let input = document.createElement('input');
        let deleteBtn = document.createElement('button');
        input.setAttribute('type', 'file');
        input.setAttribute('name', 'uploadFile');
        deleteBtn.append('삭제');
        deleteBtn.onclick = function (){
            this.parentElement.remove();
            return false;
        }
        div.appendChild(input);
        div.appendChild(deleteBtn);
        document.getElementById('divFiles').appendChild(div);
        return false;
    }
</script>
</body>
</html>
