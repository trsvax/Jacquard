package com.trsvax.jacquard.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ClassNameLocator;
import org.apache.tapestry5.ioc.services.cron.CronSchedule;
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.slf4j.Logger;

import com.trsvax.jacquard.CronJob;
import com.trsvax.jacquard.JacquardSymbols;
import com.trsvax.jacquard.Schedule;

@EagerLoad
public class JobRunnerTapestry implements JobRunner {
	
	private final PeriodicExecutor periodicExecutor;
	private final Logger logger;
	private final WebService webService;	
	private final String baseURL;
	private final ComponentClassResolver componentClassResolver;
	
	private final Map<String,CronJob> jobs = new HashMap<>();

	public JobRunnerTapestry(PeriodicExecutor periodicExecutor, Logger logger, 
			@Symbol(JacquardSymbols.JOBURL) String baseURL,
			@Symbol(JacquardSymbols.JOBPACKAGE) String jobPackage,
			ComponentClassResolver componentClassResolver,
			ClassNameLocator classNameLocator, WebService webService) throws ClassNotFoundException {
		this.periodicExecutor = periodicExecutor;
		this.webService = webService;
		this.logger = logger;
		this.baseURL = baseURL;
		this.componentClassResolver = componentClassResolver;
		
		if ( baseURL != null && baseURL.length() > 0 && jobPackage != null && jobPackage.length() > 0 ) {			
			Collection<String> jobClasses = classNameLocator.locateClassNames(jobPackage);
			for ( String jobClass : jobClasses ) {
				logger.info("try {}",jobClass);
				addJob(Class.forName(jobClass));
			}
		}
	}
	
	void addJob(Class<?> jobClass) {
			Schedule schedule = jobClass.getAnnotation(Schedule.class);
			if ( schedule != null && schedule.value() != null  ) {
				String pageName = componentClassResolver.resolvePageClassNameToPageName(jobClass.getName());
				logger.info("Schedule {} {}",pageName,schedule);
				CronJob cronJob = new CronJob(pageName);
				cronJob.setSchedule(schedule.value());
				cronJob.setJob(periodicExecutor.addJob(new CronSchedule(schedule.value()), pageName, job(jobClass)));
				jobs.put(pageName, cronJob);
			}
				
	}
	
	Runnable job(final Class<?> jobClass) {		
		final String url = baseURL + jobClass.getSimpleName();

		return new Runnable() {
			
			@Override
			public void run() {
				logger.info("run {} {}", jobClass.getSimpleName(), url);
				webService.content(url);
			}
		};
	}

	@Override
	public Collection<CronJob> getJobs() {
		
		return jobs.values();
	}

	@Override
	public void logRun(String pageName) {
		if ( jobs.containsKey(pageName)) {
			jobs.get(pageName).setLastRun(new Date());
		}
	}

	@Override
	public void logFail(String pageName) {
		if ( jobs.containsKey(pageName)) {
			jobs.get(pageName).setLastFailure(new Date());	
		}
	}
	

}
