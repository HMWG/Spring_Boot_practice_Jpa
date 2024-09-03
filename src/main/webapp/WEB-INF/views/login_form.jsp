<%--
  Created by IntelliJ IDEA.
  User: minwo
  Date: 24. 8. 14.
  Time: 오후 4:13
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: minwo
  Date: 24. 8. 14.
  Time: 오후 4:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .login-container h2 {
            margin-bottom: 20px;
            color: #4CAF50;
        }

        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .login-container input[type="checkbox"] {
            margin-right: 5px;
        }

        .login-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .login-container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .login-container label {
            font-size: 14px;
            display: block;
            text-align: left;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>로그인</h2>
    <form action="<%=request.getContextPath()%>/user/login" method="post">
        <label for="userid">ID</label>
        <input type="text" name="username" id="userid" value="<%
            Cookie[] cookies = request.getCookies();
            boolean checkLogin = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("remId")) {
                        %><%=cookie.getValue()%><%
                    } else if(cookie.getName().equals("remLogin")) {
                        session.setAttribute("loginId", cookie.getValue());
                        //response.sendRedirect(request.getContextPath()+"/main");
                        checkLogin = true;
                    }
                }
            }
            %>">
        <%
            if (checkLogin){
        %>
        <script>
            alert("자동로그인 되었습니다.")
            location.href="/main";
        </script>
        <%
        }
        %>
        <label for="userpw">PW</label>
        <input type="password" name="password" id="userpw">
        <label><input type="checkbox" name="remId"> 아이디 기억하기</label>
        <label><input type="checkbox" name="remLogin"> 자동 로그인</label>
        <input type="submit" value="로그인">
    </form>
</div>
</body>
</html>
