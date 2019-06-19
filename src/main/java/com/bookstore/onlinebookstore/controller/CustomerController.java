package com.bookstore.onlinebookstore.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.AddressService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.OrderedBookService;

@Controller
@RequestMapping("/account")
public class CustomerController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private OrderedBookService orderedBookService;

	@RequestMapping("/order-details")
	public String getOrderDetails(@RequestParam String orderID, ModelMap modelMap, HttpServletRequest request,
			Principal principal) {
		if (orderID != null) {
			System.out.println(orderID);
			String hash = orderID;
			User user = (User) request.getSession().getAttribute("user");
			Order order = orderService.getOrderByHashAndByUserId(hash, user.getId());
			if (order != null) {

				System.out.println(order);
				modelMap.put("orderedDate", orderService.formatDate(order.getDateOrdered()));
				modelMap.put("order", order);

				Address address = addressService.getAddressById(order.getAddressId());
				modelMap.put("userAddress", address);
				List<Item> orderedBooks = orderedBookService.getOrderedBooksByOrderId(order.getOrderId());
				System.out.println(orderedBooks);
				modelMap.put("orderedBooks", orderedBooks);
				modelMap.put("totalItemsOrdered", orderedBookService.getTotalQuantity(orderedBooks));

				System.out.println(address);

			} else {
				return "redirect:/account/order-history";
			}
		}
		return "order-details";
	}

	/*
	 * @RequestMapping("/order-details") public String getOrderDetails(ModelMap
	 * modelMap) { return "redirect:/account/order-history"; }
	 */

	@RequestMapping("/order-history")
	public String getOrderHistory(ModelMap modelMap, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(user);
		HashMap<Order, List<Item>> orders = orderedBookService
				.getOrderedBooks(orderService.getAllOrdersByUserId(user.getId()));
		modelMap.put("orders", orders);
		System.out.println(orders);		

		return "order-history";
	}

	@RequestMapping("/profile")
	public String getAccountProfile() {
		return "account-profile";
	}
}