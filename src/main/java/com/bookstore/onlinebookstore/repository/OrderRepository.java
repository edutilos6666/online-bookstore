package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public Order findByUserIdOrderByDateOrderedDesc(Long userId);
	public Order findFirstByOrderByOrderIdDesc();
}
