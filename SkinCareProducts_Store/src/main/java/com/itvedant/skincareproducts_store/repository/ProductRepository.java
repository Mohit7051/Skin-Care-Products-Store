package com.itvedant.skincareproducts_store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itvedant.skincareproducts_store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
