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
    <title>글 작성화면</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: 0 auto;
        }

        .form-container input[type="text"],
        .form-container textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .form-container input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-container label {
            font-weight: bold;
        }
    </style>
</head>
<body>
<%@ include file="common/header.jsp"%>
<br>
<%
    String action = (String) request.getAttribute("action");
    Long no = (Long) request.getAttribute("no");
%>
<div class="form-container">
    <form action="<%=request.getContextPath()%>/chat/<%=action%><% if(action.equals("update")) { %>?no=<%=no%><% } %>" method="post" enctype="multipart/form-data">
        <label for="name">채팅방 이름 :</label>
        <input type="text" name="name" id="name" required><br>

        <label for="description">설명 :</label>
        <textarea name="description" id="description" rows="5" required></textarea><br>

        <input type="submit" value="작성완료">
    </form>
</div>
</body>
</html>
