package com.itvedant.skincareproducts_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.itvedant.skincareproducts_store.dao.RegisterDao;
import com.itvedant.skincareproducts_store.entity.User;
import com.itvedant.skincareproducts_store.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User createUser(RegisterDao registerDao) {
		
		if(this.userRepository.findByEmail(registerDao.getEmail()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User With email Already exist");
		}
		
		User user = new User();
		
		user.setFirstname(registerDao.getFirstname());
		user.setLastname(registerDao.getLastname());
		user.setPassword(bCryptPasswordEncoder.encode(registerDao.getPassword()));
		user.setEmail(registerDao.getEmail());
		user.setMobile(registerDao.getMobile());
		user.setRoles(registerDao.getRoles());
		                                     
		this.userRepository.save(user);
		
		return user;
	}

}