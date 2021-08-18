package com.myrestapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myrestapp.demo.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
