package com.ultracash.upi.api.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SendHeartBeatManager implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SendHeartBeatManager.class);

	private ScheduledExecutorService scheduler;	

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SendHeartBeatJob(), 0, 2, TimeUnit.MINUTES);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
		try {
			scheduler.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
