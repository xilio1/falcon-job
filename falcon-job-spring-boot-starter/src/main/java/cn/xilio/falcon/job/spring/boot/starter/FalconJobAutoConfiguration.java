package cn.xilio.falcon.job.spring.boot.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FalconJobAutoConfiguration {
    @Bean
    public FalconJobSpringExecutor falconJobSpringExecutor() {
        FalconJobSpringExecutor executor = new FalconJobSpringExecutor();
        return executor;
    }
}
