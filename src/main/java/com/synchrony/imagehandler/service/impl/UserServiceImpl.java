package com.javadevjournal.user.service;

import com.javadevjournal.system.exception.UserAlreadyExistException;
import com.javadevjournal.user.jpa.data.UserEntity;
import com.javadevjournal.user.jpa.repository.UserRepository;
import com.javadevjournal.web.data.user.UserData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    
    @Override
    public void register(User user) throws UserAlreadyExistException {

        //Check if user already is registered
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistException("User exists for this email. Register with new email");
        }
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        userRepository.save(user);
    }

    
    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) !=null ? true : false;
    }

    private void encodePassword( UserEntity userEntity, UserData user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}