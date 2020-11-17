package com.lucascaca.expensetracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucascaca.expensetracker.domain.Categories;
import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.dto.FindCategoriesDto;
import com.lucascaca.expensetracker.exceptions.EtBadRequestException;
import com.lucascaca.expensetracker.exceptions.EtResourceNotFoundException;
import com.lucascaca.expensetracker.repositories.CategoriesRepository;
import com.lucascaca.expensetracker.repositories.UserRepository;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService{
	
	@Autowired
	CategoriesRepository categoriesRepository;
	
	@Autowired
    UserRepository userRepository;

	@Override
	public List<FindCategoriesDto> fetchAllCategories(Long userId) {
		return categoriesRepository.findAllByUser(userId);
	}

	@Override
	public FindCategoriesDto fetchCategoryById(Long userId, Long categoryId) throws EtResourceNotFoundException {
		return categoriesRepository.findByIdAndUserId(categoryId, userId);
	}

	@Override
	public FindCategoriesDto addCategory(Long userId, String title, String description) throws EtBadRequestException {
		
		Categories categories = new Categories();
		categories.setTitle(title);
		categories.setDescription(description);
		
		User user = userRepository.findById(userId).get();
		
		categories.setUser(user);
		
		categoriesRepository.save(categories);
		
		return categoriesRepository.findByIdAndUserId(categories.getCategoryId(), userId); 
	}

	@Override
	public void updateCategory(Long userId, Long categoryId, String title, String description) throws EtBadRequestException {

		Categories updatedCategory = categoriesRepository.findByCategoryIdAndUserId(categoryId, userId);
		
		updatedCategory.setTitle(title);
		updatedCategory.setDescription(description);;
		
		categoriesRepository.save(updatedCategory);
	}

	@Override
	public void removeCategoryWithAllTransactions(Long userId, Long categoryId)
			throws EtResourceNotFoundException {

		Categories category = categoriesRepository.findByCategoryIdAndUserId(categoryId, userId);
		
		categoriesRepository.delete(category);
	}

}
