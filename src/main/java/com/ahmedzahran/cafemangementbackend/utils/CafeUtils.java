package com.ahmedzahran.cafemangementbackend.utils;

import com.ahmedzahran.cafemangementbackend.constants.CafeConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {

    private CafeUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"Message\": \""+ responseMessage +"\" }", httpStatus);
    }

    public static ResponseEntity<String> somethingWentWrong(){
        return getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
