package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//@Component 붙어 있는 객체는 스프링 컨테이너가 관리하는 객체가 된다. Bean
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	// main메소드는 Spring이 관리 안한다.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// DataSource Bean(Spring이 관리하는 객체)
	// Spring이 Object로 참고할 수 있는 모든 Bean을 주입한다.
	@Autowired // 자동으로 주입
	RoleDao roleDao;

	@Override
	public void run(String... args) throws Exception {
//		Role role = new Role();
//		role.setRoleId(3);
//		role.setName("ROLE_TEST");
//		roleDao.addRole(role);

//		boolean flag = roleDao.deleteRole(3);
//		System.out.println("flag = " + flag);

//		Role role = roleDao.getRole(1);
//		System.out.println(role.getRoleId() + ", " + role.getName());

//		List<Role> list = roleDao.getRoles();
//		for(Role role : list) {
//			System.out.println(role.getRoleId() + ", " + role.getName());
//		}
	}

}
