package com.itvedant.skincareproducts_store;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import com.itvedant.skincareproducts_store.service.MyUserDetailsService;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain config(HttpSecurity http) throws Exception{
		
		http = http.csrf().disable();
		http.authorizeHttpRequests()
		    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
		    .requestMatchers(HttpMethod.POST,"/products/**").permitAll()
		    .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
		    .requestMatchers(HttpMethod.GET, "/").authenticated()
		    .anyRequest().permitAll()
		    .and().formLogin(form -> form
			          .loginPage("/users/login")
			          .usernameParameter("email")
			          .passwordParameter("password")
			          .defaultSuccessUrl("/index")
			          .failureUrl("/login?loginError=true"))
			    .httpBasic(withDefaults())
			    .authenticationProvider(daoAuthenticationProvider());
		    
		    return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {     // password encode karte
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService users() {
		
		UserDetails user = User.builder()
				           .username("user")
				           .password(passwordEncoder().encode("password"))
				           .roles("USER")
				           .build();
		
		UserDetails admin = User.builder()
				            .username("admin")
				            .password(passwordEncoder().encode("password"))
				            .roles("ADMIN", "USER")
				            .build();
		
		return new InMemoryUserDetailsManager(user, admin);

	}
	
	
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(myUserDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
}


