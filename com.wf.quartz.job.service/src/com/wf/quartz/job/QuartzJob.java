package com.wf.quartz.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface QuartzJob extends Job {

	public abstract String getName();

	public abstract String getGroupName();

	public abstract Trigger getTrigger();

	public abstract JobDetail createJobDetail();
	
	

}
