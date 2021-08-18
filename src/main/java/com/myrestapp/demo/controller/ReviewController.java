package com.myrestapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.demo.model.Customer;
import com.myrestapp.demo.model.Product;
import com.myrestapp.demo.model.Review;
import com.myrestapp.demo.repository.CustomerRepository;
import com.myrestapp.demo.repository.ProductRepository;
import com.myrestapp.demo.repository.ReviewRepository;

@RestController
public class ReviewController {
	
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping("/review/{pid}/{cid}")
	public Review postReview(@RequestBody Review review, @PathVariable("pid") Long product_id, @PathVariable("cid") Long customer_id) {
		Customer customer = customerRepository.getById(customer_id);
		Product product = productRepository.getById(product_id);
		review.setCustomer(customer);
		review.setProduct(product);
		return reviewRepository.save(review);
	}
	
	@GetMapping("/review")
	public List<Review> getAllReviews() {
		List<Review> list = reviewRepository.findAll();
		return list;
	}
	
	@GetMapping("/review/{id}")
	public Review getReview(@PathVariable("id") Long id) {
		Review review = reviewRepository.getById(id);
		return review;
	}
	
	@GetMapping("/review/product")
	public List<Review> getAllReviewsByProduct(@RequestParam("pid") Long pid) {
		return reviewRepository.findByProductId(pid);
	}
	
	@GetMapping("/review/customer")
	public List<Review> getAllReviewsByCustomer(@RequestParam("cid") Long cid) {
		return reviewRepository.findByCustomerId(cid);
	}
	
	@DeleteMapping("/review/{id}")
	public void deleteReview(@PathVariable("id") Long id) {
		reviewRepository.deleteById(id);
	}
	
	@DeleteMapping("/review/product")
	public void deleteReviewByProduct(@RequestParam("pid") Long pid) {
		reviewRepository.deleteProduct(pid);
	}
	
	@DeleteMapping("/review/customer")
	public void deleteReviewByCustomer(@RequestParam("cid") Long cid) {
		reviewRepository.deleteCustomer(cid);
	}
	
	@GetMapping("/review/product/{price}")
	public List<Review> getReviewByProductPrice(@PathVariable("price") Double price) {
		return reviewRepository.getReviewByProductPrice(price);
	}
	
	@GetMapping("/review/product/customer/{price}/{city}")
	public List<Review> getReviewByProductAndCustomer(@PathVariable("price") Double price,@PathVariable("city") String city) {
		return reviewRepository.getReviewByProductAndCustomer(price,city);
	}
}
