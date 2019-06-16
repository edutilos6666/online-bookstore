package com.bookstore.onlinebookstore.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.model.enums.RoleType;
import com.bookstore.onlinebookstore.repository.UserRepository;
import com.bookstore.onlinebookstore.service.BookService;
import com.bookstore.onlinebookstore.service.ShoppingCartService;

@Controller
@SessionAttributes({ "URL_REF", "user", "cart"})
public class MainController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private BookService bookService;
	@Autowired
	private ShoppingCartService shoppingCartService;
//	@Autowired
//	private BookRepository bookRepository;

	@RequestMapping("/top")
//	public @ResponseBody List<Book> getAll(@RequestParam String q) {
	public @ResponseBody List<Book> getAll() {
//		return bookService.getSearchResults(q);
		return bookService.getAncientLiteratureBooks();
	}

	@RequestMapping("/query")
	public @ResponseBody List<Book> getQueryResults(@RequestParam String q) {
		return bookService.getQueryResults(q);
	}

	@RequestMapping("/search")
	public String getQuerySearch(@RequestParam String q, ModelMap modelMap) {
		modelMap.put("query", q);
		modelMap.put("title", q + " - " + "OnlineBookstore");
		List<Book> list = bookService.getSearchResults(q);
		modelMap.put("numOfResults", list.size());
		modelMap.put("searchResultList", list);

		return "search-results";
	}

	@RequestMapping("/test")
	public String test() {
		return "test";
	}

	@RequestMapping("/")
	public String userHomePage(Model model, ModelMap modelMap) {
		// Links
//		modelMap.put("userLogin", "/login");
//		modelMap.put("userRegister", "/register");
//		modelMap.put("home", "/");
		// List of Books
		modelMap.put("topPopularBooksList", bookService.getPopularBooks());
		modelMap.put("topRatedBooksList", bookService.getTopRatedBooks());
		modelMap.put("topRatedBooksByYearList", bookService.getTopRatedBooksByYear());
		modelMap.put("ancientLitBooksList", bookService.getAncientLiteratureBooks());
		return "home";
	}

	@RequestMapping("/register")
	public String userRegistrationPage(ModelMap modelMap) {
		modelMap.put("reg", "/register.do");
		return "register";
	}

	@PostMapping("/register.do")
	public String validateUserRegistrationForm(ModelMap modelMap, HttpServletRequest request,
			RedirectAttributes redirectAttr) {
		System.out.println(request.getParameter("firstName"));
		System.out.println(request.getParameter("lastName"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("verifyPassword"));
		User user = new User(request.getParameter("firstName"), request.getParameter("lastName"),
				request.getParameter("email"), passwordEncoder.encode(request.getParameter("password")), new Date(),
				RoleType.CUSTOMER);
		userRepo.save(user);
		redirectAttr.addFlashAttribute("REGISTRATION_SUCCESSFUL_MESSAGE",
				"Success! You have successfully registered for an account.");
		return "redirect:/login"; // send a registration successful message/hidden input to login page
	}

	@RequestMapping("/login")
	public String userLoginPage(ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("userLogin", "/login.do");
		System.out.println("Referer: " + request.getHeader("Referer"));

		modelMap.addAttribute("URL_REF", request.getHeader("Referer"));

		return "login";
	}

	@RequestMapping("/account")
	public String userAccountPage(ModelMap modelMap, Principal principal) {
		modelMap.put("welcomeMessage", "Welcome!");
//		User user = userRepo.findByEmail(principal.getName());
//		user.setPassword(null);
//		user.setId(null);
//		modelMap.put("user", user);
//
//		System.out.println(user);
		return "account";
	}

	@RequestMapping("/admin")
	public String adminHomePage(ModelMap modelMap) {
		return "admin";
	}

	@RequestMapping("/loginSuccessful")
	public String userLoginSuccessful(@RequestParam String role, ModelMap modelMap, HttpServletRequest request,
			Principal principal) {

		User user = userRepo.findByEmail(principal.getName());
		user.setPassword(null);
		// user.setId(null);
		modelMap.put("user", user);
		Cart cart = shoppingCartService.getSavedUserShoppingCart(user);
		System.out.println("-------------------------------------");
		System.out.println(cart);
		modelMap.put("cart", cart);
		System.out.println(user);

		String url_ref = request.getSession().getAttribute("URL_REF").toString();
		String url = null;
		if (url_ref != null && url_ref.contains("/cart/view")) {
			System.out.println("-------------------------------" + url_ref);
			url = "redirect:/cart/checkout";
		} else {
			System.out.println("hello: " + url_ref);
			url = "redirect:/account";
		}
		return url;
	}

	@RequestMapping("/logoutSuccessful")
	public String userLogoutValidation(ModelMap modelMap, RedirectAttributes redirectAttr) {
		System.out.println("You have logged out!");
		redirectAttr.addFlashAttribute("LOGOUT_SUCCESSFUL_MESSAGE", "Success! You have successfully logged out.");
		return "redirect:/login";
	}
}