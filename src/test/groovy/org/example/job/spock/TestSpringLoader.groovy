package org.example.job.spock

import org.example.BatchApplication
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = BatchApplication.class, properties = "spring.config.location=classpath:/application.yml")

// @SpringBatchTest

@SpringBootTest(classes = [BatchApplication])
@SpringBatchTest
class TestSpringLoader extends Specification{
    @Autowired ApplicationContext applicationContext
    @Autowired JobBuilderFactory jobBuilderFactory
    @Autowired StepBuilderFactory stepBuilderFactory
    @Autowired JobLauncherTestUtils jobLauncherTestUtils
}
