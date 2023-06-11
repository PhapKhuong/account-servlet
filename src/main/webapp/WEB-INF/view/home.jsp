<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.LocalDateTime" %>
<%--
  Created by IntelliJ IDEA.
  User: phapk
  Date: 24-May-23
  Time: 2:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/asset/bootstrap.min.css">
    <link rel="stylesheet" href="/asset/library.css">
</head>
<body>
<div class="container">

    <!--TẠO THANH MENU TRÊN ĐẦU-->
    <div id="menu">
        <div id="position-menu">
            <a href="/account">User</a>
        </div>
    </div>
    <!--KẾT THÚC-->

    <!--TẠO THÂN TRANG-->
    <div id="body-page">
        <div class="content">
            <div class="main-content">
                <c:if test="${error!=null}">
                    <h3 class="popup">${error}</h3>
                </c:if>

                <c:if test="${error==null}">
                    <!--FORM TÌM KIẾM USER-->
                    <form class="d-flex" action="/account" method="post">
                        <input type="hidden" name="action" value="searchUser">
                        <input class="form-control me-2" type="search" placeholder="Search by name" aria-label="Search"
                               name="searchUserByName">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>

                    <!--PHẦN HIỂN THỊ BẢNG USER-->
                    <h2>Danh sách sách</h2>
                    <table class="table">
                        <thread>
                            <tr>
                                <th scope="col">User ID</th>
                                <th scope="col">Full Name</th>
                                <th scope="col">Code</th>
                                <th scope="col">Birthday</th>
                                <th scope="col">InitTime</th>
                                <th scope="col">Role</th>
                                <th>
                                    <form action="/account" method="post">
                                        <input type="hidden" name="action" value="initCreateUser">
                                        <input type="submit" class="btn btn-primary" value="Create">
                                    </form>
                                </th>
                                <th></th>
                            </tr>
                        </thread>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <c:forEach items="${user.roles}" var="r">
                                <tr class="col">
                                    <td>${user.userId}</td>
                                    <td>${user.fullName}</td>
                                    <td>${user.code}</td>
                                    <td>
                                        <fmt:parseDate var="parseBirrthday" pattern="yyyy-MM-dd"
                                                       value="${user.birthday}" type="date"/>
                                        <fmt:formatDate value="${parseBirrthday}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>${user.initTime}</td>
                                    <td>${r.getRoleName()}</td>
                                    <td>
                                        <button
                                                type="button"
                                                class="btn btn-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#editUserModal"
                                                data-bs-userId="${user.userId}"
                                                data-bs-userName="${user.fullName}"
                                                data-bs-code="${user.code}"
                                                data-bs-birthday="${user.birthday}"
                                                data-bs-initTime="<%=(LocalDateTime.now())%>">
                                            Edit
                                        </button>
                                    </td>
                                    <td>
                                        <button
                                                type="button"
                                                class="btn btn-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#delUserModal"
                                                data-bs-userId="${user.userId}">
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!--TẠO MODAL SỬA THÔNG TIN USER-->
                    <div class="modal fade"
                         id="editUserModal"
                         tabindex="-1"
                         aria-labelledby="editUserModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editUserModalLabel">Sửa thông tin người dùng</h5>
                                    <button type="button"
                                            class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Close">
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="/account" method="post">
                                        <input type="hidden" name="action" value="editUser">
                                        <div class="mb-3">
                                            <label for="userId" class="col-form-label">Mã người dùng:</label>
                                            <input type="text" class="form-control" id="userId" name="userId" readonly/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="userName" class="col-form-label">Tên người dùng:</label>
                                            <input type="text" class="form-control" id="userName" name="userName"
                                                   required/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="code" class="col-form-label">Code:</label>
                                            <input type="text" class="form-control" id="code" name="code" readonly/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="birthday" class="col-form-label">Ngày sinh:</label>
                                            <input type="date" class="form-control" id="birthday"
                                                   name="birthday" required/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="initTime" class="col-form-label">Khởi tạo:</label>
                                            <input type="datetime-local" class="form-control" id="initTime"
                                                   name="initTime" readonly/>
                                        </div>
                                        <div class="mb-3">
                                            <label class="col-form-label">Role:</label>
                                            <input type="checkbox" name="role" value="1"/> Admin
                                            <input type="checkbox" name="role" value="2"/> User
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                Close
                                            </button>
                                            <input type="submit" class="btn btn-secondary" value="OK">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--KẾT THÚC TẠO MODAL SỬA THÔNG TIN USER-->
                </c:if>

                <!--TẠO MODAL XÓA USER-->
                <div class="modal fade"
                     id="delUserModal"
                     tabindex="-1"
                     aria-labelledby="delUserModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="delUserModalLabel">Xóa người dùng</h5>
                                <button type="button"
                                        class="btn-close"
                                        data-bs-dismiss="modal"
                                        aria-label="Close">
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="/account" method="post">
                                    <input type="hidden" name="action" value="delUser">
                                    <div class="mb-3">
                                        <input type="number" class="form-control" name="userId" id="delUserId"
                                               readonly/>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                            Close
                                        </button>
                                        <input type="submit" class="btn btn-secondary" value="OK">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!--KẾT THÚC TẠO MODAL XÓA USER-->
            </div>
        </div>
    </div>
    <!--KẾT THÚC THÂN TRANG-->

</div>
</body>
<script src="/asset/bootstrap.bundle.min.js"></script>
<script src="/asset/users.js"></script>
<script src="/asset/student.js"></script>
<script src="/asset/card.js"></script>
</html>
