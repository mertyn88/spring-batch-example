package org.example.job.spock.employee

import org.example.job.employee.EmployeeBatch
import org.example.job.employee.listener.EmployeeJobListener
import org.example.job.employee.model.Employee
import org.example.job.employee.model.Profile
import org.example.job.spock.TestSpringLoader
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class EmployeeBatchTest extends TestSpringLoader {

    static def JOB_NAME = "employeeJob"
    static {
        System.properties.'job.name' = JOB_NAME
    }

    @MockBean Job job
    @Autowired Step step1
    @Autowired Step step2
    @Autowired Step step3
    @Autowired EmployeeJobListener listener
    @Autowired JdbcTemplate jdbcTemplate

    def "스팍 테스트"() {
        println(JOB_NAME)

        given:
        def number = 3

        when:
        def result = number > 1

        then:
        result == true
    }

    def setup() {
        println('Setup job')
        job = new EmployeeBatch(jobBuilderFactory, step2, step3).employeeJob(listener, step1)
        jobLauncherTestUtils.setJob(job)
        assert Objects.nonNull(jobLauncherTestUtils)
        assert Objects.nonNull(job)
    }

    def "Step - itemReader Test"() {
        given:
        ItemReader<Employee> reader = applicationContext.getBean('employeeItemReader')
        reader.open(new ExecutionContext()) // reader open 메소드가 실행하지 않으면 reader는 null
        reader.afterPropertiesSet() // Reader의 쿼리를 생성. 이 메소드가 실행되지 않으면 Reader의 쿼리가 null

        when:
        Employee data = reader.read()

        then:
        data.getEmpCode() == 111
        data.getEmpName() == 'Mahesh'
    }

    def "Step - itemProcessor Test"() {
        given:
        ItemProcessor<Employee, Profile> processor = applicationContext.getBean('employeeItemProcessor')

        when:
        Profile profile = processor.process(new Employee(){{
            setEmpCode(1)
            setEmpName('test')
            setExpInYears(10)
        }})

        then:
        profile.getEmpCode() == 1
        profile.getEmpName() == 'test'
        profile.getProfileName() == 'Manager'
    }


    def "Step - itemWriter Test"() {
        given:
        ItemWriter<Profile> writer = applicationContext.getBean('employeeItemWriter')
        writer.write(List.of(new Profile(1, 'test', 'Manager')))

        when:
        Profile result = jdbcTemplate.queryForObject("SELECT empCode, empName, profileName FROM profile WHERE empCode = 1", new RowMapper<Profile>() {
            @Override
            Profile mapRow(ResultSet rs, int row) throws SQLException {
                return new Profile(rs.getLong(1), rs.getString(2), rs.getString(3));
            }
        })

        then:
        result.getEmpCode() == 1
        result.getEmpName() == 'test'
        result.getProfileName() == 'Manager'
    }

    def "Step Execute Test"() {
        given:
        def jobExecution = jobLauncherTestUtils.launchStep("step1")

        when:
        def stepExecution = jobExecution.getStepExecutions().iterator().next()

        then:
        stepExecution.getExitStatus() == ExitStatus.COMPLETED
    }

    def "Step Execute after, Query Test"() {
        given:
        jobLauncherTestUtils.launchStep("step1")

        expect:
        jdbcTemplate.queryForObject("SELECT empName FROM profile WHERE empCode = " + code + " LIMIT 1", String.class) == name

        where:
        code | name
        111  | 'Mahesh'
        112  | 'Krishna'
        113  | 'Rama'
        114  | 'Shiva'
    }
}