package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.example.bean.annotation.config.DatabaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@DatabaseConfiguration
public class H2Configuration {

	@Bean @Primary
	public DataSource dataSource() {
		System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setJdbcUrl("jdbc:h2:mem:testdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}
