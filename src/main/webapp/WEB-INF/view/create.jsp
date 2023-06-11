<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<%--
  Created by IntelliJ IDEA.
  User: phapk
  Date: 25-May-23
  Time: 12:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Borrow</title>
    <link rel="stylesheet" href="/asset/library.css">
    <link rel="stylesheet" href="/asset/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Thêm người dùng</h2>
    <div id="popup">
        <c:if test="${error!=null}">
            <h3>${error}</h3>
        </c:if>
    </div>

    <c:if test="${error==null}">
        <form action="/account" method="post">
            <table class="table">
                <tbody>
                <input type="hidden" name="action" value="createUser">
                <tr class="col">
                    <td>User ID</td>
                    <td>
                        <input type="text" name="userId" value="${userId}"/>
                    </td>
                </tr>
                <tr class="col">
                    <td>User Name</td>
                    <td>
                        <input type="text" name="userName" required/>
                    </td>
                </tr>
                <tr class="col">
                    <td>Code</td>
                    <td>
                        <input type="text" name="code" value="${code}"/>
                    </td>
                </tr>
                <tr class="col">
                    <td>Birthday</td>
                    <td>
                        <input type="date" name="birthday" required/>
                    </td>
                </tr>
                <tr class="col">
                    <td>InitTime</td>
                    <td>
                        <input type="datetime-local" value="<%=(LocalDateTime.now())%>" name="initTime" readonly/>
                    </td>
                </tr>
                <tr class="col">
                    <td>Role</td>
                    <td>
                        <input type="checkbox" name="role" value="1"> Admin
                        <input type="checkbox" name="role" value="2"> User
                    </td>
                </tr>
                </tbody>
            </table>
            <div>
                <a href="/account" class="btn btn-secondary">Cancel</a>
                <input type="submit" class="btn btn-secondary" value="OK">
            </div>
        </form>
    </c:if>
</div>
</body>
<script src="/asset/bootstrap.bundle.min.js"></script>
<script src="/asset/users.js"></script>
<script src="/asset/card.js"></script>
<script src="/asset/student.js"></script>
</html>
