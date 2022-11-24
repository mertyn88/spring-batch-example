package org.example.job.step;

import lombok.RequiredArgsConstructor;
import org.example.listener.Step2ExecutionListener;
import org.example.listener.Step3ExecutionListener;
import org.example.model.Employee;
import org.example.model.Profile;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
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
