package org.example.job.employee.step.listener;

import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class EmployeeStep2Listener implements Tasklet, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before step");
        System.out.println("Executing Step2 id " + stepExecution.getId());
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        /*
        contribution - 현재 단계 실행을 업데이트하기 위해 다시 전달되는 변경 가능한 상태
        chunkContext - 호출 간에는 공유되지만 재시작 간에는 공유되지 않는 속성
         */

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After step");
        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Step complete");

            // context 이관
            stepExecution
                    .getJobExecution()
                    .getExecutionContext()
                    .put("param", "hello!!!");
        }
        return ExitStatus.COMPLETED;
    }
}
