package examples.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement    // 트랜잭션과 관련된 AOP를 적용시키는 애노테이션, 없으면 트랜잭션 처리 X
public class DBConfig {
}
