package com.itvedant.skincareproducts_store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itvedant.skincareproducts_store.dao.LoginDao;
import com.itvedant.skincareproducts_store.dao.RegisterDao;
import com.itvedant.skincareproducts_store.entity.User;
import com.itvedant.skincareproducts_store.service.UserService;

@Controller
@RequestMapping("/users")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody RegisterDao resiRegisterDao){
		return ResponseEntity.ok(this.userService.createUser(resiRegisterDao));
	}
	
	@GetMapping("/login")
	public String log(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDao loginDao){    // requestBody convert code json to java
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDao.getEmail(),
						                                loginDao.getPassword()));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				return ResponseEntity.ok("Login Succesfull");
		
	}
	
	@GetMapping("/register")
	public String registerGet(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") RegisterDao registerDao, Model model) {
		model.addAttribute("user", registerDao);
		this.userService.createUser(registerDao);
		return "index";
		
	}

}

