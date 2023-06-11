package com.service.impl;

import com.bean.Role;
import com.repository.impl.RoleRepositoryImpl;
import com.repository.itf.RoleRepository;
import com.service.itf.RoleService;

public class RoleServiceImpl implements RoleService {
    RoleRepository repository = new RoleRepositoryImpl();

    @Override
    public Role search(int id) {
        return repository.search(id);
    }
}
