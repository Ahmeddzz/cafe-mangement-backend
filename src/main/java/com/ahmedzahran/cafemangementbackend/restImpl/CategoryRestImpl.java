package com.ahmedzahran.cafemangementbackend.restImpl;

import com.ahmedzahran.cafemangementbackend.constants.CafeConstants;
import com.ahmedzahran.cafemangementbackend.model.Category;
import com.ahmedzahran.cafemangementbackend.rest.CategoryRest;
import com.ahmedzahran.cafemangementbackend.service.CategoryService;
import com.ahmedzahran.cafemangementbackend.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;


    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {

        try{
            return categoryService.getAllCategories(filterValue);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoryService.updateCategory(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
