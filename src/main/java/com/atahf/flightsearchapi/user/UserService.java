package com.atahf.flightsearchapi.user;

import com.atahf.flightsearchapi.user.UserDto.UserInfoDto;
import com.atahf.flightsearchapi.utils.NotFoundException;
import com.atahf.flightsearchapi.utils.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);

        if(user == null) throw new UsernameNotFoundException(String.format("Username %s not found", username));

        return user;
    }

    public boolean userExist(String username) {
        return userDao.findUserByUsername(username) != null;
    }

    @Transactional
    public void addUser(UserInfoDto userInfoDto) throws Exception {
        User user = userDao.findUserByUsername(userInfoDto.getUsername());
        if(user != null) throw new UserExistsException("Username Already Exists!");

        User newUser = new User(userInfoDto);
        newUser.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        userDao.save(newUser);
    }

    @Transactional
    public void deleteUser(String username) throws Exception {
        User user = userDao.findUserByUsername(username);
        if(user == null) throw new NotFoundException("Username Not Found!");

        userDao.delete(user);
    }
}
