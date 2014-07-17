package com.wf.quartz.job.simple;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.wf.quartz.job.UniforceJobHandler;

public class SimpleJob implements UniforceJobHandler {

	
	public String getName() {
		return "simpleJob";
	}

	
	public String getGroupName() {
		return "simpleGroup";
	}

	
	public Trigger getTrigger() {
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName(getName() + "TRIGGER");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(2);
		trigger.setRepeatInterval(3000);
		return trigger;
	}

	
	public JobDetail createJobDetail() {
		JobDetail job = new JobDetail();
		job.setName(getName());
		job.setGroup(getGroupName());
		job.setJobClass(this.getClass());
		return job;
	}

	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("[" + this.getClass().getSimpleName() + "]I am the simplest job for quartz");
	}

}
