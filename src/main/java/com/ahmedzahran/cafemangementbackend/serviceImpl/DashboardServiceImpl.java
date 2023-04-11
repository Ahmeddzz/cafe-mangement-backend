package com.ahmedzahran.cafemangementbackend.serviceImpl;

import com.ahmedzahran.cafemangementbackend.dao.BillDao;
import com.ahmedzahran.cafemangementbackend.dao.CategoryDao;
import com.ahmedzahran.cafemangementbackend.dao.ProductDao;
import com.ahmedzahran.cafemangementbackend.model.Product;
import com.ahmedzahran.cafemangementbackend.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {

        Map<String, Object> map = new HashMap<>();
        map.put("category",categoryDao.count());
        map.put("product",productDao.count());
        map.put("bill",billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
