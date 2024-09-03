<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>사용자 헤더</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      font-size: 14px;
      border-bottom: 2px solid #45a049;
    }

    .header .message {
      margin-right: 10px;
    }

    .header a {
      color: white;
      text-decoration: none;
      margin-left: 10px;
      font-size: 14px;
      padding: 5px 10px;
      background-color: #45a049;
      border-radius: 3px;
      transition: background-color 0.3s ease;
    }

    .header a:hover {
      background-color: #3e8e41;
    }

    hr {
      margin: 0;
      border: 0;
      border-top: 1px solid #ddd;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="message">
    <%
      String loginId = (String)session.getAttribute("loginUsername");
      if(loginId != null) {
    %>
    <%=loginId%>님 로그인 중이시네요. 환영합니다.
    <%
    } else {
    %>
    <a href="<%=request.getContextPath()%>/user/login">로그인 하러 가기</a>
    <a href="<%=request.getContextPath()%>/user/join">회원가입</a>
    <%
      }
    %>
  </div>
  <div>
    <%
      if(loginId != null) {
    %>
    <a href="<%=request.getContextPath()%>/user/logout">로그아웃</a>
    <%
      }
    %>
  </div>
</div>
<hr>
</body>
</html>
