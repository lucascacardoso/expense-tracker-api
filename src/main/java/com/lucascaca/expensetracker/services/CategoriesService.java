package com.lucascaca.expensetracker.services;

import java.util.List;

import com.lucascaca.expensetracker.domain.Categories;
import com.lucascaca.expensetracker.dto.FindCategoriesDto;
import com.lucascaca.expensetracker.exceptions.EtBadRequestException;
import com.lucascaca.expensetracker.exceptions.EtResourceNotFoundException;

public interface CategoriesService {
	
	List<FindCategoriesDto> fetchAllCategories(Long userId);

	FindCategoriesDto fetchCategoryById(Long userId, Long categoryId) throws EtResourceNotFoundException;

    FindCategoriesDto addCategory(Long userId, String title, String description) throws EtBadRequestException;

    void updateCategory(Long userId, Long categoryId, String title, String description) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(Long userId, Long categoryId) throws EtResourceNotFoundException;

}
