package com.service.itf;

import com.bean.User;

import java.util.List;

public interface UserService {
    List<User> display();

    void update(User user);

    void delete(int id);

    void create(User user);

    List<User> search(String name);
}
