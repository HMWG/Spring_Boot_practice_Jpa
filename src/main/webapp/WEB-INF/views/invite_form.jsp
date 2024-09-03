<%@ page import="java.util.List" %>
<%@ page import="com.grepp.jpa.model.entity.UserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>초대 화면</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
        }
        table {
            width: 50%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .header {
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@ include file="common/header.jsp" %>

<div class="header">
    <h2>사용자 초대</h2>
</div>

<table>
    <thead>
    <tr>
        <th>사용자 번호</th>
        <th>사용자 이름</th>
        <th>초대</th>
    </tr>
    </thead>
    <tbody>
    <%
        Long chatRoomNo = (Long) request.getAttribute("no");
        List<UserEntity> userList = (List<UserEntity>) request.getAttribute("userList");
        for (UserEntity userDTO : userList) {
    %>
    <tr>
        <td><%= userDTO.getId() %></td>
        <td><%= userDTO.getUsername() %></td>
        <td><a href="<%=request.getContextPath()%>/chat/invite?userNo=<%=userDTO.getId()%>&chatRoomNo=<%=chatRoomNo%>">초대하기</a></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
