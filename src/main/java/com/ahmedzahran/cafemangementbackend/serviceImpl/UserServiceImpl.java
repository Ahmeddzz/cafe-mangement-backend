package com.ahmedzahran.cafemangementbackend.serviceImpl;

import com.ahmedzahran.cafemangementbackend.constants.CafeConstants;
import com.ahmedzahran.cafemangementbackend.dao.UserDao;
import com.ahmedzahran.cafemangementbackend.model.User;
import com.ahmedzahran.cafemangementbackend.service.UserService;
import com.ahmedzahran.cafemangementbackend.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        log.info("Checking for validity...");

        try{
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));

                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity(CafeConstants.SUCCESSFULLY_REGISTERED,HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity(CafeConstants.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
                }


            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        boolean valid = requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
        return valid;
    }

    private User getUserFromMap(Map<String, String> mapRequest) {
        User user = new User();
        user.setName(mapRequest.get("name"));
        user.setContactNumber(mapRequest.get("contactNumber"));
        user.setEmail(mapRequest.get("email"));
        user.setPassword(mapRequest.get("password"));
        user.setStatus("false");
        user.setRole("User");

        return user;
    }
}
