package com.tds.auth.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLogAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@AfterThrowing(value = "within(com.tds.auth.service.impl.*)", throwing = "exception")
	public void afterThrow(JoinPoint joinPoint, Throwable exception) throws Throwable {
		StringBuilder messageString = new StringBuilder();
		messageString.append("An Exception occurred: ").append(exception.getMessage()).append("\n Following are the parameters of method throwing exception:\n");
		Object[] parameterValues = joinPoint.getArgs();
		
		MethodSignature method = (MethodSignature)joinPoint.getSignature();
        String[] parameters = method.getParameterNames();
        
        
        for (int t = 0; t< parameters.length; t++) {
        	messageString.append(parameters[t]).append(" : ").append(parameterValues[t]).append(" \n ");
        }
        logger.error(messageString.toString());
        
	}
}
