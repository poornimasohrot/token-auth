package com.tds.auth.log;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

public class PerformanceLogMonitoring extends AbstractMonitoringInterceptor {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		long startTime = System.currentTimeMillis();
		 Object returnedObject = null;
		try {
		  returnedObject = invocation.proceed();
		}
		finally {
			long endTime = System.currentTimeMillis();
			long result = endTime - startTime;
			logger.trace("Response time from method: " + invocation.getMethod().getName() + " of class:"+ invocation.getMethod().getDeclaringClass().getName() + " is: " + result + "ms.");
		}
		 
		return returnedObject;
	}

}
