package com.example.javaspringbootangular3login.services;

import com.example.javaspringbootangular3login.models.User;
import com.example.javaspringbootangular3login.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    RegistrationRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User saveUser(User user){
        return repository.save(user);
    }

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public User findByEmailAndPassword(String email, String password){
        return repository.findByEmailAndPassword(email,password);
    }

    public User findById(Long id){
       return repository.findById(id).isPresent()?repository.findById(id).get():null;
    }

    public User delete(Long id){
        User user = findById(id);
        if(user!=null)
            repository.deleteById(id);
        return  user;

    }
}
