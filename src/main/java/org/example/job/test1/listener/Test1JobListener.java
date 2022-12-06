package org.example.job.test1.listener;

import org.example.bean.annotation.job.Test1JobAnnotation;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Test1JobAnnotation
public class Test1JobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("test1 beforeJob ::: " + jobExecution.getJobId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("test1 afterJob ::: " + jobExecution.getJobId());
	}
} 