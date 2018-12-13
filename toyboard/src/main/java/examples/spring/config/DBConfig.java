package examples.spring.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement    // 트랜잭션과 관련된 AOP를 적용시키는 애노테이션, 없으면 트랜잭션 처리 X
public class DBConfig {
    // ConnectionPool 은 커넥션을 미리 연결해서 가지고 있는 객체이다.
    // 따라서 ConnectionPool은 DB에 접속할 수 있는 설정이 있어야 한다.
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/mysql?characterEncoding=UTF-8&serverTimezone=UTC";
    private String username = "crazy";
    private String password = "1234";

    // PlatformTransactionManager  생성할 DataSource가 메소드로 들어온다. 따라서 Spring 컨테이너는 DataSource를 먼저 만든다.
    // DataSource 가 들어오면, 내부적으로 DataSourceTransactionManager 라는 객체를 생성한다.
    // return type 은 PlatformTransactionManager 인데 리턴하는 것은 DataSourceTransactionManager 인 것을 보아
    // DataSourceTransactionManager는 PlatformTransactionManager 를 상속받고 있는 객체임을 알 수 있다.
    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds){
        return new DataSourceTransactionManager(ds);
    }

    // Spring Container 가 생성할 DataSource
    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;  // DataSource 는 인터페이스이다. 따라서 BasicDataSource 라는 DataSource 를 구현하고 있는 객체를 리턴한다.
    }
}
