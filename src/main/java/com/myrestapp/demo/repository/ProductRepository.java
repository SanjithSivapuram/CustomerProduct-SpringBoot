package com.myrestapp.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.myrestapp.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByVendorId(Long id);
	
	Product findByTitle(String title);

	@Modifying
	@Transactional
	@Query("select p from Product p join p.customer c where c.id = ?1")
	List<Product> getProductByCustomerId(Long cid);

}
