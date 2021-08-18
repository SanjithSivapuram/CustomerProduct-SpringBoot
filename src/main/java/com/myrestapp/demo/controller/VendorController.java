package com.myrestapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.demo.model.Vendor;
import com.myrestapp.demo.repository.VendorRepository;

@RestController
public class VendorController {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@PostMapping("/vendor")
	public Vendor postVendor(@RequestBody Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	@GetMapping("/vendor")
	public List<Vendor> getAllVendors() {
		List<Vendor> list = vendorRepository.findAll();
		return list;
	}
	
	@GetMapping("/vendor/{id}")
	public Vendor getVendor(@PathVariable("id") Long id) {
		Vendor vendor = vendorRepository.getById(id);
		return vendor;
	}
	
	@PutMapping("/vendor/{id}")
	public Vendor updateVendor(@RequestBody Vendor vendor,@PathVariable("id") Long id) {
		Vendor vendorDB = vendorRepository.getById(id);
		vendorDB.setName(vendor.getName());
		vendorDB.setCity(vendor.getCity());
		vendorDB.setRating(vendor.getRating());
		return vendorRepository.save(vendorDB);
	}
	
	@DeleteMapping("/vendor/{id}")
	public void deleteVendor(@PathVariable("id") Long id) {
		vendorRepository.deleteById(id);
	}
}
