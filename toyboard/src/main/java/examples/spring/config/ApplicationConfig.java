package examples.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration                  // Java Config 임을 나타냄
@Import( {DBConfig.class} )     // Import : 다른 Java Config를 같이 읽어들이겠다!
                                // config 설정을 나누는 이유는 무엇에 관한 설정인지 구분지어 관리하기 위함이다.
public class ApplicationConfig {
}
