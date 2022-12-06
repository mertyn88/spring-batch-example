package org.example.job.employee.step;

import lombok.RequiredArgsConstructor;
import org.example.bean.annotation.job.EmployeeJobAnnotation;
import org.example.job.employee.model.Employee;
import org.example.job.employee.model.Profile;
import org.example.job.employee.step.listener.EmployeeStep2Listener;
import org.example.job.employee.step.listener.EmployeeStep3Listener;
import org.example.job.employee.step.processor.EmployeeJobItemProcessor;
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
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

// @EnableBatchProcessing
@RequiredArgsConstructor
@EmployeeJobAnnotation
public class EmployeeStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final EmployeeStep2Listener employeeStep2Listener;
    private final EmployeeStep3Listener employeeStep3Listener;

    @Bean
    public Step step1(ItemReader<Employee> reader, ItemWriter<Profile> writer, ItemProcessor<Employee, Profile> processor) {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
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
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
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
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return new JdbcBatchItemWriterBuilder<Profile>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Profile>())
                .sql("INSERT INTO profile (empCode, empName, profileName) VALUES (:empCode, :empName, :profileName)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Profile> processor() {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return new EmployeeJobItemProcessor();
    }


    @Bean
    public Step step2() {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return stepBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())
                .tasklet(employeeStep2Listener)
                .build();
    }

    @Bean
    public Step step3() {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return stepBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())
                .tasklet(employeeStep3Listener)
                .build();
    }
}
