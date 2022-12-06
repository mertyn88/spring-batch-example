package org.example.job.test1;

import lombok.RequiredArgsConstructor;
import org.example.bean.annotation.job.Test1JobAno;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@Test1JobAno
public class Test1Batch {
    private final JobBuilderFactory jobBuilderFactory;
    private final JobExecutionListener test1Test1Listener;
    private final Step test1Step;

    @Bean
    public Job test1Job() {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return jobBuilderFactory
                .get(new Object(){}.getClass().getEnclosingMethod().getName())
                .listener(test1Test1Listener)
                .start(test1Step)
                .build();
    }
}
