package com.txr.forlove.service.impl;

import com.txr.forlove.domain.User;
import com.txr.forlove.repository.UserReposiory;
import com.txr.forlove.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource private UserReposiory userReposiory;

    @Override
    public User findById(Long id) {
        return userReposiory.findById(id.toString()).get();
    }

    @Override
    public List<User> findAll() {
        return userReposiory.findAll();
    }

    @Override
    public User save(User user) {
        return userReposiory.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userReposiory.deleteById(id.toString());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public User findByName(String name) {
        return userReposiory.findByName(name);
    }
}
