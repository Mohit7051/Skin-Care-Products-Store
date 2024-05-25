package com.itvedant.skincareproducts_store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itvedant.skincareproducts_store.entity.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
