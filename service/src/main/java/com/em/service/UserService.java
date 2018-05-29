package com.em.service;

import com.em.model.User;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
public interface UserService {

    User findByUsername(String username);

    List<User> findAllUser();

    User save(User user);

    void delete(User user);

}
