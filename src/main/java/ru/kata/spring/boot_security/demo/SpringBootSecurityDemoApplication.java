package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.Collections;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleDAO roleDAO;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
		userDAO.deleteAll();
		roleDAO.deleteAll();
		Role user = roleDAO.save(new Role("ROLE_USER"));
		Role admin = roleDAO.save(new Role("ROLE_ADMIN"));
		userDAO.save(User.builder()
				.name("admin")
				.lastname("adminov")
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.roles(Collections.singletonList(admin)).build()
		);
	}
}
