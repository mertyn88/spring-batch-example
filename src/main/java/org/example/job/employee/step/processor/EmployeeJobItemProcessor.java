package org.example.job.employee.step.processor;

import org.example.job.employee.model.Employee;
import org.example.job.employee.model.Profile;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeJobItemProcessor implements ItemProcessor<Employee, Profile> {
    @Override
    public Profile process(final Employee emp) throws Exception {
	String profileName = "";
        if (emp.getExpInYears() < 5) {
        	profileName = "Developer";
        } else if (emp.getExpInYears() >= 5 && emp.getExpInYears() <= 8) {
        	profileName = "Team Lead";
        } else if (emp.getExpInYears() > 8) {
        	profileName = "Manager";
        }
        System.out.println("Emp Code: " + emp.getEmpCode() + 
        		", Emp Name: " + emp.getEmpName() + ", Profile Name:" + profileName);
	return new Profile(emp.getEmpCode(), emp.getEmpName(), profileName);
    }
} 