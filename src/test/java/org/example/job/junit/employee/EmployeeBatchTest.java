package org.example.job.junit.employee;

import org.example.job.employee.EmployeeBatch;
import org.example.job.junit.TestSpringLoader;
import org.example.job.employee.listener.EmployeeJobListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;


class EmployeeBatchTest extends TestSpringLoader {

    static String JOB_NAME = "employeeJob";
    static {
        System.setProperty("job.name", JOB_NAME);
    }


    /*
    Mock으로 생성하지 않으면 job.name에 의해 Spring Loading중 해당 Job이 실행
     */
    @MockBean
    private Job job;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    Step step1;

    @Autowired
    Step step2;

    @Autowired
    Step step3;

    @Autowired
    EmployeeJobListener listener;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        // given
        System.out.println("Create Job ::: " + JOB_NAME);
        job = new EmployeeBatch(jobBuilderFactory, step2, step3).employeeJob(listener, step1);
        jobLauncherTestUtils.setJob(job);
    }

    @Test
    @DisplayName(value = "EmployeeJob - Job 테스트")
    public void test_employee_Job() throws Exception {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        // then
        assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
    }

    @Test
    @DisplayName(value = "EmployeeJob - Step1 테스트")
    public void test_employee_Step1() {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        // then
        assertEquals(stepExecution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(
                jdbcTemplate.queryForObject(
                        "SELECT empName FROM profile WHERE empCode = '111'", String.class)
                    , "Mahesh"
        );
    }

    @Test
    @DisplayName(value = "EmployeeJob - Step2 테스트")
    public void test_employee_Step2() {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step2");
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        // then
        assertEquals(stepExecution.getExitStatus(), ExitStatus.COMPLETED);
    }

    @Test
    @DisplayName(value = "EmployeeJob - Step3 테스트")
    public void test_employee_Step3() {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step3");
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        // then
        assertEquals(stepExecution.getExitStatus(), ExitStatus.COMPLETED);
    }
}