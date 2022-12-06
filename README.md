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

```bash
# Job 실행
# Environment Variables Set
--job.name=employeeJob
--job.name=test1Job
```

## BatchConfiguration
설정에 관련된 @Bean처리

### jdbcTemplate
Database CRUD

### @EnableBatchProcessing
@EnableBatchProcessing 선언시, `JobBuilderFactory`와 `StepBuilderFactory`이 @Bean으로 등록  

## H2Configuration
H2 Database 관련된 @Bean처리  

### Datasource
local testdb를 메모리 형식으로 사용하도록 설정.  
H2 dependencies 추가 및 dataSource를 지정하지 않으면, 자동으로 datasource 의존성이 추가

## Annotation
특정 Job에 해당하는 @Bean만 등록하기 위해 처리되는 @Bean  

### Condition
AnyNestedCondition(OR 조건)으로 특정 Config를 그룹화하고 Job이름에 속하면 해당 @Bean은 로드한다.

### @DatabaseConfiguration
DB에 관련된 Config 집합.  

### @TestConfiguration
테스트용 집합.  

### @EmployeeJobAno
EmployeeJob관련 어노테이션

### @Test1JobAno
Test용 어노테이션

## EmployeeBatch
H2 디비를 사용하는 예제 Job  

## EmployeeStep
Job의 Step중, Employee에 대한 Job step

## EmployeeJobListener
`JobExecutionListener`의 구현체, beforeJob, afterJob 구현  

## StepExecutionListener
`Tasklet, StepExecutionListener`의 구현체, beforeJob, execute, afterJob 구현  

## EmployeeItemProcessor  
reader -> processor -> writer 중 processor 단계

## H2-console url
http://localhost:8080/h2-console  
필요값은 시작 로그에 다음과 같이 존재  
```bash
# log
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'
```