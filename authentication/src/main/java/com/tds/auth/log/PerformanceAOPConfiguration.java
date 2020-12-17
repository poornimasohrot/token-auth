package com.tds.auth.log;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class PerformanceAOPConfiguration {
	
	@Bean
	public PerformanceLogMonitoring performanceLogMonitoring() {
		return new PerformanceLogMonitoring();
				
	}
	
	@Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("within(com.tds.auth.controller.*)"); // com.tds.auth.log.PerformanceAOPConfiguration.monitor()
        return new DefaultPointcutAdvisor(pointcut, performanceLogMonitoring());
    }
}
