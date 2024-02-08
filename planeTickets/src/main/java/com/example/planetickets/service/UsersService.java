package com.example.planetickets.service;

import com.example.planetickets.dto.InfoUser;
import com.example.planetickets.model.UsersModel;
import com.example.planetickets.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String name,String password,String email)
    {
        if(name!=null && password!=null)
        {
            UsersModel usersModel=new UsersModel();
            usersModel.setName(name);
            usersModel.setPassword(password);
            usersModel.setEmail(email);
            usersModel.setRole("CLIENT");
            return usersRepository.save(usersModel);
        }
        else
        {
            return null;
        }
    }

    public UsersModel authenticate(String name,String password)
    {
        return usersRepository.findByNameAndPassword(name,password).orElse(null);
    }

    public List<UsersModel> findAllUsers() {
        return usersRepository.findAll();
    }

    public void removeUser(InfoUser infoUser)
    {
        //usersRepository.deleteUsersByFirstName(infoUser.getId());
        usersRepository.deleteById(infoUser.getId());
    }
    public void editUser(InfoUser infoUser)
    {
        UsersModel usersModel=new UsersModel();
        usersModel.setId(infoUser.getId());
        usersModel.setName(infoUser.getName());
        usersModel.setPassword(infoUser.getPassword());
        usersModel.setEmail(infoUser.getEmail());
        usersModel.setRole(infoUser.getRole());
        usersRepository.save(usersModel);
    }

}
