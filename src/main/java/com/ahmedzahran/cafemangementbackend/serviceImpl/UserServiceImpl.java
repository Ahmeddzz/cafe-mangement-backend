package com.ahmedzahran.cafemangementbackend.serviceImpl;

import com.ahmedzahran.cafemangementbackend.JWT.CustomerUsersDetailsService;
import com.ahmedzahran.cafemangementbackend.JWT.JwtRequestFilter;
import com.ahmedzahran.cafemangementbackend.JWT.JwtUtil;
import com.ahmedzahran.cafemangementbackend.constants.CafeConstants;
import com.ahmedzahran.cafemangementbackend.dao.UserDao;
import com.ahmedzahran.cafemangementbackend.model.User;
import com.ahmedzahran.cafemangementbackend.service.UserService;
import com.ahmedzahran.cafemangementbackend.utils.CafeUtils;
import com.ahmedzahran.cafemangementbackend.utils.EmailUtils;
import com.ahmedzahran.cafemangementbackend.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    EmailUtils emailUtils;


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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

        log.info("Inside login");

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"),
                            requestMap.get("password"))
            );

            if (auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")){
                    log.info("Status is true.");
                    return new ResponseEntity<String>("{\"token\" :\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetails().getEmail(),
                                    customerUsersDetailsService.getUserDetails().getRole()) + "\"}",HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception exp){
            log.error("{}",exp);
        }

        return new ResponseEntity<String>("{\"message\":\"" + "BAD Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{

            if(jwtRequestFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            } else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtRequestFilter.isAdmin()){
               Optional<User> optional =  userDao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty()){
                   userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                   sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(),userDao.getAllAdmin());
                   return CafeUtils.getResponseEntity("User Status updated Successfully.",HttpStatus.OK);
               }else{
                   return CafeUtils.getResponseEntity("User ID does not exist.",HttpStatus.OK);
               }
            } else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user ,List<String> allAdmin) {
        
        allAdmin.remove(jwtRequestFilter.getCurrentUser());
        if(status !=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtRequestFilter.getCurrentUser(),"Account Approved","USER:-"+ user
                    + "\n is approved by \nADMIN:-" + jwtRequestFilter.getCurrentUser() ,allAdmin );
        }else{
            emailUtils.sendSimpleMessage(jwtRequestFilter.getCurrentUser(),"Account Disabled","USER:-"+ user
                    + "\n is disabled by \nADMIN:-" + jwtRequestFilter.getCurrentUser() ,allAdmin );

        }
    }
}
