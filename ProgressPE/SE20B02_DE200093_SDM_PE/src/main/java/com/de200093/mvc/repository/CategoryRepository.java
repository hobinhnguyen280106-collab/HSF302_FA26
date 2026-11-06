package com.de200093.mvc.repository;

import com.de200093.mvc.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
