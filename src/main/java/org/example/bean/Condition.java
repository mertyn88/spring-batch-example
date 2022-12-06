package org.example.bean;

import org.example.bean.annotation.job.EmployeeJobAnnotation;
import org.example.bean.annotation.job.Test1JobAnnotation;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;

public class Condition {

    public static class DatabaseCondition extends AnyNestedCondition {
        public DatabaseCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @EmployeeJobAnnotation
        class EmployeeJobCondition { }
    }

    public static class TestCondition extends AnyNestedCondition {
        public TestCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @EmployeeJobAnnotation
        class EmployeeJobCondition { }

        @Test1JobAnnotation
        class Test1JobCondition { }
    }
}
