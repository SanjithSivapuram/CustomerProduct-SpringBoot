package com.myrestapp.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.demo.dto.ProductDto;
import com.myrestapp.demo.model.Product;
import com.myrestapp.demo.model.Vendor;
import com.myrestapp.demo.repository.ProductRepository;
import com.myrestapp.demo.repository.VendorRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private VendorRepository vendorRepository;

	@PostMapping("/product/{id}")
	public Product postProduct(@RequestBody Product product, @PathVariable("id") Long vendor_id) {
		Vendor vendor = vendorRepository.getById(vendor_id);
		product.setVendor(vendor);
		return productRepository.save(product);
	}

	@GetMapping("/product")
	public List<ProductDto> getAllProducts(
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String direction) {
		List<Product> list = new ArrayList<>();
		if (direction.equalsIgnoreCase("DESC")) {
			list = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		} else {
			list = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
		}
		List<ProductDto> listDto = new ArrayList<>();
		
		for(Product p : list) {
			ProductDto dto = new ProductDto();
			dto.setId(p.getId());
			dto.setName(p.getVendor().getName());
			dto.setPrice(p.getPrice());
			dto.setTitle(p.getTitle());
			listDto.add(dto);
		}
		return listDto;
	}

	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id);
		return product;
	}

	@GetMapping("/product/vendor/{id}")
	public List<Product> getAllProductsByVendor(@PathVariable("id") Long id) {
		return productRepository.findByVendorId(id);
	}

	@PutMapping("/product/{id}/{vid}")
	public Product updateProduct(@RequestBody Product product, @PathVariable("id") Long id,
			@PathVariable("vid") Long vid) {
		Product productDB = productRepository.getById(id);
		Vendor vendor = vendorRepository.getById(vid);
		productDB.setTitle(product.getTitle());
		productDB.setPrice(product.getPrice());
		productDB.setVendor(vendor);
		return productRepository.save(productDB);
	}

	@GetMapping("/product/customer/{cid}")
	public List<Product> getProductByCustomerId(@PathVariable("cid") Long cid) {
		return productRepository.getProductByCustomerId(cid);
	}
}
