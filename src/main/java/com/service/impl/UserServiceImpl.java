package com.service.impl;

import com.bean.User;
import com.repository.impl.UserRepositoryImpl;
import com.repository.itf.UserRepository;
import com.service.itf.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository repository = new UserRepositoryImpl();
    @Override
    public List<User> display() {
        return repository.display();
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void create(User user) {
        repository.create(user);
    }

    @Override
    public List<User> search(String name) {
        return repository.search(name);
    }
}
