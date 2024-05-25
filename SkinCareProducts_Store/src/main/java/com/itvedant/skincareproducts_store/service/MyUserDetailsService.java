package com.itvedant.skincareproducts_store.service;


import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itvedant.skincareproducts_store.entity.User;
import com.itvedant.skincareproducts_store.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username)
				                .map(
				                	user -> {	
				                		return new User(
				                				user.getEmail(),
				                				user.getPassword(),
				                				user.getRoles().stream()
				                				    .map(role -> new SimpleGrantedAuthority(role))
				                				    .collect(Collectors.toList())
				                				
				                				);
				                	}).orElseThrow(() -> {
				                		throw new UsernameNotFoundException("User not found");
				                	});
	}
	

}
