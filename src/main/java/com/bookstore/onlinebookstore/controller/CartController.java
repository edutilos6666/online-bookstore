package com.bookstore.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.model.forms.AddressForm;
import com.bookstore.onlinebookstore.service.AddressService;
import com.bookstore.onlinebookstore.service.CartService;

@Controller
@RequestMapping("/cart")
@SessionAttributes({ "cart" })
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressService addressService;

	/*
	 * ******* Generate a BrainTreeGateway token for payment transaction ******
	 * 
	 * @RequestMapping(value = "/cart/braintree/cltoken", method =
	 * RequestMethod.POST, produces = "application/json") public @ResponseBody
	 * Map<String, String> getClientToken() { BraintreeGateway gateway =
	 * processPaymentService.getBrainTreeGateway(); ClientTokenRequest
	 * clientTokenRequest = new ClientTokenRequest(); String clientToken =
	 * gateway.clientToken().generate(clientTokenRequest); HashMap<String, String>
	 * map = new HashMap<>(); map.put("clientToken", clientToken); return map; }
	 */
	@PostMapping("/cart.do")
	public String cartItem(ModelMap modelMap, HttpServletRequest request) {
		cartService.addItemToCart(modelMap, request);
		return "redirect:/cart/view";
	}

	@PostMapping("/update")
	public String updateItem(ModelMap modelMap, HttpServletRequest request) {
		cartService.updateItem(modelMap, request);
		return "redirect:/cart/view";
	}

	@RequestMapping("/view")
	public String getShoppingCart(ModelMap modelMap) {
		return "shopping-cart";
	}

	@RequestMapping("/checkout")
	@PostMapping("/checkout")
	public String checkoutCart(ModelMap modelMap, HttpServletRequest request, AddressForm addressForm) {
		// if shopping cart is empty redirect user to the home page
		// mostly recently used shipping address
		User user = (User) request.getSession().getAttribute("user");
		addressService.getRecentlyUsedAddress(modelMap, user.getId());
		return "checkout";
	}

	@PostMapping("/address/add")
	public String addNewShippingAddress(ModelMap modelMap, @Valid AddressForm addressForm, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("--------------------------Error-----------------------------");
			System.out.println(bindingResult.getFieldError());
			return "/cart/checkout";
		}
		User user = (User) request.getSession().getAttribute("user");
		addressService.addAdress(addressForm, user.getId());

		return "redirect:/cart/checkout";
	}
}