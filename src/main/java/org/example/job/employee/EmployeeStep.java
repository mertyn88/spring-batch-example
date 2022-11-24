package org.example.job.employee;

import lombok.RequiredArgsConstructor;
import org.example.listener.Step2ExecutionListener;
import org.example.listener.Step3ExecutionListener;
import org.example.model.Employee;
import org.example.model.Profile;
import org.example.processor.EmployeeItemProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

// @EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class EmployeeStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final Step2ExecutionListener step2ExecutionListener;
    private final Step3ExecutionListener step3ExecutionListener;

    @Bean
    public Step step1(ItemReader<Employee> reader, ItemWriter<Profile> writer, ItemProcessor<Employee, Profile> processor) {
        return stepBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())  // == step1
                .<Employee, Profile>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

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


    @Bean
    public Step step2() {
        return stepBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())
                .tasklet(step2ExecutionListener)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())
                .tasklet(step3ExecutionListener)
                .build();
    }
}
