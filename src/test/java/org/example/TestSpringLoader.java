package org.example;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.config.location=" +
        "classpath:/application.yml"
)
@SpringBatchTest
//@EnableAutoConfiguration
//@ActiveProfiles("local")
public class TestSpringLoader {
    /*
        Test 기본 사항
        given(준비): 어떠한 데이터가 준비되었을 때
        when(실행): 어떠한 함수를 실행하면
        then(검증): 어떠한 결과가 나와야 한다.
    */
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;
}