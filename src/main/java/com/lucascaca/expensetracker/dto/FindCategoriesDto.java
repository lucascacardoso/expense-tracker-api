package com.lucascaca.expensetracker.dto;

import java.math.BigDecimal;

public class FindCategoriesDto {
	
	private Long categoryId;
	private Long userId;
	private String title;
	private String description;
	private BigDecimal totalExpense;	
	
	public FindCategoriesDto() {}

	public FindCategoriesDto(Long categoryId, Long userId, String title, String description, BigDecimal totalExpense) {
		this.categoryId = categoryId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.totalExpense = totalExpense;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}	

}
