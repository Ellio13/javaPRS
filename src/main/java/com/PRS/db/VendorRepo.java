package com.PRS.db;


import org.springframework.data.jpa.repository.JpaRepository;

import com.PRS.model.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer>{  //provide CRUD functionality
}
