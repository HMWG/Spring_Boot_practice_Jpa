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
    <title>마이톡</title>
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

        h2 {
            color: #4CAF50;
            text-align: center;
        }

        .nav-links {
            text-align: center;
            margin-top: 20px;
        }

        .nav-links a {
            color: #4CAF50;
            text-decoration: none;
            margin: 0 10px;
            padding: 10px 20px;
            border: 1px solid #4CAF50;
            border-radius: 5px;
            background-color: #f9f9f9;
            transition: background-color 0.3s, color 0.3s;
        }

        .nav-links a:hover {
            background-color: #4CAF50;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="container">
    <%@ include file="common/header.jsp"%>
    <%@ include file="common/alert.jsp"%>
    <h2>마이톡 시스템</h2>
    <div class="nav-links">
        <a href="<%=request.getContextPath()%>/chat/create">채팅방 만들기</a>
        <a href="<%=request.getContextPath()%>/chat/list">채팅방 목록으로</a>
    </div>
</div>
</body>
</html>
