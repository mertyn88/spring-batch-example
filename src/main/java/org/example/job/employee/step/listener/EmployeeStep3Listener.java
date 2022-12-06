package org.example.job.employee.step.listener;

import org.example.bean.annotation.job.EmployeeJobAnnotation;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

@EmployeeJobAnnotation
public class EmployeeStep3Listener implements Tasklet, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before step");
        System.out.println("Executing Step3 id " + stepExecution.getId());

        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        String param = (String) executionContext.get("param");
        System.out.println("Parameter ::: " + param);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After step 3");
        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Step 3 complete");
        }
        return ExitStatus.COMPLETED;
    }
}
