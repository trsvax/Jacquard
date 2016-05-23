package com.trsvax.jacquard;

import java.util.Date;

import org.apache.tapestry5.ioc.services.cron.PeriodicJob;

public class CronJob {
	
	public CronJob(String pageName) {
		this.pageName = pageName;
	}
	
	String pageName;	
	String schedule;	
	Date lastRun;	
	Date LastFailure;	
	PeriodicJob job;
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public Date getLastRun() {
		return lastRun;
	}
	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}
	public Date getLastFailure() {
		return LastFailure;
	}
	public void setLastFailure(Date lastFailure) {
		LastFailure = lastFailure;
	}
	public PeriodicJob getJob() {
		return job;
	}
	public void setJob(PeriodicJob job) {
		this.job = job;
	}

	

}
