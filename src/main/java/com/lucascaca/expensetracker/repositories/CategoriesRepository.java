package com.lucascaca.expensetracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucascaca.expensetracker.domain.Categories;
import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.dto.FindCategoriesDto;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>{
	
	
	@Query("select new com.lucascaca.expensetracker.dto.FindCategoriesDto(c.categoryId, c.user.userId, c.title, c.description, coalesce(sum(t.amount), 0) as totalExpense) "
			+ "from Transactions t right join t.category c "
			+ "where c = (select c from Categories c join c.user u where u.userId = :userId) "
			+ "group by c.categoryId")
	List<FindCategoriesDto> findAllByUser(@Param("userId") Long userId);
	
	@Query("select new com.lucascaca.expensetracker.dto.FindCategoriesDto(c.categoryId, c.user.userId, c.title, c.description, coalesce(sum(t.amount), 0) as totalExpense) "
			+ "from Transactions t right join t.category c "
			+ "where c = (select c from Categories c join c.user u where u.userId = :userId) and c.categoryId = :categoryId "
			+ "group by c.categoryId")
	FindCategoriesDto findByIdAndUserId(@Param("categoryId") Long categoryId ,@Param("userId") Long userId);
	
	@Query("select c from Categories c join c.user u where u.userId = :userId and c.categoryId = :categoryId")
	Categories findByCategoryIdAndUserId(@Param("categoryId") Long categoryId ,@Param("userId") Long userId);

}
