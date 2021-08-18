package com.myrestapp.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.demo.model.Customer;
import com.myrestapp.demo.model.Product;
import com.myrestapp.demo.repository.CustomerRepository;
import com.myrestapp.demo.repository.ProductRepository;

@RestController
@CrossOrigin(origins = "http://localhost:59279", methods = {RequestMethod.POST,RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PUT})
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/customer")
	public Customer postCustomer(@RequestBody Customer customer) {
		// Save is like insert method in the database.
		return customerRepository.save(customer);
	}

	@GetMapping("/customer")
	public List<Customer> getAllCustomers(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size",required = false,defaultValue = "10000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Customer> list = customerRepository.findAll(pageable).getContent();
		return list;
	}

	@GetMapping("/customer/{id}") // {id} sending id as parameter
	public Customer getCustomerById(@PathVariable("id") Long id) {
		return customerRepository.getById(id);
	}

	@PutMapping("/customer/{id}")
	public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer newCustomerVal) {
		Customer customerDB = customerRepository.getById(id);
		customerDB.setName(newCustomerVal.getName());
		customerDB.setCity(newCustomerVal.getCity());
		return customerRepository.save(customerDB);
	}

	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
	}

	// Writing insertion function for many to many relation tables
	@PostMapping("/customer/product/{cid}/{pid}")
	public void purchaseApi(@PathVariable("cid") Long cid, @PathVariable("pid") Long pid) {
		// to insert into the relationship table we have to save always save using the
		// owners relation table
		Product product = productRepository.getById(pid);
		Customer customer = customerRepository.getById(cid);

		List<Product> list = new ArrayList<>();
		list.add(product);
		customer.setProduct(list);
		customerRepository.save(customer);
	}

	// inserting multiple products at once
	@PostMapping("/customer/multiple/product/{cid}")
	public void purchaseMultiple(@PathVariable("cid") Long cid, @RequestParam("strId") String strId) {
		// to insert into the relationship table we have to save always save using the
		// owners relation table
		Customer customer = customerRepository.getById(cid);

		String[] strArry = strId.split(",");
		List<Long> listID = new ArrayList<>();

		for (String s : strArry) {
			listID.add(Long.parseLong(s));
		}

		List<Product> listProduct = productRepository.findAllById(listID);
		customer.setProduct(listProduct);
		customerRepository.save(customer);
	}

	@DeleteMapping("/customer/product/{cid}/{pid}")
	public void deletePurchase(@PathVariable("cid") Long cid, @PathVariable("pid") Long pid) {
		Customer customer = customerRepository.getById(cid);
		List<Product> productList = customer.getProduct();
		List<Product> productListMain = new ArrayList<>();

		for (Product p : productList) {
			if (!p.getId().equals(pid)) {
				productListMain.add(p);
			}
		}

		customer.setProduct(productListMain);
		customerRepository.save(customer);
	}

	@GetMapping("/customer/product/{pid}")
	public List<Customer> getCustomerByProductId(@PathVariable("pid") Long pid) {
		return customerRepository.getCustomerByProductId(pid);
	}
}
