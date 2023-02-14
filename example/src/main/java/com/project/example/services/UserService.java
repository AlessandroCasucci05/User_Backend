package com.project.example.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.example.entities.User;
import com.project.example.exceptions.CouldNotSave;
import com.project.example.exceptions.EmailExistException;
import com.project.example.exceptions.NoUserFoundException;
import com.project.example.repositories.UserRepository;
import com.project.example.utils.CsvUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepo;
    private final CsvUtil csvUtil;


    @Autowired
    public UserService(UserRepository userRepo){
        this.userRepo=userRepo;
        csvUtil= new CsvUtil();
    }

    public User getSpecificUser(Long id) throws Exception{
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        log.error("Could not find user with id {} on the database",id);
        throw new NoUserFoundException();
    }

    public void saveUser(User user) throws Exception{
        try{
            if (doesEmailAlreadyExist(user.getEmail())){
                log.error("Email {} already exists, could not save user",user.getEmail());
                throw new EmailExistException();
            }
            userRepo.save(user);
        }catch(Exception e){
            throw new CouldNotSave(e.getMessage());
        }
    }

    public void changeUser(User userToCopy,Long id) throws Exception{
        User userToChange = getSpecificUser(id);
        mapUserData(userToChange, userToCopy);
        saveUser(userToChange);
    }

    public void deleteUser(Long userToDeleteId) throws Exception{
        User userToDelete = getSpecificUser(userToDeleteId);
            userRepo.delete(userToDelete);
    }

    public List<User> getUsersByName(String userName) throws Exception{
        List<User> users = userRepo.findByName(userName);
        if (!users.isEmpty()){
            return users;
        }
        log.error("Could not find user with username {}",userName);
        throw new NoUserFoundException();
    }

    public User getUserByEmailAndUsername(String email,String username) throws Exception{
        Optional<User> user = userRepo.findByEmailAndUsername(email, username);
        if (user.isPresent()){
            return user.get();
        }
        log.error("User with email {}, and username {}, could not be found",email,username);
        throw new NoUserFoundException();
    }

    public void save(MultipartFile file) throws Exception{
        try {
            if (!csvUtil.isThisCsvFormat(file)){
                throw new Exception("File not in CSV format!");
            }
            List<User> users = csvUtil.convertToUsers(file.getInputStream());
            saveAllUsers(users);
          } catch (Exception e) {
            log.error("Users taken from the csv could not be stored");
            throw new Exception("Fail to store csv data: " + e.getMessage());
          }
    }

    private void saveAllUsers(List<User> users){
        users.forEach(user->{
            if (!doesEmailAlreadyExist(user.getEmail())){
                userRepo.save(user);
            }else{
                log.warn("User with email {} could not be saved; email already exist", 
                user.getEmail());
            }
        });
    }
    

    private void mapUserData(User userToChange,User userToCopy){
        userToChange.setUsername(userToCopy.getUsername());
        userToChange.setAddress(userToCopy.getAddress());
        userToChange.setEmail(userToCopy.getEmail());
        userToChange.setName(userToCopy.getName());
    }

    private boolean doesEmailAlreadyExist(String email){
       return userRepo.findByEmail(email).isPresent();
    }
}


