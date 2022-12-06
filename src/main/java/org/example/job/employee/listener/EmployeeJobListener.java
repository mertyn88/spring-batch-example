package org.example.job.employee.listener;

import lombok.RequiredArgsConstructor;
import org.example.bean.annotation.job.EmployeeJobAnnotation;
import org.example.job.employee.model.Profile;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
@RequiredArgsConstructor
@EmployeeJobAnnotation
public class EmployeeJobListener implements JobExecutionListener {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {
	    System.out.println("Executing job id " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	        List<Profile> result = jdbcTemplate.query("SELECT empCode, empName, profileName FROM profile", 
	        		new RowMapper<Profile>() {
	            @Override
	            public Profile mapRow(ResultSet rs, int row) throws SQLException {
	                return new Profile(rs.getLong(1), rs.getString(2), rs.getString(3));
	            }
	        });
	        System.out.println("Number of Records:"+result.size());
	    }
	}
} 