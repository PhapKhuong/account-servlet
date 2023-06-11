package com.repository.itf;

import com.bean.User;

import java.util.List;

public interface UserRepository {
    List<User> display();

    void update(User book);

    void delete(int id);

    void create (User book);

    List<User> search(String name);
}
