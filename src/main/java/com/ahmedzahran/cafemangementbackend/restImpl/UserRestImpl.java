package com.ahmedzahran.cafemangementbackend.restImpl;

import com.ahmedzahran.cafemangementbackend.constants.CafeConstants;
import com.ahmedzahran.cafemangementbackend.rest.UserRest;
import com.ahmedzahran.cafemangementbackend.service.UserService;
import com.ahmedzahran.cafemangementbackend.utils.CafeUtils;
import com.ahmedzahran.cafemangementbackend.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> loginIn(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            return userService.getAllUser();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
       try{
           return userService.update(requestMap);
       } catch (Exception ex){
           ex.printStackTrace();
       }

       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
