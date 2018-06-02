package com.cdk.contactapp.contactapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdk.contactapp.contacts.Contacts;

@SpringBootApplication
@RestController
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
    @CrossOrigin
	@RequestMapping(value="/contacts",produces = "application/json")
	public List<Contacts> appName() {
		
		List<Contacts> list = new ArrayList<>();		
		try {
			Connection connection = App.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * from people");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Contacts contacts=new Contacts();
				contacts.setFname(rs.getString(2));
				contacts.setLname(rs.getString(3));				
				list.add(contacts);
								
			}
	
			pstmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/sample_db");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
