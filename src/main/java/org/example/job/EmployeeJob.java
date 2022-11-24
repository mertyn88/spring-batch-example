package org.example.job;

import lombok.RequiredArgsConstructor;
import org.example.job.step.EmployeeStep;
import org.example.listener.EmpJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class EmployeeJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final Step step2;
    private final Step step3;
    /*
        Bean의 객체를 인자값으로 할당하거나, autowired로 지정하는 방식 있음
     */
    @Bean
    public Job jobEmployee(EmpJobExecutionListener listener, Step step1) {
        return jobBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())  // == createEmployeeJob
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                //.flow(step)
                .start(step1)
                .on("COMPLETED")
                .to(step2)
                .next(step3)
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
