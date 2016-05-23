package com.trsvax.jacquard.pages.job;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.jacquard.CronJob;
import com.trsvax.jacquard.services.JobRunner;
import com.trsvax.jacquard.services.LogWatcher;

public class JobIndex {
	
	@Inject
	JobRunner jobRunner;
			
	@Property
	Collection<CronJob> jobs;
	
	@Inject
	LogWatcher logWatcher;
	
	@SetupRender
	void setupRender() {
		logWatcher.start("JobIndex");
		jobs = jobRunner.getJobs();
	}
	
	

}
