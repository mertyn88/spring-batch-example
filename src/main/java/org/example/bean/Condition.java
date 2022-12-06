package org.example.bean;

import org.example.bean.annotation.job.EmployeeJobAno;
import org.example.bean.annotation.job.Test1JobAno;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;

public class Condition {

    public static class DatabaseCondition extends AnyNestedCondition {
        public DatabaseCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @EmployeeJobAno
        class EmployeeJobCondition { }
    }

    public static class TestCondition extends AnyNestedCondition {
        public TestCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @EmployeeJobAno
        class EmployeeJobCondition { }

        @Test1JobAno
        class Test1JobCondition { }
    }
}
