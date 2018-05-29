package com.em.service;

import com.em.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void delete(User user);
}
