# spring-batch-example
Spring batch 예제

## build.gradle
```groovy
// spring framework 사용
plugins {
    id 'java'
    id 'org.springframework.boot' version "${springBootVersion}"
}
```
```groovy
dependencies {
    implementation "org.springframework.boot:spring-boot-autoconfigure:${springBootVersion}"
    implementation "org.springframework.boot:spring-boot-starter-batch:${springBootVersion}"
    // Tomcat on - h2 console
    // h2 web을 사용하지 않을시에는 불필요
    implementation "org.springframework.boot:spring-boot-starter-web:${springBootVersion}"
    // 외부 접속시 runtimeOnly -> implementation 변경
    runtimeOnly "com.h2database:h2:${h2Version}"
}
```

## BatchConfiguration
설정에 관련된 @Bean처리

### jdbcTemplate
Database CRUD

### @EnableBatchProcessing
@EnableBatchProcessing 선언시, `JobBuilderFactory`와 `StepBuilderFactory`이 @Bean으로 등록  

### Datasource
H2 dependencies 추가시, 자동으로 datasource 의존성이 추가  

## BatchJob
실행될 Job들을 정의

## EmployeeStep
Job의 Step중, Employee에 대한 Job step

## EmpJobExecutionListener
`JobExecutionListener`의 구현체, beforeJob, afterJob 구현  

## StepExecutionListener
`Tasklet, StepExecutionListener`의 구현체, beforeJob, execute, afterJob 구현  

## EmployeeItemProcessor  
reader -> processor -> writer 중 processor 단계  

## BatchJobScheduler
Batch Schedule 적용  

## H2-console url
http://localhost:8080/h2-console  
필요값은 시작 로그에 다음과 같이 존재  
```bash
# log
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:[token]'
```