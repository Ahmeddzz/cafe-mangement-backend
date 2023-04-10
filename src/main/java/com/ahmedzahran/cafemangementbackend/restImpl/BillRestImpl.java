package com.ahmedzahran.cafemangementbackend.restImpl;

import com.ahmedzahran.cafemangementbackend.rest.BillRest;
import com.ahmedzahran.cafemangementbackend.service.BillService;
import com.ahmedzahran.cafemangementbackend.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.somethingWentWrong();
    }
}
