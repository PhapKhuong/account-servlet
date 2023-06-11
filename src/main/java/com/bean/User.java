package com.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class User {
    private int userId;
    private String fullName;
    private String code;
    private LocalDate birthday;
    private LocalDateTime initTime;
    private List<Role> roles;

    public User() {
    }

    public User(int userId, String fullName, String code,
                LocalDate birthday, LocalDateTime initTime, List<Role> roles) {
        this.userId = userId;
        this.fullName = fullName;
        this.code = code;
        this.birthday = birthday;
        this.initTime = initTime;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public void setInitTime(LocalDateTime initTime) {
        this.initTime = initTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
