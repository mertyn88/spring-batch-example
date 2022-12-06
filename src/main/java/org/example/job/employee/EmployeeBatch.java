package org.example.job.employee;

import lombok.RequiredArgsConstructor;
import org.example.bean.annotation.job.EmployeeJobAnnotation;
import org.example.job.employee.listener.EmployeeJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EmployeeJobAnnotation
public class EmployeeBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step step2;
    private final Step step3;

    /*
        Bean의 객체를 인자값으로 할당하거나, autowired로 지정하는 방식 있음
     */
    @Bean
    public Job employeeJob(EmployeeJobListener listener, Step step1) {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return jobBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())  // == batchJob
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
