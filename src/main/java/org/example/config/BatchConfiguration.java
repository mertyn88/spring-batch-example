package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.model.Employee;
import org.example.model.Profile;
import org.example.processor.EmployeeItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

	@Bean
	public FlatFileItemReader<Employee> reader() {
		return new FlatFileItemReaderBuilder<Employee>()
		  .name("employeeItemReader")		
		  .resource(new ClassPathResource("employees.csv"))
		  .delimited()
		  .names(new String[]{ "empCode", "empName", "expInYears" })
		  .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
			   setTargetType(Employee.class);
		  }})
		  .linesToSkip(1)
		  .build();
	} 	

	@Bean
	public JdbcBatchItemWriter<Profile> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Profile>()
		   .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Profile>())
		   .sql("INSERT INTO profile (empCode, empName, profileName) VALUES (:empCode, :empName, :profileName)")
		   .dataSource(dataSource)
		   .build();
	}

	@Bean
	public ItemProcessor<Employee, Profile> processor() {
		return new EmployeeItemProcessor();
	}

	/*@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setJdbcUrl("jdbc:h2:tcp://localhost/~/mydb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}*/

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
} 