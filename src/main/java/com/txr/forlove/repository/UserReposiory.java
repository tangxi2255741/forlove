package com.txr.forlove.repository;

import com.txr.forlove.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReposiory extends JpaRepository<User,String> {

    User findByName(String name);

    List<User> findByIdIn(List<Long> ids);
}
