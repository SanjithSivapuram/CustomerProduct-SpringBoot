package com.myrestapp.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.myrestapp.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Modifying
	@Transactional
	@Query("select c from Customer c join c.product p where p.id = ?1")
	List<Customer> getCustomerByProductId(Long pid);

}
