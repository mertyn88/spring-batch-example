package org.example.job.test1.step;

import lombok.RequiredArgsConstructor;
import org.example.bean.annotation.job.Test1JobAno;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@Test1JobAno
public class Test1Step {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step test1JobStep() {
        System.out.println("===================================> " + new Object(){}.getClass().getEnclosingMethod().getName());
        return stepBuilderFactory.get(new Object(){}.getClass().getEnclosingMethod().getName())
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }
}
