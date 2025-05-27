package cn.xilio.falcon.job.spring.boot.starter;

import cn.xilio.falcon.job.coe.executor.FalconJobExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FalconJobSpringExecutor extends FalconJobExecutor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {
    private ApplicationContext applicationContext;

    @Override
    public void afterSingletonsInstantiated() {
        super.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
