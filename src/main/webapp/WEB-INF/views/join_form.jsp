<%--
  Created by IntelliJ IDEA.
  User: minwo
  Date: 24. 8. 22.
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입</title>
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

        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        .form-container h2 {
            margin-bottom: 20px;
            color: #4CAF50;
        }

        .form-container input[type="text"],
        .form-container input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .form-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-container input::placeholder {
            color: #888;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>회원 가입</h2>
    <form action="<%=request.getContextPath()%>/user/join" method="post">
        <input type="text" placeholder="아이디를 입력하세요" id="userid" name="username"><br>
        <input type="password" placeholder="비밀번호를 입력하세요" id="userpw" name="password"><br>

        <input type="submit" value="작성 완료">
    </form>
</div>
</body>
</html>
