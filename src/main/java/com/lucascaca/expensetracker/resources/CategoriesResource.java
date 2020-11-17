package com.lucascaca.expensetracker.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucascaca.expensetracker.domain.Categories;
import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.dto.FindCategoriesDto;
import com.lucascaca.expensetracker.repositories.UserRepository;
import com.lucascaca.expensetracker.services.CategoriesService;

@RestController
@RequestMapping("/api/categories")
public class CategoriesResource {
	
	@Autowired
    CategoriesService categoriesService;

	@GetMapping("")
    public ResponseEntity<List<FindCategoriesDto>> getAllCategories(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
        List<FindCategoriesDto> categories = categoriesService.fetchAllCategories(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<FindCategoriesDto> getCategoryById(HttpServletRequest request,
                                                    @PathVariable("categoryId") Long categoryId) {
    	
        Long userId = (Long) request.getAttribute("userId");
        FindCategoriesDto categoriesDto = categoriesService.fetchCategoryById(userId, categoryId);
        
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<FindCategoriesDto> addCategory(HttpServletRequest request,
                                                @RequestBody Map<String, Object> categoryMap) {
        Long userId = (Long) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        
                
        FindCategoriesDto categoriesDto = categoriesService.addCategory(userId, title, description);
        
        return new ResponseEntity<>(categoriesDto, HttpStatus.CREATED);
    }
    
    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") Long categoryId,
                                                               @RequestBody Map<String, Object> categoryMap) {
    	
    	Long userId = (Long) request.getAttribute("userId");
    	String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
         
        categoriesService.updateCategory(userId, categoryId, title, description);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") Long categoryId) {
    	
    	Long userId = (Long) request.getAttribute("userId");
        categoriesService.removeCategoryWithAllTransactions(userId, categoryId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
