package com.ahmedzahran.cafemangementbackend.JWT;

import com.ahmedzahran.cafemangementbackend.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class CustomerUsersDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    private com.ahmedzahran.cafemangementbackend.model.User userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {} ", username);
        userDetails = userDao.findByEmailId(username);
        if(!Objects.isNull(userDetails)){
            return new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>() );
        } else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.ahmedzahran.cafemangementbackend.model.User getUserDetails(){
        com.ahmedzahran.cafemangementbackend.model.User user = userDetails;
        user.setPassword(null);
        return user;
    }
}
