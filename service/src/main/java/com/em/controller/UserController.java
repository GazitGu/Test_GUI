package com.em.controller;

import com.em.model.RestResponse;
import com.em.model.User;
import com.em.service.UserService;
import com.em.util.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
@RestController
@RequestMapping(path = "/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "validateUser/{username}/{pwd}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public User validateUser(@PathVariable String username,@PathVariable String pwd) {
        User user = userService.findByUsername(username);
        if(user==null){
            return null;
        }
        if(user.getPassword().equals(Encryptor.encrypt(pwd))){
            return user;
        }
        return null;
    }

    @GetMapping(path = "getAll/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<User> getAll() {
        return userService.findAllUser();
    }

    /*@GetMapping(path = "newUser/{username}/{pwd}/{description}/{role}/{macs}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse newUser(@PathVariable String username,
                                @PathVariable String pwd,@PathVariable String description, @PathVariable int role, @PathVariable String macs) {
        User user = userService.findByUsername(username);
        if(user!=null){
            return new RestResponse(false,"Username "+username+" already exists");
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(Encryptor.encrypt(pwd));
        user.setDescription(description);
        user.setRole(role);
        user.setMacs(macs);
        userService.save(user);
        return new RestResponse(true,"");
    }*/

//    @GetMapping(path = "newUser/{username}/{pwd}/{description}/{role}/", produces = "application/json; charset=UTF-8")
//    @ResponseBody
    @RequestMapping(path = "newUser", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse newUser(@RequestBody User user) {
        User user0 = userService.findByUsername(user.getUsername());
        if(user0!=null){
            return new RestResponse(false,"Username "+user.getUsername()+" already exists");
        }
        userService.save(user);
        return new RestResponse(true,"");
    }

    @GetMapping(path = "updateUser/{username}/{macs}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse updateUser(@PathVariable String username, @PathVariable String macs) {
        User user = userService.findByUsername(username);
        if(user==null){
            return new RestResponse(false,"Username "+username+" was not exists");
        }
        user.setMacs(macs);
        userService.save(user);
        return new RestResponse(true,"");
    }
    @GetMapping(path = "updatePwd/{username}/{pwd}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse updatePwd(@PathVariable String username, @PathVariable String pwd) {
        User user = userService.findByUsername(username);
        if(user==null){
            return new RestResponse(false,"Username "+username+" was not exists");
        }
        user.setPassword(Encryptor.encrypt(pwd));
        userService.save(user);
        return new RestResponse(true,"");
    }
    @GetMapping(path = "deleteUser/{username}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse deleteUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if(user==null){
            return new RestResponse(false,"User "+username+" was not exists");
        }
        userService.delete(user);
        return new RestResponse(true,"");
    }
}
