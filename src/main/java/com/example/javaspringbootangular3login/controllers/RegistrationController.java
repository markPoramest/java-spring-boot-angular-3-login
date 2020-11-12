package com.example.javaspringbootangular3login.controllers;

import com.example.javaspringbootangular3login.models.User;
import com.example.javaspringbootangular3login.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    RegistrationService service;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/findAll")
    public List<User> findAllUser(){
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    @CrossOrigin("http://localhost:4200/main")
    public User findAllUser(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping("/save")
    public User save(@RequestBody User user)throws Exception{
        String password = user.getPassword();
        String oldPassword = service.findById(user.getId()).getPassword();
        System.out.println("password : "+password);
        System.out.println("Old Password : "+oldPassword);
        boolean check = bCryptPasswordEncoder.matches(password,oldPassword);
        if(!check) {
            throw new Exception("Password doesn't match");
        }

            String encodedPassword = bCryptPasswordEncoder.encode(password);
            user.setPassword(encodedPassword);
            return service.saveUser(user);
    }

    @PostMapping("/register")
    @CrossOrigin("http://localhost:4200/register")
    public User saveUser(@RequestBody  User user) throws Exception {
        String email = user.getEmail();
        System.out.println("email : "+user.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User u1 = null;
        u1 = service.findByEmail(email);
        if(!email.isEmpty() && u1!=null && email.equals(u1.getEmail())){
            throw new Exception("This Email was used!!");
        }
        return service.saveUser(user);
    }
    @PostMapping("/login")
    @CrossOrigin("http://localhost:4200")
    public User loginUser(@RequestBody User user) throws Exception {
        String passwordTrue  = service.findByEmail(user.getEmail()).getPassword();
        String tempEmail = user.getEmail();
        String tempPassword = user.getPassword();
        User user1 = null;
        if(tempEmail != null && tempPassword != null){
            boolean check = bCryptPasswordEncoder.matches(tempPassword,passwordTrue);
            if(check)
                user1 = service.findByEmailAndPassword(tempEmail,passwordTrue);
            else
                throw new Exception("Email or Password Was Wrong");
        }
        if(user1==null){
            throw new Exception("Email or Password Was Wrong");
        }
        return user1;
    }
    @DeleteMapping("/delete/{id}")
    public User delete(@PathVariable("id") Long id){
        return service.delete(id);
    }
}
