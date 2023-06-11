package com.controller;

import com.bean.Role;
import com.bean.User;
import com.exception.ExistException;
import com.exception.ValidateException;
import com.service.impl.RoleServiceImpl;
import com.service.impl.UserServiceImpl;
import com.service.itf.RoleService;
import com.service.itf.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private RoleService roleService = new RoleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showListUser(req, resp);
    }

    private void showListUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.display();
        req.setAttribute("users", users);
        req.getRequestDispatcher("WEB-INF/view/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {

            case "initCreateUser":
                doInitCreateUser(req, resp);
                break;
            case "createUser":
                doCreateUser(req, resp);
                break;
            case "editUser":
                doEditUser(req, resp);
                break;
            case "delUser":
                doDelUser(req, resp);
                break;
            case "searchUser":
                doSearchUser(req, resp);
                break;
            default:
                break;
        }
    }

    private void doInitCreateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<User> users = userService.display();
        int initUserId = 0;
        int maxCode = 0;
        if (users.size() == 0) {
            initUserId = 1;
            maxCode = 1;
        } else {
            for (User user : users) {
                int id = user.getUserId();
                int code = Integer.parseInt(user.getCode().substring(2));
                if (id + 1 > initUserId) {
                    initUserId = id + 1;
                }
                if (code + 1 > maxCode) {
                    maxCode = code + 1;
                }
            }
        }

        String initCode;
        if (maxCode < 10) {
            initCode = "U-000" + maxCode;
        } else if (maxCode < 100) {
            initCode = "U-00" + maxCode;
        } else if (maxCode < 1000) {
            initCode = "U-0" + maxCode;
        } else {
            initCode = "U-" + maxCode;
        }

        req.setAttribute("userId", initUserId);
        req.setAttribute("code", initCode);
        req.getRequestDispatcher("WEB-INF/view/create.jsp").forward(req, resp);
    }

    private void doCreateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            List<User> users = userService.display();
            int userId = Integer.parseInt(req.getParameter("userId"));
            String code = req.getParameter("code");

            for (User user : users) {
                if (user.getUserId() == userId || user.getCode().equals(code)) {
                    throw new ExistException();
                }
            }

            if (!code.matches("^U-[\\d]{4}$")) {
                throw new ValidateException();
            }

            String userName = req.getParameter("userName");
            LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
            LocalDateTime initTime = LocalDateTime.parse(req.getParameter("initTime"));

            String[] roleAsString = req.getParameterValues("role");
            List<Role> roleList = new ArrayList<>();
            for (String r : roleAsString) {
                Role role = roleService.search(Integer.parseInt(r));
                roleList.add(role);
            }

            User user = new User(userId, userName, code, birthday, initTime, roleList);

            userService.create(user);
            resp.sendRedirect("/account");
        } catch (NullPointerException e) {
            String errorMsg = "Bạn chưa chọn role";
            req.setAttribute("error", errorMsg);
            req.getRequestDispatcher("WEB-INF/view/create.jsp").forward(req, resp);
        } catch (ExistException e) {
            String errorMsg = "Mã người dùng đã tồn tại";
            req.setAttribute("error", errorMsg);
            req.getRequestDispatcher("WEB-INF/view/create.jsp").forward(req, resp);
        } catch (ValidateException e) {
            String errorMsg = "Mã code người dùng không đúng định dạng";
            req.setAttribute("error", errorMsg);
            req.getRequestDispatcher("WEB-INF/view/create.jsp").forward(req, resp);
        }
    }

    private void doEditUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            int userId = Integer.parseInt(req.getParameter("userId"));
            String userName = req.getParameter("userName");
            String code = req.getParameter("code");
            LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
            LocalDateTime initTime = LocalDateTime.parse(req.getParameter("initTime"));

            String[] roleAsString = req.getParameterValues("role");
            List<Role> roleList = new ArrayList<>();
            for (String r : roleAsString) {
                Role role = roleService.search(Integer.parseInt(r));
                roleList.add(role);
            }

            User user = new User(userId, userName, code, birthday, initTime, roleList);

            userService.update(user);
            resp.sendRedirect("/account");
        } catch (NullPointerException e) {
            String errorMsg = "Bạn chưa chọn role";
            req.setAttribute("error", errorMsg);
            req.getRequestDispatcher("WEB-INF/view/create.jsp").forward(req, resp);
        }
    }

    private void doDelUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        userService.delete(userId);
        resp.sendRedirect("/account");
    }

    private void doSearchUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = req.getParameter("searchUserByName");
        String userName = "%" + str + "%";
        List<User> users = userService.search(userName);
        req.setAttribute("users", users);
        req.getRequestDispatcher("WEB-INF/view/home.jsp").forward(req, resp);
    }
}
