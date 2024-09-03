<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.grepp.jpa.model.entity.RoomEntity" %>
<%--
  Created by IntelliJ IDEA.
  User: 관리자
  Date: 2024-08-20
  Time: 오전 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판 목록</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            color: #4CAF50;
            text-decoration: none;
            margin: 0 5px;
        }

        a:hover {
            text-decoration: underline;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .create-chat-link {
            display: block;
            margin: 20px 0;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <%@ include file="common/header.jsp"%>
    <%@ include file="common/alert.jsp"%>
    <div class="create-chat-link">
        <a href="<%=request.getContextPath()%>/main">메인으로 가기</a>
    <%
        if(session.getAttribute("loginNo") != null){
    %>
        <a href="<%=request.getContextPath()%>/chat/create">채팅방 만들기</a>

    <%
        }
    %>
    </div>
    <table>
        <thead>
        <tr>
            <th>번호</th>
            <th>채팅방 이름</th>
            <th>설명</th>
            <th>생성일</th>
        </tr>
        </thead>
        <tbody>
        <%
            Map<String, Object> pageData = (Map<String, Object>) request.getAttribute("pageData");
            List<RoomEntity> cList = (List<RoomEntity>) pageData.get("cList");
            for(RoomEntity c: cList){
        %>
        <tr>
            <td><%=c.getId()%></td>
            <td><a href="<%=request.getContextPath()%>/chatting/<%=c.getId()%>"><%=c.getName()%></a></td>
            <td><%=c.getDescription()%></td>
            <td><%=c.getCreatedAt()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="pagination">
        <%
            int nowPage = (int) pageData.get("page");
            int startPage = (int) pageData.get("startPage");
            int endPage = (int) pageData.get("endPage");
            int totalPage = (int) pageData.get("totalPageCount");
            if (1 < startPage) {
        %>
        <a href="<%=request.getContextPath()%>/chat/list?page=<%=startPage-1%>">이전</a>
        <%
            }
            for (int i = startPage; i <= endPage; i++) {
        %>
        <a href="<%=request.getContextPath()%>/chat/list?page=<%=i%>"><%=i%> </a>
        <%
            }
            if (endPage < totalPage) {
        %>
        <a href="<%=request.getContextPath()%>/chat/list?page=<%=endPage+1%>">다음</a>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
