package com.trsvax.jacquard.services;

import java.util.Collection;

import com.trsvax.jacquard.CronJob;

public interface JobRunner {

	public Collection<CronJob> getJobs();
	
	public void logRun(String pageName);
	public void logFail(String pageName);
}
