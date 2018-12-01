package com.txr.forlove.service;

import com.txr.forlove.domain.User;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    User save(User user);

    void deleteById(Long id);

    Page<User> findAll(Pageable pageable);

    User findByName(String name);
}
